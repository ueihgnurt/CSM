package action;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.define;
public class RecvConnectionFromServer extends Thread{
    
//    private Socket      loginSocket;
    
    public RecvConnectionFromServer()
    {
//        this.loginSocket = loginSocket;
    }

    @Override
    public void run() {
        try 
        {
            ServerSocket mSocket = new ServerSocket(define.CMD_CLIENT_PORT);
            while(true)
            {
                Socket sSocket = mSocket.accept();
                CommandFromServer cmdthread = new CommandFromServer(sSocket);
                cmdthread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(RecvConnectionFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
