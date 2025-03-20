package io.simplechattoolserver.TcpServer.console;

import io.netty.channel.Channel;

public interface Command {
    String execute(Channel sender, String[] args);
}