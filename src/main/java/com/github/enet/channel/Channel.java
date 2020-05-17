package com.github.enet.channel;

import java.net.SocketAddress;

public interface Channel extends ChannelOutboundInvoker{
    
	public long id();
	
	public boolean isActive();
	
	public boolean isRegistered();
	
	public boolean isOpen();
	
	
	
	//
	SocketAddress localAddress();
	
	SocketAddress remoteAddress();
	
	
}
