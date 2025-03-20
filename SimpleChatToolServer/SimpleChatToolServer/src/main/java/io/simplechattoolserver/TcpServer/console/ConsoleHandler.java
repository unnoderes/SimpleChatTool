package io.simplechattoolserver.TcpServer.console;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleHandler.class);

    // ✅ 统一换行格式，确保不同终端兼容
    private void sendMessage(Channel channel, String message) {
        channel.writeAndFlush(message + "\r\n");
    }

    public void printCommandInputPrompt(Channel channel) {
        sendMessage(channel, "\r[服务器] 请输入指令："); // ✅ \r 确保光标回到行首
    }

    public void printSystemMessage(Channel channel, String message) {
        sendMessage(channel, "[服务器] " + message);
    }

    public void printError(Channel channel, String message) {
        sendMessage(channel, "[错误] " + message);
    }

    public void printResponse(Channel channel, String message) {
        sendMessage(channel, message);
    }

    public void logClientMessage(Channel channel, String msg) {
        Logger logger = LoggerFactory.getLogger(ConsoleHandler.class);
        logger.info("收到客户端 [{}] 消息：{}", channel.remoteAddress(), msg);
    }

    public void logClientConnection(Channel channel, boolean connected) {
        Logger logger = LoggerFactory.getLogger(ConsoleHandler.class);
        if (connected) {
            logger.info("客户端连接：{}", channel.remoteAddress());
        } else {
            logger.info("客户端断开连接：{}", channel.remoteAddress());
        }
    }

    public void logClientError(Channel channel, Throwable cause) {
        Logger logger = LoggerFactory.getLogger(ConsoleHandler.class);
        logger.error("客户端 [{}] 发生异常", channel.remoteAddress(), cause);
    }
}