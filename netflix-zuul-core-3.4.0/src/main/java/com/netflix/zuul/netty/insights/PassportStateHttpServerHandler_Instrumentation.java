package com.netflix.zuul.netty.insights;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.ChannelHandlerContext_Instrumentation;
import io.netty.channel.ChannelPromise;

@Weave(originalName = "com.netflix.zuul.netty.insights.PassportStateHttpServerHandler")
public abstract class PassportStateHttpServerHandler_Instrumentation {

	@Weave(originalName = "com.netflix.zuul.netty.insights.PassportStateHttpServerHandler$InboundHandler")
	public static class InboundHandler_Instrumentation {

		@Trace(async = true)
		public void channelRead(ChannelHandlerContext_Instrumentation ctx, Object msg) {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}

		@Trace(async = true)
		public void userEventTriggered(ChannelHandlerContext_Instrumentation ctx, Object evt)  {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}

	}

	@Weave(originalName = "com.netflix.zuul.netty.insights.PassportStateHttpServerHandler$OutboundHandler")
	public static class OutboundHandler_Instrumentation {

		@Trace(async = true)
		public void write(ChannelHandlerContext_Instrumentation ctx, Object msg, ChannelPromise promise)  {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}
	}
}
