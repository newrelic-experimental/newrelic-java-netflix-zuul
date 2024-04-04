package com.netflix.zuul;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class FilterProcessor {
	
	@Trace
	public void preRoute() {
		Weaver.callOriginal();
	}
	
	@Trace
	public void route() {
		Weaver.callOriginal();
	}

	@Trace
	public void postRoute() {
		Weaver.callOriginal();
	}
}
