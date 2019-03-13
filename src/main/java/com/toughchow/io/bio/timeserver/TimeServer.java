package com.toughchow.io.bio.timeserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by toughChow
 * 2019-03-13 13:13
 * desc: 这种方式，每次请求获取时间都会创建一个线程来创建TimeServerHandler
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 默认端口
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is start in port : " + port);
            Socket socket = null;
            while(true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                System.out.println("Server close");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
