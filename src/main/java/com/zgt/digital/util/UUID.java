package com.zgt.digital.util;

public class UUID {
	
	public static final String getUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
}
