package com.penetrate.client;

import com.penetrate.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

public class RealHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) {
        // 客户读取到真实服务数据了
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        Message message = new Message();
        message.setType(Message.TYPE_TRANSFER);
        message.setData(bytes);
        String vid = ctx.channel().attr(Constant.VID).get();
        if (StringUtil.isNullOrEmpty(vid)) {
            return;
        }
        Channel proxyChannel = Constant.vpc.get(vid);
        if (null != proxyChannel) {
            proxyChannel.writeAndFlush(message);
        }
        // 客户端发送真实数据到代理了
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String vid = ctx.channel().attr(Constant.VID).get();
        if (StringUtil.isNullOrEmpty(vid)) {
            super.channelInactive(ctx);
            return;
        }
        Channel proxyChannel = Constant.vpc.get(vid);
        if (proxyChannel != null) {
            Message message = new Message();
            message.setType(Message.TYPE_TRANSFER);
            message.setData(vid.getBytes());
            proxyChannel.writeAndFlush(message);
        }

        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        String vid = ctx.channel().attr(Constant.VID).get();
        if (StringUtil.isNullOrEmpty(vid)) {
            super.channelWritabilityChanged(ctx);
            return;
        }
        Channel proxyChannel = Constant.vpc.get(vid);
        if (proxyChannel != null) {
            proxyChannel.config().setOption(ChannelOption.AUTO_READ, ctx.channel().isWritable());
        }

        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}