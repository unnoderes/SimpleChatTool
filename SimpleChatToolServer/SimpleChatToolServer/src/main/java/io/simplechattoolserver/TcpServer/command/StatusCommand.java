package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.simplechattoolserver.TcpServer.console.Command;

public class StatusCommand implements Command {
    private final ChannelGroup clients;

    public StatusCommand(ChannelGroup clients) {
        this.clients = clients;
    }

    @Override
    public String execute(Channel sender, String[] args) {
        return "[服务器状态] 在线用户数: " + clients.size();
    }
}
