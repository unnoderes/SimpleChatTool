package io.simplechattoolclient.command;

public enum Command {
    HELP("help", "显示帮助信息"),
    EXIT("exit", "退出聊天"),
    LIST("list", "查看在线用户"),
    TIME("time", "获取服务器时间"),
    ECHO("echo <msg>", "发送回显消息"),
    BROADCAST("broadcast <msg>", "发送广播消息"),
    PRIVATE("private <user> <msg>", "发送私聊消息"),
    CLEAR("clear", "清空屏幕");

    private final String command;
    private final String description;

    Command(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
