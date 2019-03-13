package com.toughchow.io.nio.timeserver;

/**
 * Created by toughChow
 * 2019-03-13 16:59
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8081;
        new Thread(new TimeClientHandle("127.0,0,1", port), "TimeClient-001").start();
    }
}
