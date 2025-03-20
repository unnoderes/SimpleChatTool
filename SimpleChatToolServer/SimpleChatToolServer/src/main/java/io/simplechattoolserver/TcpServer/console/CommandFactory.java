package io.simplechattoolserver.TcpServer.console;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.simplechattoolserver.TcpServer.command.*;

import java.util.EnumMap;
import java.util.Map;

public class CommandFactory {
    private final Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

    public CommandFactory(ChannelGroup clients, Map<String, Channel> userChannels) {
        commands.put(CommandType.LIST, new ListCommand(userChannels));
        commands.put(CommandType.BROADCAST, new BroadcastCommand(clients));
        commands.put(CommandType.PRIVATE, new PrivateMessageCommand(userChannels));
        commands.put(CommandType.TIME, new TimeCommand());
        commands.put(CommandType.USERS, new UsersNumCommand(clients));
        commands.put(CommandType.ECHO, new EchoCommand());
        commands.put(CommandType.STATUS, new StatusCommand(clients));
        commands.put(CommandType.EXIT, new ExitCommand());
        commands.put(CommandType.HELP, new HelpCommand());
        commands.put(CommandType.CLEAR, new ClearCommand());
    }

    public Command getCommand(String command) {
        CommandType type = CommandType.fromString(command);
        return commands.getOrDefault(type, (sender, args) -> "[服务器] 未知指令：" + command);
    }
}
