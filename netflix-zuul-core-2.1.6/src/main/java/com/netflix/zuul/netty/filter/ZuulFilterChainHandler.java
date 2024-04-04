package com.netflix.zuul.netty.filter;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.ChannelHandlerContext_Instrumentation;

@Weave
public abstract class ZuulFilterChainHandler {

	@Trace(async = true)
	public void channelRead(ChannelHandlerContext_Instrumentation ctx, Object msg) {
		if(ctx.pipeline().zuul_token != null) {
			ctx.pipeline().zuul_token.link();
		}
		
		Weaver.callOriginal();
	}
	
	@Trace(async = true)
	public void userEventTriggered(ChannelHandlerContext_Instrumentation ctx, Object evt) {
		if(ctx.pipeline().zuul_token != null) {
			ctx.pipeline().zuul_token.link();
		}
		
		Weaver.callOriginal();
	}
}
