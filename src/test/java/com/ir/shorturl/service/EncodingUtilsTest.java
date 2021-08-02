package com.ir.shorturl.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.ir.shorturl.utils.EncodingUtils;

@SpringBootTest
public class EncodingUtilsTest {

	@Test
	public void toAlphaTest_SUCCESS() {
		String expected = "CF";
		
		Assert.isTrue(EncodingUtils.toAlpha(57).equals(expected),"Failed to encode value!!");
	}
	
	
	@Test
	public void fromAlphaTest_SUCCESS() {
		int expected = 57;
		Assert.isTrue(EncodingUtils.fromAlpha("CF")==expected,"Failed to encode value!!");
	}
	
	

	@Test
	public void toAlpha52Test_SUCCESS() {
		String expected = "bwfS";

		Assert.isTrue(EncodingUtils.toAlpha52(200400).equals(expected),"Failed to encode value!!");
	}
	
	
	@Test
	public void fromAlphaTest52_SUCCESS() {
		int expected = 200400;
		Assert.isTrue(EncodingUtils.fromAlpha52("bwfS")==expected,"Failed to encode value!!");
	}
	
	
}
