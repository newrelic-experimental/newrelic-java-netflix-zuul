package com.netflix.zuul.filters;

import com.netflix.zuul.message.ZuulMessage;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface, originalName = "com.netflix.zuul.filters.SyncZuulFilter")
public abstract class SyncZuulFilter_Instrumentation<I extends ZuulMessage, O extends ZuulMessage> implements ZuulFilter<I, O> {
	
	@Trace
	public O apply(I input) {
		String filterName = filterName();
		if(filterName != null && !filterName.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix-Zuul","SyncZuulFilter",filterName,"apply");
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix-Zuul","SyncZuulFilter",getClass().getSimpleName(),"apply");
		}
		return Weaver.callOriginal();
	}

}
