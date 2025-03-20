package io.simplechattoolserver;

import io.simplechattoolserver.TcpServer.NettyTcpServer;
import io.simplechattoolserver.UdpServer.NettyUdpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SimpleChatToolServerApplication {
    private final NettyTcpServer tcpServer;
    private final NettyUdpServer udpServer;

    public SimpleChatToolServerApplication(NettyTcpServer tcpServer, NettyUdpServer udpServer) {
        this.tcpServer = tcpServer;
        this.udpServer = udpServer;
    }


    @PostConstruct
    public void startServers() throws InterruptedException {
        new Thread(() -> {
            try {
                tcpServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                udpServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleChatToolServerApplication.class, args);
    }
}
