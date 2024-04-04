package com.netflix.zuul.netty.filter;

import com.netflix.zuul.filters.ZuulFilter_instrumentation;
import com.netflix.zuul.message.ZuulMessage;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.handler.codec.http.HttpContent;

@Weave(type = MatchType.BaseClass)
public abstract class BaseZuulFilterRunner<I extends ZuulMessage, O extends ZuulMessage> {
	
	@Trace(dispatcher=true)
	protected void invokeNextStage(O zuulMesg) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix","Zuul",getClass().getSimpleName(),"invokeNextStage");
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	protected O filter(ZuulFilter_instrumentation<I, O> filter, I inMesg)  {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix","Zuul",getClass().getSimpleName(),"filter");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected void invokeNextStage(O zuulMesg, HttpContent chunk) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix","Zuul",getClass().getSimpleName(),"invokeNextStage");
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	 protected void resume(O zuulMesg) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Netflix","Zuul",getClass().getSimpleName(),"resume");
		Weaver.callOriginal();
	 }
}
