package io.simplechattoolserver.TcpServer.console;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    TIME("time"),
    USERS("users"),
    ECHO("echo"),
    BROADCAST("broadcast"),
    STATUS("status"),
    PRIVATE("private"),
    LIST("list"),
    HELP("help"),
    CLEAR("clear"),
    EXIT("exit");

    private final String command;
    private static final Map<String, CommandType> COMMAND_MAP = new HashMap<>();

    // 静态初始化映射表
    static {
        for (CommandType type : values()) {
            COMMAND_MAP.put(type.command, type);
        }
    }

    CommandType(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }


    public static CommandType fromString(String command) {

        for (CommandType type : values()) {
            if (type.command.equalsIgnoreCase(command.split(" ")[0])) {
                return type;
            }
        }
        return COMMAND_MAP.getOrDefault(command.toLowerCase(), null);
    }
}
