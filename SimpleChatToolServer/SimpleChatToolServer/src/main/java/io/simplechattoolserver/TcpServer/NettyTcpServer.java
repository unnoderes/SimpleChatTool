package io.simplechattoolserver.TcpServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class NettyTcpServer {
    private final int port = 8080;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TcpServerInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("TCP Chat Server started on port: " + port);
            future.channel().closeFuture().sync();
        } finally {
            stop();
        }
    }

    public void stop() {
        if (bossGroup != null) bossGroup.shutdownGracefully();
        if (workerGroup != null) workerGroup.shutdownGracefully();
    }
}
