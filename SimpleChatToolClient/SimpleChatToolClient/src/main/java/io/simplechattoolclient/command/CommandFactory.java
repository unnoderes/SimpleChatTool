package io.simplechattoolclient.command;

import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, CommandHandler> handlers = new HashMap<>();

    public CommandFactory(Channel channel) {
        registerCommands(channel);
    }

    //  统一管理所有指令
    private void registerCommands(Channel channel) {
        handlers.put("exit", new ExitCommandHandler(channel));
        handlers.put("list", new ListCommandHandler(channel));
        handlers.put("help", new HelpCommandHandler());
        handlers.put("clear", new ClearCommandHandler());
        // 可以继续添加其他指令
    }

    /**
     * 根据命令获取处理器
     * @param command 命令
     * @return 对应的 `CommandHandler`，若命令不存在，则返回 `DefaultCommandHandler`
     */
    public CommandHandler getCommandHandler(String command) {
        return handlers.getOrDefault(command, new DefaultCommandHandler(command));
    }
}
