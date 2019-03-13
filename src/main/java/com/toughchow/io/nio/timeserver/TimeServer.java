package com.toughchow.io.nio.timeserver;

/**
 * Created by toughChow
 * 2019-03-13 14:48
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);

        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
