package Action;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsmConnection extends Thread{
    
    private static final int CSMPORT = 10000;
    
    private Vector<Socket> lstsocket;
    private Vector<Thread> lstclientthr;
    public CsmConnection()
    {
        lstsocket = new Vector<>();
        lstclientthr = new Vector<>();
    }

    @Override
    public void run() 
    {
        try 
        {
            ServerSocket serversock  = new ServerSocket(CSMPORT);
            while(true)
            {
                Socket clientsock = serversock.accept();
                System.out.println("server accept 1 client");
                        
                LoginFromClient clientlogin = new LoginFromClient(clientsock);
                clientlogin.start();
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(CsmConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
