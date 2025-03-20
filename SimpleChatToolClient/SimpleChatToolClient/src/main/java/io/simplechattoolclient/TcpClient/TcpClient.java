package io.simplechattoolclient.TcpClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class TcpClient implements CommandLineRunner {

    private String serverHost = "127.0.0.1"; // 服务器地址（默认）
    private int serverPort = 8080;   // 服务器端口（默认）

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        // 让用户输入服务器 IP 和端口
        while (true) {
            System.out.print("\n\033[1;34m[客户端] 请输入服务器地址（默认 " + serverHost + ":" + serverPort + "）：\033[0m");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) break; // 直接回车，使用默认 IP & 端口

            String[] parts = input.split(":");
            if (parts.length == 1) {
                serverHost = parts[0]; // 只输入 IP，使用默认端口
            } else if (parts.length == 2) {
                serverHost = parts[0];
                try {
                    serverPort = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("\033[1;31m[客户端] 端口号格式错误，请重新输入！\033[0m");
                    continue;
                }
            } else {
                System.out.println("\033[1;31m[客户端] 地址格式错误，请使用 IP:端口 格式！\033[0m");
                continue;
            }
            break;
        }

        // 启动连接管理器
        TcpConnectionManager connectionManager = new TcpConnectionManager(serverHost, serverPort);
        connectionManager.start();
    }
}
