package com.ir.shorturl.repository;

import com.ir.shorturl.domain.ShortcutEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortcutRepository extends MongoRepository<ShortcutEntry, String> {

	public ShortcutEntry findByUrl(String url);
	
	public ShortcutEntry findByDestinationUrl(String url);

}
