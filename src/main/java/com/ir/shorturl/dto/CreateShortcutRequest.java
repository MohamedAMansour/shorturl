package com.ir.shorturl.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateShortcutRequest {
	private String targetUrl;
	private boolean statsOn;
}
