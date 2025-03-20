package io.simplechattoolclient.TcpClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpClientHandler extends SimpleChannelInboundHandler<String> {
    private final TcpCommandProcessor commandProcessor;

    public TcpClientHandler(TcpCommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        if (msg.startsWith("[服务器] 你已登录为 ")) {
            String username = msg.replace("[服务器] 你已登录为 ", "").trim();
            commandProcessor.setUsername(username);
        }

        // 打印服务器消息
        System.out.println(msg);

        // 重新打印输入提示符（确保服务器消息后不会多余地打印用户输入的命令）
        commandProcessor.printPrompt();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("\033[1;31m[客户端] 连接异常: " + cause.getMessage() + "\033[0m");
        ctx.close();
    }
}