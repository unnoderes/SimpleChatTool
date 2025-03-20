package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;

public class EchoCommand implements Command {
    @Override
    public String execute(Channel sender, String[] args) {
        if (args.length == 0) return "[服务器] 使用格式：echo <消息>";
        return "[回显] " + String.join(" ", args);
    }
}
