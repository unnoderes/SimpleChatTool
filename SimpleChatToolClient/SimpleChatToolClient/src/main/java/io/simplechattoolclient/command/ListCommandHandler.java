package io.simplechattoolclient.command;

import io.netty.channel.Channel;

public class ListCommandHandler implements CommandHandler {
    private final Channel channel;

    public ListCommandHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean execute(String args) {
        channel.writeAndFlush("list\n"); // 发送 `list` 指令到服务器
        return true;
    }
}
