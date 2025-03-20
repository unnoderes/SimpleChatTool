package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;

public class ExitCommand implements Command {
    @Override
    public String execute(Channel sender, String[] args) {
        sender.close();
        return "[服务器] 连接已断开";
    }
}

