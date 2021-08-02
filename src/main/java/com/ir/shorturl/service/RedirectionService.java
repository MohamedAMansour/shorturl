package com.ir.shorturl.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ir.shorturl.domain.ClientTrackingEntry;
import com.ir.shorturl.domain.ShortcutEntry;
import com.ir.shorturl.exception.EntryAlreadyExistException;
import com.ir.shorturl.exception.EntryNotFoundException;
import com.ir.shorturl.repository.ShortcutRepository;
import com.ir.shorturl.utils.EncodingUtils;

@Service
public class RedirectionService {
	
	private static final String URL_NAME = "url";
	private static final String CLIENT_IP = "clientIP";
	private static final String NUMBER_OF_TIMES_ACCESSED = "nOfTimesAccessed"; 
	private static final String NUMBER_OF_HITS = "nOfHists";
	private static final String BASE_URL = "http://localhost:8080/";
	private static final String DESTINATION_URL ="destinationUrl";
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ShortcutRepository shortRepo;
	
	
	public ShortcutEntry createOrUpdateShortUrl(String targetUrl , boolean updateEnabled) throws EntryAlreadyExistException{
		ShortcutEntry shortcutEntry = null;
		boolean isNew = false;
		shortcutEntry = mongoTemplate.findOne(
				  Query.query(Criteria.where(DESTINATION_URL).is(targetUrl)), ShortcutEntry.class);
		
		if(!updateEnabled && shortcutEntry != null) {
			throw new EntryAlreadyExistException();
		}
		
		if(shortcutEntry == null) {
			shortcutEntry = new ShortcutEntry(targetUrl);
			isNew = true;
		}
		shortcutEntry = mongoTemplate.save(shortcutEntry);
		
		if(isNew) {
			ObjectId objectId =new ObjectId(shortcutEntry.getId());
			Integer timestamp = objectId.getTimestamp();
			String shortcutUrl = EncodingUtils.toAlpha52(timestamp);
			shortcutEntry.setUrl(shortcutUrl);
			mongoTemplate.save(shortcutEntry);
		}
		return shortcutEntry;
	}
	
	
	public ShortcutEntry retriveAndUpdateHitCount(String shortUrl) {
		Query query = new Query();
		query.addCriteria(Criteria.where(URL_NAME).is(shortUrl));
		Update update = new Update();
		update.inc(NUMBER_OF_TIMES_ACCESSED);
		ShortcutEntry shortcutEntry = mongoTemplate.findAndModify(query, update, ShortcutEntry.class);
		return shortcutEntry;
	}
	
	//statistics
	public void upsertLogEntry(String sourceIP , String shortUrl) {
		Query query = new Query();
		query.addCriteria(Criteria.where(URL_NAME).is(shortUrl).and(CLIENT_IP).is(sourceIP));
		Update update = new Update();
		update.set(CLIENT_IP, sourceIP);
		update.set(URL_NAME, shortUrl);
		update.inc(NUMBER_OF_HITS);
		mongoTemplate.upsert(query, update, ClientTrackingEntry.class);
	}
	
	//return destination URL
	public String redirectToUrl(String shortUrl, String sourceIP) throws EntryNotFoundException {
		//check if the entry exist
		ShortcutEntry shortcutEntry=shortRepo.findByUrl(shortUrl);
		if(shortcutEntry == null) {
			throw new EntryNotFoundException();
		}
		upsertLogEntry(sourceIP, shortUrl);
		retriveAndUpdateHitCount(shortUrl);
		return shortcutEntry.getDestinationUrl();
	}
	
}
