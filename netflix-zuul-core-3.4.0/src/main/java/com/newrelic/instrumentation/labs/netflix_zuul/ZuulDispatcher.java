package com.newrelic.instrumentation.labs.netflix_zuul;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.ChannelHandlerContext_Instrumentation;
import io.netty.handler.codec.http.HttpRequest;

public class ZuulDispatcher {
	
	private static volatile ZuulDispatcher instance = null;
	public static final AtomicBoolean instrumented = new AtomicBoolean(false);
	public static enum ChannelType {SERVER, CLIENT};
	
	public static ZuulDispatcher get() {
		if(instance == null) {
			synchronized (ZuulDispatcher.class) {
				if(instance == null) {
					instance = new ZuulDispatcher();
					instrumented.set(true);
				}
			}
		}
		
		return instance;
	}
	
	private ZuulDispatcher() {
		AgentBridge.instrumentation.retransformUninstrumentedClass(ZuulDispatcher.class);
	}
	
	@Trace(dispatcher = true)
	public void channelRead(ChannelHandlerContext_Instrumentation ctx, Object msg) {
		
		try {
			ctx.pipeline().zuul_token = NewRelic.getAgent().getTransaction().getToken();
			
			TracedMethod traced = NewRelic.getAgent().getTracedMethod();
			if(traced == null) {
				NewRelic.getAgent().getLogger().log(Level.FINER, "Unable to dispatch ");
			} else {
				traced.setMetricName("Custom","Netflix-Zuul","ZuulDispatcher");
				HttpRequest request = (HttpRequest)msg;
				NettyRequestHeaders inbound = new NettyRequestHeaders(request);
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.HTTP, inbound);
			}
		} catch (Throwable e) {
			AgentBridge.instrumentation.noticeInstrumentationError(e,  Weaver.getImplementationTitle());
		}
		finally {
			AgentBridge.currentApiSource.remove();
		}
	}
}
