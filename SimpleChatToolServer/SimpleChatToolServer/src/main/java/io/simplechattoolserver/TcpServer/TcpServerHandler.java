package io.simplechattoolserver.TcpServer;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.simplechattoolserver.TcpServer.console.CommandProcessor;
import io.simplechattoolserver.TcpServer.console.ConsoleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class TcpServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);
    private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<String, Channel> userChannels = new ConcurrentHashMap<>();

    private final CommandProcessor commandProcessor = new CommandProcessor(clients, userChannels);
    private final ConsoleHandler consoleHandler = new ConsoleHandler(); //   新增 ConsoleHandler

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        clients.add(incoming);
        consoleHandler.logClientConnection(incoming, true); //   记录日志
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();

        //   修正：移除 `userChannels` 中的对应用户
        userChannels.entrySet().removeIf(entry -> entry.getValue().equals(incoming));

        clients.remove(incoming);
        consoleHandler.logClientConnection(incoming, false); //   记录日志
    }

    //   解析客户端输入的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel incoming = ctx.channel();
        msg = msg.trim(); // 避免不必要的空格
        msg = new String(msg.getBytes(CharsetUtil.ISO_8859_1), CharsetUtil.UTF_8).trim();

        consoleHandler.logClientMessage(incoming, msg); //   记录日志

        //   如果输入为空或者仅是空格，不处理指令，直接打印提示符
        if (msg.isEmpty()) {
            consoleHandler.printCommandInputPrompt(incoming); //   循环打印输入提示符
            return;
        }

        if (!userChannels.containsValue(incoming)) {
            String username = msg.trim();
            //   确保用户名不重复
            if (userChannels.containsKey(username)) {
                consoleHandler.printError(incoming, "用户名已存在，请重新输入：");
            } else {
                userChannels.put(username, incoming);
                consoleHandler.printSystemMessage(incoming, "你已登录为 " + username);
                logger.info("用户 [{}] 登录，地址：{}", username, incoming.remoteAddress());
            }
            return;
        }

        String response = commandProcessor.processCommand(incoming, msg);
        if (response != null) {
            consoleHandler.printResponse(incoming, response); //   统一使用 ConsoleHandler 输出
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();

        String remoteAddress = incoming.remoteAddress().toString();

        if (cause instanceof java.io.IOException && cause.getMessage().contains("远程主机强迫关闭了一个现有的连接")) {
            // 记录警告日志，避免打印错误堆栈
            logger.warn("客户端 [{}] 非正常断开连接", remoteAddress);
        } else {
            // 其他异常才打印完整堆栈信息
            logger.error("客户端 [{}] 发生异常", remoteAddress, cause);
        }

        // 关闭连接，清理资源
        ctx.close();
    }

    //   让用户登录时提供用户名
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        consoleHandler.printSystemMessage(incoming, "请输入你的用户名：");
    }

}
