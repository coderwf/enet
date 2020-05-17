package com.github.enet.concurrent;


public abstract class AbstractEventLoop implements EventLoop {
    
	private Thread thread;
		
	protected abstract void safeExecute(Runnable runner);
	
	public void execute(Runnable runner) {
		if(inEventLoop())
			try {
				runner.run();
			} catch (Exception e) {
			    
			}
		else
			safeExecute(runner);
	}

	public boolean inEventLoop() {
		return inEventLoop(Thread.currentThread());
	}

	public boolean inEventLoop(Thread thread) {
		return this.thread == thread;
	}
    
	protected abstract void process();
	
	public final void loop() {
	    thread = new Thread() {
	    	@Override
	    	public void run() {
	    		process();
	    	}
	    };
	    
	    thread.start();
	}
}
