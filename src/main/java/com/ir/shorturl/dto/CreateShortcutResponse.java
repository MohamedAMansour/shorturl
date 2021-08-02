package com.ir.shorturl.dto;

import com.ir.shorturl.domain.ShortcutEntry;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateShortcutResponse {
	
	private ShortcutEntry shortcutEntry;
	private String errorCode;
	
}
