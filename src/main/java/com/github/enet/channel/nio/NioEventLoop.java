package com.github.enet.channel.nio;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.enet.concurrent.AbstractEventLoop;

public class NioEventLoop extends AbstractEventLoop{
    
	private Selector selector;
	
	private BlockingQueue<Runnable> taskQs = new LinkedBlockingQueue<Runnable>();
	
	private Iterator<SelectionKey> selectedKeys;
	
	
	@Override
	protected void safeExecute(Runnable runner) {
	    try {
			taskQs.put(runner);
			
			selector.wakeup();
			
		} catch (InterruptedException e) {
			// e.printStackTrace();
			// to-do
		}    
	}
    
	private void runAllTasks() {
		
	    for(;;) {
	    	Runnable runner = taskQs.poll();
	    	if(runner == null)
	    		break;
	    	
	    	try {
				runner.run();
			} catch (Exception e) {
			}
	    }
	    
	}
	
	private void processSelectedKey(SelectionKey key) {
		
		if(!key.isValid()) {
			//close the channel
			
		}
	}
	
	private void processSelectedKeys() {
		SelectionKey key;
		while(selectedKeys.hasNext()) {
		    key = selectedKeys.next();
		    selectedKeys.remove();
		    processSelectedKey(key);
		}
	}
	
	private boolean doSelect(long timeout) {
		int selectCnt = 0;
		
	    try {
			selectCnt = timeout <= 0? selector.selectNow(): selector.select(timeout);
		} catch (IOException e) {
			
		}
	    
	    return selectCnt > 0;
	}
	
	private void select(long timeout) {
	    if(! doSelect(timeout))
	    	return ;
	    
	    selectedKeys = selector.selectedKeys().iterator();
	    processSelectedKeys();
	}
	
	private static long getSelectTimeout() {
		long selectTimeout = 1000;
		try {
			selectTimeout = Long.parseLong(System.getenv("ENET_SELECT_TIMEOUT"));
		} catch (Exception e) {
			//
		}
		
		return selectTimeout < 1000? 1000: selectTimeout;
	}
	
	@Override
	protected void process() {
		
		long selectTimeout = getSelectTimeout(); //ms
		long runTaskStart, runTaskEnd;
		
        for(;;) {
        	
        	runTaskStart = System.currentTimeMillis();
        	runAllTasks();
        	runTaskEnd= System.currentTimeMillis();
        	select(selectTimeout - (runTaskEnd - runTaskStart));
        }
        
	}
	
}
