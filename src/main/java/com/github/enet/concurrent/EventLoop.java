package com.github.enet.concurrent;

public interface EventLoop extends Executor{
    
	public boolean inEventLoop();
	
	public boolean inEventLoop(Thread thread);
	
}
