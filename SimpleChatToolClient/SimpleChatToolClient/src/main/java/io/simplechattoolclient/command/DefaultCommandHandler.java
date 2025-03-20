package io.simplechattoolclient.command;

import io.netty.channel.Channel;

public class DefaultCommandHandler implements CommandHandler {
    private final String command;

    public DefaultCommandHandler(String command) {
        this.command = command;
    }

    @Override
    public boolean execute(String args) {
        System.out.println("\033[1;31m[服务器] 未知指令：" + command + "\033[0m");
        System.out.println("输入 \033[1;33mhelp\033[0m 查看可用命令。\n");
        return true;
    }
}