package com.example.jerotest;

import junit.framework.Assert;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMsg;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context ctx = ZMQ.context(1);
		Socket socket = ctx.socket(ZMQ.PUSH);
		boolean b = socket.send("it works!");
		TextView tv = new TextView(this);
		tv.setText("result "+b);
		setContentView(tv);
	}
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Context ctx = ZMQ.context(1);
//		Socket socket = ctx.socket(ZMQ.PULL);
//		ZMsg msg = ZMsg.recvMsg(socket);
//        Assert.assertNull(msg);
//		TextView tv = new TextView(this);
//		tv.setText(msg.toString());
//		setContentView(tv);
//	}

}
