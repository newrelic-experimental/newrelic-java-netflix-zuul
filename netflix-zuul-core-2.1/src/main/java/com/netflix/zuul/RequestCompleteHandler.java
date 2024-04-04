package com.netflix.zuul;

import java.util.HashMap;

import com.netflix.zuul.message.http.HttpRequestInfo;
import com.netflix.zuul.message.http.HttpResponseMessage;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.netflix_zuul.Utils;

@Weave(type = MatchType.Interface)
public class RequestCompleteHandler {

	@Trace
	public void handle(HttpRequestInfo inboundRequest, HttpResponseMessage response) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<>();
		Utils.addRequestInfo(attributes, inboundRequest);
		traced.addCustomAttribute("In", inboundRequest.getMethod());
		traced.setMetricName("Custom","Netflix","Zuul","RequestCompleteHandler",getClass().getSimpleName(),"handle");
		Weaver.callOriginal();
	}
}
