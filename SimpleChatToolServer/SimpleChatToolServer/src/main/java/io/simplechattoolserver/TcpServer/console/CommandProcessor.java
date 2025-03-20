package io.simplechattoolserver.TcpServer.console;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;

public class CommandProcessor {
    private final CommandFactory commandFactory;
    private final ConsoleHandler consoleHandler;

    public CommandProcessor(ChannelGroup clients, Map<String, Channel> userChannels) {
        this.commandFactory = new CommandFactory(clients, userChannels);
        this.consoleHandler = new ConsoleHandler();
    }

    public String processCommand(Channel channel, String msg) {
        String[] parts = msg.trim().split(" ", 2);
        String command = parts[0].toLowerCase();
        String[] args = parts.length > 1 ? parts[1].split(" ") : new String[0];

        String response = commandFactory.getCommand(command).execute(channel, args);
        consoleHandler.printResponse(channel, response); // 使用 ConsoleHandler 统一管理输出

        return command;
    }
}
