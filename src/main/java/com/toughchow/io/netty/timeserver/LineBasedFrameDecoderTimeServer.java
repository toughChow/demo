package com.toughchow.io.netty.timeserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by toughChow
 * 2019-03-14 11:03
 */
public class LineBasedFrameDecoderTimeServer {

    public void bind(int port) throws Exception {
        /*  配置服务端的NIO线程组
            NioEventLoopGroup是个线程组，它包含了一组NIO线程，专门用于网络时间的处理，实际上就是Reactor线程组
            这里创建两个是一个用于服务端接收客户端的连接，一个用于SocketChannel的网络读写
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // ServerBootstrap是Netty用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap b = new ServerBootstrap();
            // 将两个NIO线程组当作入参传递到ServerBootstrap中
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)// 设置创建的Channel为NioServerChannel（功能对应JDK NIO的ServerSocketChannel）
                    .option(ChannelOption.SO_BACKLOG, 1024) // 配置NioServerSocketChannel的TCP参数
                    .childHandler(new ChildChannelHandler()); // 绑定IO时间的处理类ChildChannelHandler 用于处理网络I/O事件
            // 绑定端口 同步等待成功
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 退出 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new LineBaseFrameDecoderTimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8081;
        new LineBasedFrameDecoderTimeServer().bind(port);
    }
}
