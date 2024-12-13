package javaswingdev.form;

import java.sql.*;
import javaswingdev.main.Main;
import config.DatabaseConfig;
import javaswingdev.util.TextFieldFilter;
import javax.swing.text.AbstractDocument;
import config.Session;
import raven.alerts.MessageAlerts;

public class Form_Login extends javax.swing.JPanel {

    Connection connection = null;
    Main mainf = null;

    public Form_Login(Main main) {
        initComponents();
        getCon();
        ((AbstractDocument) userInput.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9A-Za-z]*"));
        ((AbstractDocument) passInput.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9A-Za-z]*"));
        this.mainf = main;
//        lb.setText("Form " + name);
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cobaLogin() {
        try {
            String usn = userInput.getText();
            String pass = passInput.getText();
            if (usn.equals("")) {
                throw new Exception("Username tidak boleh kosong!");

            } else if (pass.equals("")) {
                throw new Exception("Password tidak boleh kosong!");

            }
            String query = "SELECT * FROM user WHERE username = ? AND password = ? LIMIT 1";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, usn);
                ps.setString(2, pass);
                ResultSet hasil = ps.executeQuery();
                if (hasil.next()) {
                    do {
                        Session.setKode(hasil.getString("id"));
                        Session.setRole(hasil.getInt("level"));
//                        System.out.println(Session.getRole());
                        mainf.init();
                    } while (hasil.next());
                } else {
                    throw new Exception("Username atau password salah");
                }
            }

        } catch (Exception e) {
            //            System.out.println(e.getMessage());
            MessageAlerts.getInstance().showMessage("ERROR", e.getMessage(), MessageAlerts.MessageType.ERROR);
            //            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passInput = new javaswingdev.util.PasswordField();
        userInput = new javaswingdev.util.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        LoginBtn = new javaswingdev.util.Button();
        jLabel4 = new javax.swing.JLabel();
        show = new javax.swing.JCheckBox();

        setOpaque(false);

        passInput.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        passInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passInputKeyPressed(evt);
            }
        });

        userInput.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInputActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Password");

        LoginBtn.setText("LOGIN");
        LoginBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 52)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("LOGIN");

        show.setForeground(new java.awt.Color(255, 255, 255));
        show.setText("Show Password");
        show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(userInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passInput, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(show, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(125, 125, 125)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(LoginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel4)
                .addGap(65, 65, 65)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userInput, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passInput, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show)
                .addGap(38, 38, 38)
                .addComponent(LoginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void userInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userInputActionPerformed

    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginBtnActionPerformed
        cobaLogin();
    }//GEN-LAST:event_LoginBtnActionPerformed

    private void showActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showActionPerformed
        if (show.isSelected()) {
            passInput.setEchoChar((char) 0); //password = JPasswordField
        } else {
            passInput.setEchoChar('*');
        }
    }//GEN-LAST:event_showActionPerformed

    private void passInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passInputKeyPressed
        if (evt.getKeyCode() == 10) {
            cobaLogin();
        }
//        System.out.println(evt.getKeyCode() + " " + evt.getKeyChar() );
    }//GEN-LAST:event_passInputKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button LoginBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javaswingdev.util.PasswordField passInput;
    private javax.swing.JCheckBox show;
    private javaswingdev.util.TextField userInput;
    // End of variables declaration//GEN-END:variables
}
