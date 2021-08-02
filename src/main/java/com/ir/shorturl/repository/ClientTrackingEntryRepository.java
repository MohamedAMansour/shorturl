package com.ir.shorturl.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ir.shorturl.domain.ClientTrackingEntry;

public interface ClientTrackingEntryRepository  extends MongoRepository<ClientTrackingEntry, String> {

	ClientTrackingEntry findByClientIPAndUrl(String clientIP, String url);
	
}
