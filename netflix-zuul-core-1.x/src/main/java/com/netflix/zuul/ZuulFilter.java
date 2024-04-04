package com.netflix.zuul;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class ZuulFilter  {
	
	public abstract String filterType();

	@Trace
	public ZuulFilterResult runFilter() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Zuul","ZuulFilter",getClass().getSimpleName(),"runFilter");
		traced.addCustomAttribute("FilterType", filterType());
		ZuulFilterResult fResult = Weaver.callOriginal();
		String status = fResult != null ? fResult.getStatus() != null ? fResult.getStatus().name() : null : null;
		if(status != null) {
			traced.addCustomAttribute("FilterResult",status);
		}
		Throwable t = fResult != null ? fResult.getException() : null;
		if(t != null) {
			NewRelic.noticeError(t);
		}
		return fResult;
	}
	
}
