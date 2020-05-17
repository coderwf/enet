package com.github.enet.channel;

import java.net.SocketAddress;

public interface ChannelOutboundInvoker {
	
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
    public ChannelOutboundInvoker read();
    
    //
    public void write(Object write);
    
    //
    public ChannelOutboundInvoker flush();
    
    //
    public void writeAndFlush(Object write);
    
}
