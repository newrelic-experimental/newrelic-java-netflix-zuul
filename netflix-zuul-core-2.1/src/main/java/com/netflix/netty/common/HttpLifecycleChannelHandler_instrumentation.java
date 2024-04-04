package com.netflix.netty.common;

import com.netflix.netty.common.HttpLifecycleChannelHandler.CompleteReason;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.netflix_zuul.NettyRequestHeaders;
import com.newrelic.instrumentation.labs.netflix_zuul.ZuulDispatcher;

import io.netty.channel.ChannelHandlerContext_Instrumentation;
import io.netty.handler.codec.http.HttpRequest;

@Weave(originalName = "com.netflix.netty.common.HttpLifecycleChannelHandler")
public abstract class HttpLifecycleChannelHandler_instrumentation {

	
	protected static boolean fireCompleteEventIfNotAlready(ChannelHandlerContext_Instrumentation ctx, CompleteReason reason) {
		boolean b = Weaver.callOriginal();
		if(b) {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.expire();
				ctx.pipeline().zuul_token = null;
			}
		}
		
		return b;
	}
	
	 protected static boolean fireStartEvent(ChannelHandlerContext_Instrumentation ctx, HttpRequest request) {
			boolean b = Weaver.callOriginal();
			if(b) {
				ZuulDispatcher.get().channelRead(ctx, request);
				NettyRequestHeaders headers = new NettyRequestHeaders(request);
				NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
				if(ctx.pipeline().zuul_token == null) {
					Token t = NewRelic.getAgent().getTransaction().getToken();
					if(t != null && t.isActive()) {
						ctx.pipeline().zuul_token= t;
					} else if(t != null) {
						t.expire();
						t = null;
					}
				}
			}
			
			return b;
	 }
}
