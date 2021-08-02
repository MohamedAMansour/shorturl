package com.ir.shorturl;


import java.util.Collection;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.ir.shorturl.repository")
@Configuration
public class ShorturlApplication extends AbstractMongoClientConfiguration{

	public static void main(String[] args) {
		SpringApplication.run(ShorturlApplication.class, args);
	}
	
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://192.168.1.5:27017/shorturl");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        
        return MongoClients.create(mongoClientSettings);
    }

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return "shorturl";
	}

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.ir.shorturl.domain");
    }
}
