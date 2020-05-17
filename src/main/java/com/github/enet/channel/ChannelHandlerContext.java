package com.github.enet.channel;

import java.net.SocketAddress;

import com.github.enet.channel.nio.NioEventLoop;
import com.github.enet.concurrent.Executor;

public interface ChannelHandlerContext extends ChannelInboundInvoker, ChannelOutboundInvoker, Executor{
    
	public boolean isAsync();
    
    public Channel channel();
    
    public NioEventLoop ioexecutor();
    
    public ChannelHandler handler();
    
    public String name();
    
    
    
    
    ChannelHandlerContext fireChannelRegistered();
    
    ChannelHandlerContext fireChannelUnregistered();
    
    //
    ChannelHandlerContext fireChannelActive();
    
    //
    ChannelHandlerContext fireChannelInactive();
    
    //
    ChannelHandlerContext fireExceptionCaught(Throwable exc);
    
    //
    ChannelHandlerContext fireChannelRead(Object read);
    
    //
    ChannelHandlerContext fireUserEventTriggered(Object event);
    //
    
    //
    public void bind(SocketAddress localAddress);
    
    //
    public void connect(SocketAddress remoteAddress);
    
    //
    public void  connect(SocketAddress remoteAddress, SocketAddress localAddress);
    
    //
    public void disconnect();
    
    //
    public void close();
    
    //    
    public void deregister();
    
    //
    public ChannelHandlerContext read();
    
    //
    public void write(Object write);
    
    //
    public ChannelHandlerContext flush();
    
    //
    public void writeAndFlush(Object write);
    
}
