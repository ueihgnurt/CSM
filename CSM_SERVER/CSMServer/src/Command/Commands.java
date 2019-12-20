package Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.define;

public class Commands {
    
    private Socket                  socket;
    private BufferedReader          reader;
    private PrintWriter             writer;
    
    public Commands(Socket sock)
    {
        this.socket = sock;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }catch(IOException ex){
        }
    }
    public boolean LogoutButton()
    {
        boolean result = false;
        String cmd = "LOGOUT";
        try
        {
            writer.println(cmd);
            writer.flush();
            String response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }catch(IOException ex){
        }
        return result;
    }   
}