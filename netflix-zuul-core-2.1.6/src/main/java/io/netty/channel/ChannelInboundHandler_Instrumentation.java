package io.netty.channel;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface, originalName = "io.netty.channel.ChannelInboundHandler")
public abstract class ChannelInboundHandler_Instrumentation {

    @Trace(async = true, excludeFromTransactionTrace = true)
    public void channelRead(ChannelHandlerContext_Instrumentation ctx, Object msg) throws Exception {
        if (ctx.pipeline().zuul_token != null) {
            ctx.pipeline().zuul_token.link();
        }

        Weaver.callOriginal();
    }

}