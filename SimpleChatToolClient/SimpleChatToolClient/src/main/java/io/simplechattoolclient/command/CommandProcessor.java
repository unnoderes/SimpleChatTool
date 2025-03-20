package io.simplechattoolclient.command;

import io.netty.channel.Channel;

public class CommandProcessor {
    private final CommandFactory commandFactory;

    public CommandProcessor(Channel channel) {
        this.commandFactory = new CommandFactory(channel);
    }

    /**
     * 处理用户输入的指令
     * @param input 用户输入
     * @return 是否继续运行（`false` 代表退出）
     */
    public boolean processCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true; // 忽略空输入，避免输出 `未知指令`
        }

        // 解析指令
        String[] parts = input.trim().split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        // 获取对应命令处理器
        CommandHandler handler = commandFactory.getCommandHandler(command);
        return handler.execute(args);
    }
}
