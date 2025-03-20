package io.simplechattoolclient.TcpClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class TcpConnectionManager {
    private final String serverHost;
    private final int serverPort;
    private Channel channel;
    private final TcpCommandProcessor commandProcessor;

    public TcpConnectionManager(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.commandProcessor = new TcpCommandProcessor(this);
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            socketChannel.pipeline().addLast(new TcpClientHandler(commandProcessor));
                        }
                    });

            channel = bootstrap.connect(serverHost, serverPort).sync().channel();
            System.out.println("\n\033[1;32m[客户端] 连接服务器成功！\033[0m");

            // 启动命令处理循环
            commandProcessor.startCommandLoop();
        } catch (Exception e) {
            System.err.println("\n\033[1;31m[客户端] 连接失败: " + e.getMessage() + "\033[0m");
        } finally {
            group.shutdownGracefully();
            System.exit(0);
        }
    }

    public void sendMessage(String message) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(message + "\n");
        }
    }

    public void closeConnection() {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }
}
