package org.springframework.cloud.netflix.zuul.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ZuulController {

	@Trace
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String matching = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
		String method = request.getMethod();
		if(matching != null && !matching.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, false, "Spring MVC", matching, method);
		} else {
			matching = (String) request.getAttribute("\"org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
			if(matching != null && !matching.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, false, "Spring MVC", matching, method);
			}
		}
		ModelAndView modelView = Weaver.callOriginal();
		
		return modelView;
	}
}
