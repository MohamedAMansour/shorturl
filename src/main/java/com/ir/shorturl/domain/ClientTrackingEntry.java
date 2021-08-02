package com.ir.shorturl.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@RequiredArgsConstructor
public class ClientTrackingEntry {

	
	@Id 
	private String id;
	
	@NonNull
	private String clientIP;
	
	@NonNull
	private String url;
	
	private long nOfHists;
}
