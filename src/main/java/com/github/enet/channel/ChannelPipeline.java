package com.github.enet.channel;

import java.net.SocketAddress;

public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker{
	
	
	
    
	ChannelPipeline fireChannelRegistered();
    
    ChannelPipeline fireChannelUnregistered();
    
    //
    ChannelPipeline fireChannelActive();
    
    //
    ChannelPipeline fireChannelInactive();
    
    //
    ChannelPipeline fireExceptionCaught(Throwable exc);
    
    //
    ChannelPipeline fireChannelRead(Object read);
    
    //
    ChannelPipeline fireUserEventTriggered(Object event);
    
    
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
    public ChannelPipeline read();
    
    //
    public void write(Object write);
    
    //
    public ChannelPipeline flush();
    
    //
    public void writeAndFlush(Object write);
}
