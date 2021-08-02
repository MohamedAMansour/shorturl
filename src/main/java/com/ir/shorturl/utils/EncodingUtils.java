package com.ir.shorturl.utils;

public class EncodingUtils {
	private static final int BASE = 26;
	private static final int BASE_52 = 52;
	private static String BASE52_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String toAlpha(int i) {
		
		int d = i / BASE ;
		int r = i % BASE;
		String value = "";
		if(d > 0 ) {
			value = toAlpha(d)+ (char)((int)'A' + r );
		}else {
			return "" + (char)((int)'A' + r );
		}
		return value;
	}
	
	public static int fromAlpha(String value) {
		int pow = 0;
		int total = 0;
		for(int i = value.length() -1 ; i >=0 ; i--) {
			double v = Math.pow(BASE, pow++) *  ((int)value.charAt(i) - (int) 'A');
			total += v;
		}
		return total;
		
	}

	
	public static String toAlpha52(int i) {
		
		int d = i / BASE_52 ;
		int r = i % BASE_52;
		String value = "";
		if(d > 0 ) {
			value = toAlpha52(d)+ BASE52_STRING.charAt(r);
		}else {
			return ""+BASE52_STRING.charAt(r);
		}
		return value;
	}
	
	public static int fromAlpha52(String value) {
		int pow = 0;
		int total = 0;
		for(int i = value.length() -1 ; i >=0 ; i--) {
			double v = Math.pow(BASE_52, pow++) *  BASE52_STRING.indexOf(value.charAt(i));
			total += v;
		}
		return total;
		
	}
	
	public static String toAlphabetic(int i) {
	    if( i<0 ) {
	        return "-"+toAlphabetic(-i-1);
	    }

	    int quot = i/26;
	    int rem = i%26;
	    char letter = (char)((int)'A' + rem);
	    if( quot == 0 ) {
	        return ""+letter;
	    } else {
	        return toAlphabetic(quot-1) + letter;
	    }
	}
}
