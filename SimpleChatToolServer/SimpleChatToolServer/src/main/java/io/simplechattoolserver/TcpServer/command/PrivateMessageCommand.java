package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;

import java.util.Arrays;
import java.util.Map;

public class PrivateMessageCommand implements Command {
    private final Map<String, Channel> userChannels;

    public PrivateMessageCommand(Map<String, Channel> userChannels) {
        this.userChannels = userChannels;
    }

    @Override
    public String execute(Channel sender, String[] args) {
        if (args.length < 2) return "[服务器] 使用格式：private <用户名> <消息>";

        String targetUser = args[0];  // 私聊对象

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));  // 拼接消息内容

        Channel targetChannel = userChannels.get(targetUser);
        if (targetChannel == null || !targetChannel.isActive()) {
            return "[服务器] 用户 " + targetUser + " 不在线";
        }

        targetChannel.writeAndFlush("[私聊] " + getUsername(sender) + " -> 你: " + message + "\n");
        return "[私聊] 你 -> " + targetUser + ": " + message;
    }

    private String getUsername(Channel channel) {
        return userChannels.entrySet().stream()
                .filter(entry -> entry.getValue().equals(channel))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("未知用户");
    }
}