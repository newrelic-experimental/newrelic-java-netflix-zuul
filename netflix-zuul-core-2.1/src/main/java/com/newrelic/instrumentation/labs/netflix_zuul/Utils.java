package com.newrelic.instrumentation.labs.netflix_zuul;

import java.util.Map;

import com.netflix.zuul.message.http.HttpRequestInfo;

public class Utils {
	
	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			attributes.put(key, value);
		}
	}

	public static void addRequestInfo(Map<String,Object> attributes, HttpRequestInfo info) {
		if(info != null) {
			addAttribute(attributes, "Request-Method", info.getMethod());
			addAttribute(attributes, "Request-Server", info.getServerName());
			addAttribute(attributes, "Request-OriginalHost", info.getOriginalHost());
		}
	}
}
