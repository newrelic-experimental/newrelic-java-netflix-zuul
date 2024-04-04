package org.springframework.cloud.netflix.zuul.filters;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class RouteLocator {

	public Route getMatchingRoute(String path) {

		Route route = Weaver.callOriginal();
		if(route != null) {
			TracedMethod traced = NewRelic.getAgent().getTracedMethod();
			HashMap<String, Object> attributes = new HashMap<>();
			String id = route.getId();
			if(id != null && !id.isEmpty()) {
				attributes.put("Route-ID", id);
			}
			String full = route.getFullPath();
			if(full != null && !full.isEmpty()) {
				attributes.put("Route-FullPath", full);
			}
			String routePath = route.getPath();
			if(routePath != null && !routePath.isEmpty()) {
				attributes.put("Route-Path", routePath);
			}
			String location = route.getLocation();
			if(location != null && !location.isEmpty()) {
				attributes.put("Route-Location", location);
			}
			traced.addCustomAttributes(attributes);
		}

		return route;
	}
}
