package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.simplechattoolserver.TcpServer.console.Command;

public class BroadcastCommand implements Command {
    private final ChannelGroup clients;

    public BroadcastCommand(ChannelGroup clients) {
        this.clients = clients;
    }

    @Override
    public String execute(Channel sender, String[] args) {
        if (args.length == 0) return "[服务器] 广播消息不能为空";

        String content = String.join(" ", args);
        String broadcastMessage = "[广播] " + sender.remoteAddress() + ": " + content;
        clients.writeAndFlush(broadcastMessage + "\n");

        return "[服务器] 广播消息已发送";
    }
}
