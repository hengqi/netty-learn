package com.chenhl.netty.firstdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty作为Http服务器
 *
 * netty作为http服务器的一个流程：
 * 首先是启动一个serverbootstrap这样的一个服务器，服务器会关联两个事件循环组，一个叫bossGroup,一个叫workGroup,
 * bossGroup是用来获取连接的，而workerGroup是真正的用来处理链接的，这两个循环都是死循环，永远不会退出的。
 * 在服务器启动的时候可以关联相应的处理器，这里使用initializer，在initializer里的initChannel这个方法内，我们
 * 可以提供若干个自定义的处理器，也可以添加netty本身内置的handler，按照顺序一个个处理，一定能走到我们自定义的处理器中
 * 在自定义的处理器里，我们可以实现请求的处理和响应。
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
