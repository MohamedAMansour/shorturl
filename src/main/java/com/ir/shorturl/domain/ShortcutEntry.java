package com.ir.shorturl.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortcutEntry {

	  @Id
	  private String id;
	  
	  private String url;
	  @NonNull
	  private String destinationUrl;
	  
	  private long nOfTimesAccessed;
}
