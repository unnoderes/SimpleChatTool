package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;

import java.util.Map;
import java.util.stream.Collectors;

public class ListCommand implements Command {
    private final Map<String, Channel> userChannels;

    public ListCommand(Map<String, Channel> userChannels) {
        this.userChannels = userChannels;
    }

    @Override
    public String execute(Channel sender, String[] args) {
        if (userChannels.isEmpty()) return "[服务器] 当前无在线用户";
        return "[服务器] 在线用户：" + userChannels.keySet().stream()
                .collect(Collectors.joining(", "));
    }
}
