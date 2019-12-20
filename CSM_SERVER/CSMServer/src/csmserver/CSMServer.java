package csmserver;

import structure.Global;
public class CSMServer {
    public static void main(String[] args) {
        CsmserverGui csmgui = new CsmserverGui();
        csmgui.setVisible(true);
        Global.mainGui = csmgui;
    }
}
