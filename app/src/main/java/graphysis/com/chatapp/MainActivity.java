package graphysis.com.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String tag ="Connection";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Connection connection = new Connection("192.168.0.5","192.168.0.5",5222);
        Button connect = (Button)findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag,"hello i am tapped");
                connection.connect();
            }
        });

        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               connection.login("pritesh","Pritesh@1996");
            }
        });
        Button send = (Button)findViewById(R.id.sendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage message = new ChatMessage();
                message.setReceiver("shalini");
                connection.sendMessage(message);
            }
        });
    }
}
