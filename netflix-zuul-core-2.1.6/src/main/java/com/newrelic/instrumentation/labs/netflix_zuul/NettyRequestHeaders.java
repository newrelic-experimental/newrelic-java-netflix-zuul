package com.newrelic.instrumentation.labs.netflix_zuul;

import java.util.Collection;
import java.util.Collections;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

public class NettyRequestHeaders implements Headers {
	
	private HttpRequest request = null;
	
	public NettyRequestHeaders(HttpRequest req) {
		request = req;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		HttpHeaders headers = request.headers();
		if(headers != null) {
			return headers.get(name);
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		HttpHeaders headers = request.headers();
		if(headers != null) {
			return headers.getAll(name);
		}
		return Collections.emptyList();
	}

	@Override
	public void setHeader(String name, String value) {
		HttpHeaders headers = request.headers();
		headers.set(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		HttpHeaders headers = request.headers();
		headers.add(name, value);
	}

	@Override
	public Collection<String> getHeaderNames() {
		HttpHeaders headers = request.headers();
		
		return headers.names();
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
