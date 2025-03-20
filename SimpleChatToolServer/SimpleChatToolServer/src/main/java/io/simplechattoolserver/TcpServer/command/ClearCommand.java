package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;

public class ClearCommand implements Command {

    @Override
    public String execute(Channel sender, String[] args) {
        return "\033[H\033[2J";
    }
}
