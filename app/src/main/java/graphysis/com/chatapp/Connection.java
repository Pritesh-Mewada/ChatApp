package graphysis.com.chatapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;



import java.io.IOException;

/**
 * Created by pritesh on 7/5/17.
 */

public class Connection  {
    private String Tag ="Connection";
    private static  XMPPTCPConnectionConfiguration.Builder configuration;
    private static XMPPTCPConnection connection;
    private static boolean status_chat=false;

    public Connection(String host,String server,int port){
        configuration = XMPPTCPConnectionConfiguration.builder();
        configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configuration.setHost(host);
        configuration.setServiceName(server);
        configuration.setPort(port);
        configuration.setDebuggerEnabled(true);
        connection = new XMPPTCPConnection(configuration.build());
        connection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                Log.d(Tag,"Connected");
            }

            @Override
            public void authenticated(XMPPConnection connection, boolean resumed) {
                Log.d(Tag,"authenticated");
            }

            @Override
            public void connectionClosed() {
                Log.d(Tag,"Connection closed");
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                Log.d(Tag,"Connection closed on error");
            }

            @Override
            public void reconnectionSuccessful() {
                Log.d(Tag,"Re Connection successfull");
            }

            @Override
            public void reconnectingIn(int seconds) {
                Log.d(Tag,"Re connecting in  "+seconds+ " Seconds");
            }

            @Override
            public void reconnectionFailed(Exception e) {
                Log.d(Tag,"Re Connection Failed");
            }
        });
    }

    public void connect(){
        AsyncTask<Void,Void,Void> ConnectionThread = new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try{
                    if(connection.isConnected()){
                        Log.d(Tag,"Connected Already by connect method");
                    }else{
                        connection.connect();
                        Log.d(Tag,"Connected by connect method");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }


        };

        ConnectionThread.execute();

    }

    public void login(String username,String Password){
        try {
            connection.login(username,Password);
            Log.d(Tag,"Logged in successfully");
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ChatMessage chatmessage){
        if(!status_chat){
            Chat mychat = ChatManager.getInstanceFor(connection).createChat(chatmessage.getReceiver(), new ChatMessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {
                        Log.d(Tag,chat.getParticipant()+"   "+message.getBody());
                }
            });
            Message message = new Message();
            message.setBody("hello i am pritesh");
            message.setType(Message.Type.chat);

            try {
                mychat.sendMessage("hello");
                Log.d(Tag,"Message sent");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            status_chat=true;
        }
    }
}
