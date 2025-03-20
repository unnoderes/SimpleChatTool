package io.simplechattoolserver.UdpServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.stereotype.Component;

@Component
public class NettyUdpServer {
    private static final int PORT = 9090;
    private EventLoopGroup group;
    private Channel channel;

    public void start() throws InterruptedException {
        group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler());

            channel = bootstrap.bind(PORT).sync().channel();
            System.out.println("UDP Chat Server started on port: " + PORT);
            channel.closeFuture().await();
        } finally {
            stop();
        }
    }

    public void stop() {
        if (group != null) group.shutdownGracefully();
        if (channel != null) channel.close();
    }
}
