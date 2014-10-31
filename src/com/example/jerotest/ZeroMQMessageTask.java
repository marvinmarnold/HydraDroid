package com.example.jerotest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.zeromq.ZMQ;
 
public class ZeroMQMessageTask extends AsyncTask<String, Void, String> {
    private final Handler uiThreadHandler;
 
    public ZeroMQMessageTask(Handler uiThreadHandler) {
        this.uiThreadHandler = uiThreadHandler;
    }
 
    @Override
    protected String doInBackground(String... params) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        socket.connect("tcp://192.168.1.49:5555");
 
        HydraMsg hm = new HydraMsg (HydraMsg.HELLO);
        hm.send (socket);
        
        hm = HydraMsg.recv (socket);
        String result = hm.post_id();
        
//        String result = new String(socket.recv(0));
 
        socket.close();
        context.term();
 
        return result;
    }
 
    @Override
    protected void onPostExecute(String result) {
    	Message msg = new Message();
    	msg.setTarget(uiThreadHandler);
    	Bundle bundle = new Bundle();
    	bundle.putString("AT", result);
    	msg.setData(bundle);
    	//Util.bundledMessage(uiThreadHandler, result)
        uiThreadHandler.sendMessage(msg);
    }
}