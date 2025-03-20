package io.simplechattoolserver.UdpServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        String msg = packet.content().toString(CharsetUtil.UTF_8);
        InetSocketAddress sender = packet.sender();
        System.out.println("收到消息: [" + sender + "] " + msg);

        // 发送广播消息给所有客户端
        String response = "[广播] " + sender + ": " + msg;
        DatagramPacket responsePacket = new DatagramPacket(
                packet.content().copy(), sender);

        ctx.writeAndFlush(responsePacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
