package io.simplechattoolclient.TcpClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpCommandProcessor {
    private final TcpConnectionManager connectionManager;
    private String username = null;
    private final String clientAddress = getClientAddress();

    public TcpCommandProcessor(TcpConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void startCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printPrompt();
            String command = scanner.nextLine().trim();
            if (command.isEmpty()) continue;

            if ("exit".equalsIgnoreCase(command)) {
                System.out.println("\n\033[1;31m[客户端] 正在断开连接...\033[0m");
                connectionManager.sendMessage("exit");
                connectionManager.closeConnection();
                break;
            }

            if ("clear".equalsIgnoreCase(command)) {
                clearScreen();
                continue;
            }

            connectionManager.sendMessage(command);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void printPrompt() {
        if (username != null) {
            System.out.print("\033[1;34m[" + username + "] > \033[0m");
        } else {
            System.out.print("\033[1;34m[" + clientAddress + "] > \033[0m");
        }
        System.out.flush();
    }

    private static String getClientAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "UnknownHost";
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}