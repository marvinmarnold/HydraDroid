package com.example.jerotest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.zeromq.ZMQ;

public class ZeroMQServer implements Runnable {
	private final Handler uiThreadHandler;

	public ZeroMQServer(Handler uiThreadHandler) {
		this.uiThreadHandler = uiThreadHandler;
	}

	@Override
	public void run() {
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://127.0.0.1:5555");

		while (!Thread.currentThread().isInterrupted()) {
			byte[] msg =socket.recv(0);
	    	Message message = new Message();
	    	message.setTarget(uiThreadHandler);
	    	Bundle bundle = new Bundle();
	    	bundle.putString("AT", new String(msg));
	    	message.setData(bundle);
	        uiThreadHandler.sendMessage(message);
	        
//			uiThreadHandler.sendMessage(Util.bundledMessage(uiThreadHandler,
//					new String(msg)));
			socket.send("response", 0);
			//socket.send(Util.reverseInPlace(msg), 0);
		}
		socket.close();
		context.term();
	}
}
