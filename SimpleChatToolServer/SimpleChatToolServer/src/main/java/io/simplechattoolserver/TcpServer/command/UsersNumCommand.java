package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.simplechattoolserver.TcpServer.console.Command;

public class UsersNumCommand implements Command {
    private final ChannelGroup clients;

    public UsersNumCommand(ChannelGroup clients) {
        this.clients = clients;
    }

    @Override
    public String execute(Channel sender, String[] args) {
        return "[服务器] 当前在线用户数：" + clients.size();
    }
}