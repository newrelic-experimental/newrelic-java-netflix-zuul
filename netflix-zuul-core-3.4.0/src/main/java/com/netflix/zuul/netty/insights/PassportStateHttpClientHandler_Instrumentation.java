package com.netflix.zuul.netty.insights;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.ChannelHandlerContext_Instrumentation;
import io.netty.channel.ChannelPromise;

@Weave(originalName = "com.netflix.zuul.netty.insights.PassportStateHttpClientHandler")
public abstract class PassportStateHttpClientHandler_Instrumentation {
	
	@Weave(originalName = "com.netflix.zuul.netty.insights.PassportStateHttpClientHandler$InboundHandler")
	 public static class InboundHandler {
		 
		@Trace(async = true)
		public void channelRead(ChannelHandlerContext_Instrumentation ctx, Object msg)  {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}
	 }

	@Weave(originalName = "com.netflix.zuul.netty.insights.PassportStateHttpClientHandler$OutboundHandler")
	public static class OutboundHandler_Instrumentation {
		
		@Trace(async = true)
		public void write(ChannelHandlerContext_Instrumentation ctx, Object msg, ChannelPromise promise) {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}
	}
}
