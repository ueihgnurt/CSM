package csmclient;
import action.SetTime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.Global;
import structure.define;
public class CsmclientMainGui extends javax.swing.JFrame {

    private Socket clLoginSocket;
    Calendar startCal;
    SetTime settime;
    public CsmclientMainGui() {
        initComponents();
        
        clLoginSocket = Global.loginSocket;
        
        startCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String starttime = sdf.format(startCal.getTime());
        long timelilis = startCal.getTimeInMillis();
        
        txt_start_time.setText(starttime);
        settime = new SetTime(timelilis);
        settime.start();
        
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        txt_start_time = new javax.swing.JTextField();
        txt_use_time = new javax.swing.JTextField();
        txt_cost = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        clock = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSM CLIENT");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                onclosewindows(evt);
            }
        });

        txt_start_time.setEditable(false);
        txt_start_time.setPreferredSize(new java.awt.Dimension(70, 20));

        txt_use_time.setEditable(false);
        txt_use_time.setPreferredSize(new java.awt.Dimension(70, 20));

        txt_cost.setEditable(false);
        txt_cost.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabel1.setText("Thời gian bắt đầu");

        jLabel2.setText("Thời gian sử dụng (phút)");

        jLabel4.setText("Phí dịch vụ (VND)");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Clock"));

        clock.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clock)
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clock)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_start_time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_use_time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_start_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_use_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pack();
    }

    private void bt_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_logoutActionPerformed
        BufferedReader reader = null;
        PrintWriter writer = null;
                
        try {
            reader = new BufferedReader(new InputStreamReader(clLoginSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clLoginSocket.getOutputStream()), true);
            
            writer.println("LOGOUT");
            writer.flush();
            
            String response = reader.readLine();
            if(response.startsWith(define.SUCCESS))
            {
                settime.stop();
                this.dispose();
                CsmclientLoginGui logingui = new CsmclientLoginGui();
                logingui.setVisible(true);
                Global.loginGui = logingui;
                
                System.out.println("LOGOUT finish");
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(CsmclientMainGui.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_bt_logoutActionPerformed

    @SuppressWarnings("deprecation")
	private void onclosewindows(java.awt.event.WindowEvent evt) {
        settime.stop();
    }
    public javax.swing.JLabel clock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JTextField txt_cost;
    private javax.swing.JTextField txt_start_time;
    public javax.swing.JTextField txt_use_time;
}