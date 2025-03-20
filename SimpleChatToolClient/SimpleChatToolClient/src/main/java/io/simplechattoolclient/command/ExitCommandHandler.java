package io.simplechattoolclient.command;

import io.netty.channel.Channel;

public class ExitCommandHandler implements CommandHandler {
    private final Channel channel;

    public ExitCommandHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean execute(String args) {
        System.out.println("\n\033[1;31m[客户端] 正在断开连接...\033[0m");
        channel.writeAndFlush("exit\n");
        channel.close();
        return false; // 终止客户端循环
    }
}
