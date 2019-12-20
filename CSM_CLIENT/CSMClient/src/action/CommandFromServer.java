package action;

import csmclient.CsmclientLoginGui;
import csmclient.CsmclientMainGui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.Global;
import structure.define;

public class CommandFromServer extends Thread
{
    private static final int SHUTDOWN = 13;
    private static final int RESTART = 14;
    private static final int QUIT = 18;
    private static final int KILL = 19;
    private Socket              cmdSocket;
    private BufferedReader      reader;
    private PrintWriter         writer;
    
    public CommandFromServer(Socket sock)
    {
        this.cmdSocket = sock;
        //this.logSocket = Global.loginSocket;
    }
    
    @Override
    public void run() 
    {
        try
        {
            reader = new BufferedReader(new InputStreamReader(cmdSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(cmdSocket.getOutputStream()), true);
           
            String line = "";
            int parse = 0;

            line = reader.readLine();
            System.out.println(line);
            if(line == null)
            {
                System.out.print("loi reader");
            }
            else
            {
                parse = ParseInput(line);
                System.out.println("parse: " +parse);
                switch(parse)
                {
                    case SHUTDOWN:
                        CmdShutdown();
                        break;
                    case KILL:
                        CmdKillApps();
                        break;
                }
            }
            
            cmdSocket.close();
        } catch (IOException ex) {
        }
    }
    
    private void CmdLogin()
    {
        if(Global.loginGui != null)
        {
            Global.loginGui.dispose();
            CsmclientMainGui mainGui = new CsmclientMainGui();
            mainGui.setVisible(true);
            
            Global.mainGui = mainGui;
            Global.loginGui = null;
            
            writer.println(define.SUCCESS);
        }
        else
        {
            System.out.println("fail");
            writer.println(define.FAIL);
        }
    }
    
    private void CmdLogout()
    {
        if(Global.mainGui != null)
        {
            Global.mainGui.dispose();
            CsmclientLoginGui loginGui = new CsmclientLoginGui();
            loginGui.setVisible(true);
            
            Global.loginGui = loginGui;
            Global.mainGui = null;
            
            writer.println(define.SUCCESS);
        }
        else
        {
            writer.println(define.FAIL);
        }
    }
    
    private void CmdShutdown()
    {
        writer.println(define.SUCCESS);
        
        String shutdownCmd = "shutdown.exe -s -t 1";
        if(Global.loginGui != null)
        {
            Global.loginGui.dispose();
            Global.loginGui = null;
        }
        if(Global.mainGui != null)
        {
            Global.mainGui.dispose();
            Global.mainGui = null;
        }
        try 
        {
            Runtime.getRuntime().exec(shutdownCmd);
            
        } catch (IOException ex) {
            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    private void CmdRestart()
    {
        writer.println(define.SUCCESS);
        
        String restartCmd = "shutdown.exe -r -t 1";
        if(Global.loginGui != null)
        {
            Global.loginGui.dispose();
            Global.loginGui = null;
        }
        if(Global.mainGui != null)
        {
            Global.mainGui.dispose();
            Global.mainGui = null;
        }
        try 
        {
            Runtime.getRuntime().exec(restartCmd);
            
        } catch (IOException ex) {
            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    private void CmdKillApps()
    {
        try {
            writer.println(define.SUCCESS);
            String pid = reader.readLine();
            if(pid != null)
            {
                String cmd = "taskkill /PID " + pid;
                Process proc = Runtime.getRuntime().exec(cmd);
                BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String res = input.readLine();
                if(res.startsWith("SUCCESS"))
                {
                    writer.println(define.SUCCESS);
                }
                else
                {
                    writer.println(define.FAIL);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private int ParseInput(String line)
    {
        int result = 0;
        
        if(line.equals("SHUTDOWN"))
        {
            result = SHUTDOWN;
        }
        if(line.equals("QUIT"))
        {
            result = QUIT;
        }
        if(line.equals("KILL"))
        {
            result = KILL;
        }
            
        return result;
    }
    
}
