package io.simplechattoolclient.command;

import io.netty.channel.Channel;

public class ClearCommandHandler implements CommandHandler {
    @Override
    public boolean execute(String args) {
        System.out.print("\033[H\033[2J"); // 清屏
        System.out.flush();
        return true;
    }
}
