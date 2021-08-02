package com.ir.shorturl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ir.shorturl.domain.ClientTrackingEntry;
import com.ir.shorturl.domain.ShortcutEntry;
import com.ir.shorturl.dto.CreateShortcutRequest;
import com.ir.shorturl.dto.CreateShortcutResponse;
import com.ir.shorturl.exception.EntryAlreadyExistException;
import com.ir.shorturl.exception.EntryNotFoundException;
import com.ir.shorturl.repository.ClientTrackingEntryRepository;
import com.ir.shorturl.repository.ShortcutRepository;
import com.ir.shorturl.service.RedirectionService;

@Controller
public class ShortURLController {
	
	private static final String WEBSITE_NAME = "http://who.it";
	private static final String ALREADY_EXIST_CODE = "4001";
	private static final String LOCATION_HEADER = "Location";
	private static final String REFERER_HEADER = "Referer";
	
	@Autowired
	RedirectionService redirectionService;
	@Autowired
	ShortcutRepository shortcutRepository;
	
	@Autowired
	ClientTrackingEntryRepository clientTrackingEntryRepository;
	
	

	@GetMapping("/{id}")
	public String redirectToSite(@PathVariable String id, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String url = redirectionService.redirectToUrl(id, request.getRemoteAddr());
			if (url!= null) {
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				response.setHeader(LOCATION_HEADER, url);
				response.setHeader(REFERER_HEADER, WEBSITE_NAME);
				return "empty";
			}
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
		}	
		model.addAttribute("name", id);
		return "greeting";
	}

	@PostMapping("/shorturls")
	public @ResponseBody CreateShortcutResponse newEmployee(@RequestBody CreateShortcutRequest shortcutRequest) {
		ShortcutEntry shortcutEntry = null;
		CreateShortcutResponse createShortcutResponse = new CreateShortcutResponse();
		try {
			shortcutEntry = redirectionService.createOrUpdateShortUrl(shortcutRequest.getTargetUrl(), false);
		} catch (EntryAlreadyExistException e) {
			shortcutEntry = shortcutRepository.findByDestinationUrl(shortcutRequest.getTargetUrl());
			createShortcutResponse.setErrorCode(ALREADY_EXIST_CODE);
		}
		createShortcutResponse.setShortcutEntry(shortcutEntry);
		return createShortcutResponse;
	}
	
	//statistics endpoint
	@GetMapping("/urlstatistics")
	public @ResponseBody java.util.List<ShortcutEntry> shortUrlStatistics() {
		return shortcutRepository.findAll();
	}

	@GetMapping("/clienstatistics")
	public @ResponseBody java.util.List<ClientTrackingEntry> clientStatistics() {
		return clientTrackingEntryRepository.findAll();
	}
	
	
}
