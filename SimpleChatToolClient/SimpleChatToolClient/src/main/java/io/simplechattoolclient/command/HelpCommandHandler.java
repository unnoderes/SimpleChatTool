package io.simplechattoolclient.command;

public class HelpCommandHandler implements CommandHandler {

    @Override
    public boolean execute(String args) {
        System.out.println("\n\033[1;36m[服务器] 可用命令：\033[0m");
        System.out.println("\033[1;33m  list     \033[0m - 查看在线用户");
        System.out.println("\033[1;33m  users    \033[0m - 显示当前在线用户数");
        System.out.println("\033[1;33m  echo [msg] \033[0m - 回显消息");
        System.out.println("\033[1;33m  broadcast [msg] \033[0m - 发送全局消息");
        System.out.println("\033[1;33m  private [user] [msg] \033[0m - 发送私聊消息");
        System.out.println("\033[1;33m  clear    \033[0m - 清屏");
        System.out.println("\033[1;33m  help     \033[0m - 显示帮助信息");
        System.out.println("\033[1;33m  exit     \033[0m - 退出程序\n");
        return true;
    }
}
