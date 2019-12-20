package Action;

import database.ConnectSqlServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import structure.Global;
import structure.define;

public class LoginFromClient extends Thread{

    private static final int    WAIT_INFO = 0;
    private static final int    WAIT_USERNAME = 1;
    private static final int    WAIT_PASSWORD = 2;
    private static final int    WAIT_LOGOUT = 3;
    
    private Socket              cSocket;
    private BufferedReader      reader;
    private PrintWriter         writer;
    
    private String              idClient;
    private String              username;
    private String              password;
    public LoginFromClient(Socket socket) throws IOException
    {
        this.cSocket = socket;
        this.reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()), true);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void run()
    {
        boolean finish = false;
        
        String line;
        try
        {
            while(true)
            {
                line = reader.readLine();
                System.out.println(line);
                
                int state = ParseInput(line); 
                if(line.equals("QUIT"))
                {
                    break;
                }
                else
                {
                    switch(state)
                    {
                        case WAIT_INFO:
                            finish = CmdInfo(line);
                            break;
                        case WAIT_USERNAME:
                            finish = CmdUser(line);
                            break;
                        case WAIT_PASSWORD:
                            finish = CmdPassword(line);
                            break;
                        case WAIT_LOGOUT:
                            finish = CmdLogout(line);
                            break;
                    }
                }
            }
            cSocket.close();
        }catch(IOException ex){
        }
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 1);
        Global.mainGui.main_table.setValueAt(define.DISCONNECT, Integer.parseInt(idClient), 2);
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 3);
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 4);
        if(Global.threadtime != null)
        {
            Global.threadtime.stop();
            Global.threadtime = null;
        }
        
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 5);
        System.out.println("thread finish");
        
    }
    
    private boolean CmdInfo(String line)
    {
        boolean result = false;
        String ipclient = cSocket.getInetAddress().getHostAddress();
        idClient = "";
        if(line.startsWith("INFO"))
        {
            idClient = GetParameter(line);
            
            Global.mainGui.main_table.setValueAt(ipclient, Integer.parseInt(idClient), 1);
            Global.mainGui.main_table.setValueAt(define.OFFLINE, Integer.parseInt(idClient), 2);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 3);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 4);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 5);
            reply(define.SUCCESS, "INFO command success.");
            result = false;
        }
        else
        {
            reply(define.FAIL, "INFO command fail.");
            result = false;
        }
        
        return result;
    }
    
    @SuppressWarnings("deprecation")
	private boolean CmdLogout(String line)
    {
        boolean result = false;
        
        if(line.startsWith("LOGOUT"))
        {
            Global.mainGui.main_table.setValueAt(define.OFFLINE, Integer.parseInt(idClient), 2);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 3);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 4);
            
            
            if(Global.threadtime != null)
            {
                Global.threadtime.stop();;
                Global.threadtime = null;
            }
            
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 5);
            
            reply(define.SUCCESS, "Logout success.");
        }
        
        System.out.println("LOGOUT finish.");
        return result;
    }
    
    private boolean CmdUser(String line)
    {
        boolean result = false;
        
        if(line.startsWith("USER "))
        {
            username = GetParameter(line);
            reply(define.SUCCESS, "USER command success.");
            result = false;
        }
        else
        {
            reply(define.FAIL, "USER command fail.");
            result = false;
        }
        
        return result;
    }
    
    private boolean  CmdPassword(String line)
    {
        boolean result = false;
        if(line.startsWith("PASS "))
        {
            password  = GetParameter(line);
            if(CheckUserPass() == true)
            {
                reply(define.SUCCESS, "Login successful.");
                
                Calendar startCal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String startTime = sdf.format(startCal.getTime());
                long start = startCal.getTimeInMillis();
                
                Global.mainGui.main_table.setValueAt(define.ONLINE, Integer.parseInt(idClient), 2);
                Global.mainGui.main_table.setValueAt(username, Integer.parseInt(idClient), 3);
                Global.mainGui.main_table.setValueAt(startTime, Integer.parseInt(idClient), 4);
                
                SetTime setTime = new SetTime(start, Integer.parseInt(idClient));
                setTime.start();
                Global.threadtime = setTime;
                
                result = false;
            }
            else
            {
                reply(define.FAIL, "Login fail. Username or password incorrect.");
                result = false;
            }
        }
        return result;
    }
    
    private boolean CheckUserPass()
    {
        boolean result = false;
        try {
            
            if(!username.equals(""))
            {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();

                String sql = String.format("SELECT * FROM account WHERE username='%s'", username);
                ResultSet r = statement.executeQuery(sql);
                String pass = "";
                if(r.first())
                {
                    pass = r.getString("password");

                }

                if(password.equals(pass))
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
            }
        } catch (SQLException ex) {
            result = false;
        }
        
        return result;
    }
    
    private String GetParameter(String line)
    {
        String param;
        int p = 0;
        p = line.indexOf(" ");
        
        if(p >= line.length() || p ==-1) {
            param = "";
        }
        else {
            param = line.substring(p+1,line.length());
        }
        
        return param;
    }
    
    private int ParseInput(String line)
    {
        int result = WAIT_INFO;
        
        if(line.startsWith("INFO"))
        {
            result = WAIT_INFO;
        }
        if(line.startsWith("USER"))
        {
            result = WAIT_USERNAME;
        }
        if(line.startsWith("PASS"))
        {
            result = WAIT_PASSWORD;
        }
        if(line.startsWith("LOGOUT"))
        {
            result = WAIT_LOGOUT;
        }
        
        return result;
    }
    void reply(String code, String text) {
        writer.println(code + " " + text);
    }
}
