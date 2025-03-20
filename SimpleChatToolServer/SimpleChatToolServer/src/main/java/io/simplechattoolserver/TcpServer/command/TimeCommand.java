package io.simplechattoolserver.TcpServer.command;

import io.netty.channel.Channel;
import io.simplechattoolserver.TcpServer.console.Command;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCommand implements Command {
    @Override
    public String execute(Channel sender, String[] args) {
        return "[服务器] 当前时间：" + new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
