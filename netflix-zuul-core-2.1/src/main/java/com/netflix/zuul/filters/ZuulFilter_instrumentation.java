package com.netflix.zuul.filters;

import java.util.Hashtable;

import com.netflix.zuul.message.ZuulMessage;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import rx.Observable;

@Weave(type=MatchType.Interface, originalName = "com.netflix.zuul.filters.ZuulFilter")
public abstract class ZuulFilter_instrumentation<I extends ZuulMessage, O extends ZuulMessage> {

	public abstract FilterSyncType getSyncType();
	public abstract String filterName();
	public abstract FilterType filterType();

	@Trace(dispatcher = true)
	public Observable<O> applyAsync(I input) {
		String filterName = filterName();
		if(filterName != null && !filterName.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix-Zuul","ZuulFilter",filterName,"applyAsync");
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix-Zuul","ZuulFilter",getClass().getSimpleName(),"applyAsync");
		}
		Hashtable<String, Object> attributes = new Hashtable<>();
		attributes.put("Filter-Synctype", getSyncType());
		attributes.put("FilterType", filterType());
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
