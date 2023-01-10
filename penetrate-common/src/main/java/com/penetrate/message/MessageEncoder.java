package com.penetrate.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        int bodyLength = 5;
        if (message.getData() != null) {
            bodyLength += message.getData().length;
        }
        byteBuf.writeInt(bodyLength);
        byteBuf.writeByte(message.getType());
        if (message.getData() != null) {
            byteBuf.writeBytes(message.getData());
        }
    }
}
