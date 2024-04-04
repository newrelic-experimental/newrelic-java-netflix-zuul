package com.netflix.netty.common;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.netty.channel.ChannelHandlerContext_Instrumentation;
import io.netty.channel.ChannelPromise;

@Weave
public abstract class HttpServerLifecycleChannelHandler {

	@Weave
	public static class HttpServerLifecycleInboundChannelHandler {

		@Trace(async = true)
		public void channelRead(ChannelHandlerContext_Instrumentation ctx, Object msg)  {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}

	}

	@Weave
	public static class HttpServerLifecycleOutboundChannelHandler {

		@Trace(async = true)
		public void write(ChannelHandlerContext_Instrumentation ctx, Object msg, ChannelPromise promise) {
			if(ctx.pipeline().zuul_token != null) {
				ctx.pipeline().zuul_token.link();
			}
			Weaver.callOriginal();
		}

	}
}
