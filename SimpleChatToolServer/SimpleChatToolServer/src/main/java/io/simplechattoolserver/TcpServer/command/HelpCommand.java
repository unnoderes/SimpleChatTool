package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;
import io.simplechattoolserver.TcpServer.console.CommandType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HelpCommand implements Command {
    @Override
    public String execute(Channel sender, String[] args) {
        String availableCommands = Arrays.stream(CommandType.values())
                .map(type -> type.name().toLowerCase()) // 获取命令名称
                .collect(Collectors.joining(", ")); // 逗号分隔
        return "[服务器] 可用命令：" + availableCommands;
    }
}
