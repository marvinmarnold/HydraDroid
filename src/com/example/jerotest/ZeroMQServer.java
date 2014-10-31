package com.example.jerotest;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ZeroMQServer implements Runnable {
	private final Handler uiThreadHandler;

	public ZeroMQServer(Handler uiThreadHandler) {
		this.uiThreadHandler = uiThreadHandler;
	}

	@Override
	public void run() {
		ZContext context = new ZContext();
		ZMQ.Socket socket = context.createSocket(ZMQ.ROUTER);
		socket.bind("tcp://*:5555");
		int status;

		while (!Thread.currentThread().isInterrupted()) {
			HydraMsg hm = HydraMsg.recv (socket);
	        assert (hm != null);
	        
//	        status = hm.id();
//	        switch (status){
//	        	case HydraMsg.HELLO:
//	        			hm = new HydraMsg(HydraMsg.HELLO_OK);
//	        			hm.setPost_Id ("Life is short but Now lasts for ever");
//	        			hm.send(socket);
//	        		break;
//        		default:
//
//	        		break;     			
//	        		
//	        }
//	        String returnString = String.valueOf(status);
	        String returnString = "cat";
	    	Message message = new Message();
	    	message.setTarget(uiThreadHandler);
	    	Bundle bundle = new Bundle();
	    	bundle.putString("AT", returnString);
	    	message.setData(bundle);
	        uiThreadHandler.sendMessage(message);
	        
//    			uiThreadHandler.sendMessage(Util.bundledMessage(uiThreadHandler,
//    					new String(msg)));
			socket.send("response", 0);
			//socket.send(Util.reverseInPlace(msg), 0);
	        
	        hm.destroy();
		}
		context.destroy();
	}
}
