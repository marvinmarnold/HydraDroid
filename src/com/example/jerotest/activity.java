package com.example.jerotest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class activity extends Activity {
    private TextView textView;
    private EditText editText;
 
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
 
    private static String getTimeString() {
        return DATE_FORMAT.format(new Date());
    }
 
    private void serverMessageReceived(String messageBody) {
        textView.append(getTimeString() + " - server received: " + messageBody + "\n");
    }
 
    private void clientMessageReceived(String messageBody) {
        textView.append(getTimeString() + " - client received: " + messageBody + "\n");
    }
    
    private final MessageListenerHandler serverMessageHandler = new MessageListenerHandler(
            new IMessageListener() {
                @Override
                public void messageReceived(String messageBody) {
                    serverMessageReceived(messageBody);
                }
            },
            "AT");
 
    private final MessageListenerHandler clientMessageHandler = new MessageListenerHandler(
            new IMessageListener() {
                @Override
                public void messageReceived(String messageBody) {
                    clientMessageReceived(messageBody);
                }
            },
            "AT");
            
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        textView = (TextView)findViewById(R.id.text_console);
        editText = (EditText)findViewById(R.id.text_message);
 
        new Thread(new ZeroMQServer(serverMessageHandler)).start();
 
        findViewById(R.id.button_send_message).setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ZeroMQMessageTask(clientMessageHandler).execute(getTaskInput());
                }
 
                protected String getTaskInput() {
                    return editText.getText().toString();
                }
            });
    }
}
