package com.github.enet.channel;

public interface ChannelInboundInvoker {
	
	//
    ChannelInboundInvoker fireChannelRegistered();
    
    //
    ChannelInboundInvoker fireChannelUnregistered();
    
    //
    ChannelInboundInvoker fireChannelActive();
    
    //
    ChannelInboundInvoker fireChannelInactive();
    
    //
    ChannelInboundInvoker fireExceptionCaught(Throwable exc);
    
    //
    ChannelInboundInvoker fireChannelRead(Object read);
    
    //
    ChannelInboundInvoker fireUserEventTriggered(Object event);
    
}
