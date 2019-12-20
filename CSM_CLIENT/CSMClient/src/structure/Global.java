package structure;

import java.net.Socket;
public class Global {
    public static csmclient.CsmclientLoginGui loginGui = null;
    public static csmclient.CsmclientMainGui mainGui = null;
    public static Socket    loginSocket = null;
    public static Socket    cmdSocket = null;
}
