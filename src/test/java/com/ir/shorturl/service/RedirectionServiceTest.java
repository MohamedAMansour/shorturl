package com.ir.shorturl.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.ir.shorturl.exception.EntryAlreadyExistException;
import com.ir.shorturl.repository.ClientTrackingEntryRepository;
import com.ir.shorturl.repository.ShortcutRepository;
import com.ir.shorturl.domain.ShortcutEntry;

@SpringBootTest
public class RedirectionServiceTest {

	@Autowired
	RedirectionService redirectionService;
	
	
	@Autowired
	ShortcutRepository shortcutRepository;
	
	@Autowired
	ClientTrackingEntryRepository ClientTrackingEntryRepository;
	
	@BeforeEach
	void dropCollection() {
		shortcutRepository.deleteAll();
	}
	
	
	@Test
	void createOrUpdateShortUrlTest_EntryCreated() throws EntryAlreadyExistException {
		ShortcutEntry shortcutEntry =redirectionService.createOrUpdateShortUrl("https://google",false);
		
		Assert.isTrue(shortcutEntry.getUrl()!= null , "Url wrong format");
		Assert.isTrue(shortcutRepository.findByDestinationUrl("https://google")!= null , "entry not saved!");
	}
	
	
	@Test
	void createOrUpdateShortUrlTest_EXCEPTION_EntryAlreadyExistException() throws EntryAlreadyExistException {
		redirectionService.createOrUpdateShortUrl("https://google",false);
		assertThrows(EntryAlreadyExistException.class ,  () -> {
			redirectionService.createOrUpdateShortUrl("https://google",false);
			});
	}
	
	@Test
	void upsertLogEntry_SUCCESS() throws EntryAlreadyExistException {
		ShortcutEntry shortcutEntry = redirectionService.createOrUpdateShortUrl("https://google",false);
		redirectionService.upsertLogEntry("127.222.222.0", shortcutEntry.getUrl());
		redirectionService.upsertLogEntry("127.222.222.0", shortcutEntry.getUrl());
		Assert.isTrue(ClientTrackingEntryRepository.findByClientIPAndUrl("127.222.222.0", shortcutEntry.getUrl()).getNOfHists()==2 , "update failed!");
	}
	
	@Test
	void retriveAndUpdateHitCount_SUCCESS() throws EntryAlreadyExistException {
		ShortcutEntry shortcutEntry =redirectionService.createOrUpdateShortUrl("https://google",false);
		
		redirectionService.retriveAndUpdateHitCount(shortcutEntry.getUrl());
		
		Assert.isTrue(shortcutEntry.getUrl()!= null , "Url wrong format");
		Assert.isTrue(shortcutRepository.findByDestinationUrl("https://google").getNOfTimesAccessed()==1 , "entry not saved!");
	}
	
}
