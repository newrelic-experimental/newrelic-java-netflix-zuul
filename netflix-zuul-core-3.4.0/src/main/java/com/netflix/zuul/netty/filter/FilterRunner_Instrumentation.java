package com.netflix.zuul.netty.filter;

import com.netflix.zuul.message.ZuulMessage;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.handler.codec.http.HttpContent;

@Weave(type = MatchType.Interface, originalName = "com.netflix.zuul.netty.filter.FilterRunner")
public abstract class FilterRunner_Instrumentation<I extends ZuulMessage, O extends ZuulMessage>  {

	@Trace
    public void filter(I zuulMesg) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix","Zuul",getClass().getSimpleName(),"filter");
    	Weaver.callOriginal();
    }
    
	@Trace
    public void filter(I zuulMesg, HttpContent chunk) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix","Zuul",getClass().getSimpleName(),"filter");
    	Weaver.callOriginal();
    }

}
