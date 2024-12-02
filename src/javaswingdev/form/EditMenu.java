/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaswingdev.form;

import config.DatabaseConfig;
import java.sql.*;
import javaswingdev.util.TextFieldFilter;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author rayag
 */
public class EditMenu extends javax.swing.JFrame {

    Connection connection = null;
    Form_Menu menuF = null;
    String kodeMenu = "";

    /**
     * Creates new form EditMenu
     */
    public EditMenu(Form_Menu tMenu, String kMenu) {
        initComponents();
        this.menuF = tMenu;
        this.kodeMenu = kMenu;
        getCon();
        ((AbstractDocument) namaInput.getDocument()).setDocumentFilter(new TextFieldFilter("[a-z A-Z]*"));
        ((AbstractDocument) hargaInput.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9.]*"));
        ((AbstractDocument) jumlahInput.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        loadData();
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM menu WHERE kode_menu='" + kodeMenu + "';";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    namaInput.setText(rs.getString(2));
                    hargaInput.setText(rs.getString(4));
                    jumlahInput.setText(rs.getString(3));
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void editData() {
        if (connection != null) {
            try {
                if (!namaInput.equals("") || !jumlahInput.equals("") || !hargaInput.equals("")) {
                    Statement statement = connection.createStatement();
                    String query = "UPDATE `Menu` SET `nama_menu` = '" + namaInput.getText() + "', `stok_menu` = '" + jumlahInput.getText() + "', `harga` = '" + hargaInput.getText() + "' WHERE `menu`.`kode_menu` = '" + kodeMenu + "';";
                    statement.execute(query);
                    statement.close();
                    menuF.setEnabled(true);
                    menuF.loadDataMenu("");
                    menuF.enabledButton("");
                    menuF.errorPopup("Data berhasil diedit!", null, this);          
                } else {
                    throw new Exception("Data tidak boleh kosong!");
                }
            } catch (Exception e) {
                menuF.errorPopup(e.getMessage(), null, this);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container1 = new javaswingdev.util.Container();
        btn_simpan = new javaswingdev.util.Button();
        jLabel1 = new javax.swing.JLabel();
        jumlahInput = new javaswingdev.util.TextField();
        jLabel2 = new javax.swing.JLabel();
        btn_batall = new javaswingdev.util.Button();
        namaInput = new javaswingdev.util.TextField();
        jLabel3 = new javax.swing.JLabel();
        hargaInput = new javaswingdev.util.TextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);

        btn_simpan.setText("SIMPAN");
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed1(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EDIT MENU");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Menu");

        btn_batall.setText("BATAL");
        btn_batall.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_batall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batallbutton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Harga");

        hargaInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaInputActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Stok");

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(container1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                        .addComponent(btn_batall, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hargaInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jumlahInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(namaInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(hargaInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(jumlahInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_batallbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batallbutton1ActionPerformed
        this.setVisible(false);
        menuF.enabledButton("");
    }//GEN-LAST:event_btn_batallbutton1ActionPerformed

    private void btn_simpanActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed1
        editData();
    }//GEN-LAST:event_btn_simpanActionPerformed1

    private void hargaInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaInputActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(EditMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(EditMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(EditMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(EditMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new EditMenu().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btn_batall;
    private javaswingdev.util.Button btn_simpan;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField hargaInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javaswingdev.util.TextField jumlahInput;
    private javaswingdev.util.TextField namaInput;
    // End of variables declaration//GEN-END:variables
}
