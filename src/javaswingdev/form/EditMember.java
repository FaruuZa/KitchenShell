/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaswingdev.form;

/**
 *
 * @author Faza
 */
import java.sql.*;
import config.DatabaseConfig;
import java.text.SimpleDateFormat;
import javaswingdev.util.TextFieldFilter;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import java.util.Date;

public class EditMember extends javax.swing.JFrame {
    Connection connection = null;
    Form_Member memberF = null;
    String kodeMember = "";
    /**
     * Creates new form EditMember
     */
    public EditMember(Form_Member tMember, String kMember) {
        initComponents();
        this.memberF = tMember;
        this.kodeMember = kMember;
        getCon();
        ((AbstractDocument) inputNama.getDocument()).setDocumentFilter(new TextFieldFilter("[a-z A-Z]*"));
        ((AbstractDocument) inputNomer.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        ((AbstractDocument) inputPoin.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        loadData();
    }
    
    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void loadData(){
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM member WHERE kode_member='"+kodeMember+"';";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    inputNama.setText(rs.getString(2));
                    inputNomer.setText(rs.getString(3));
                    inputPoin.setText(rs.getString(4));
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean editData(){
        if (connection != null) {
            try {
                if (!inputNama.equals("") || !inputNomer.equals("") || !inputPoin.equals("")) {
                    Statement statement = connection.createStatement();
                    String query = "UPDATE `member` SET `nama_member` = '"+inputNama.getText()+"', `noTelp_member` = '"+inputNomer.getText()+"', `point` = '"+inputPoin.getText()+"' WHERE `member`.`kode_member` = '"+kodeMember+"';";
                    Boolean resultSet = statement.execute(query);
//                    System.out.println(query);
//                    resultSet.close();
                    statement.close();
                    memberF.loadDataMember("");
                    memberF.enabledButton();
                    return resultSet;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inputNama = new javaswingdev.util.TextField();
        jLabel3 = new javax.swing.JLabel();
        inputNomer = new javaswingdev.util.TextField();
        batalkan = new javaswingdev.util.Button();
        simpanBtn = new javaswingdev.util.Button();
        jLabel4 = new javax.swing.JLabel();
        inputPoin = new javaswingdev.util.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EDIT MEMBER");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Member");

        inputNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNamaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("No Telepon");

        inputNomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNomerActionPerformed(evt);
            }
        });

        batalkan.setText("BATAL");
        batalkan.setFocusable(false);
        batalkan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        batalkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalkanActionPerformed(evt);
            }
        });

        simpanBtn.setText("SIMPAN");
        simpanBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        simpanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Poin");

        inputPoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputPoinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(batalkan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simpanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))))
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(inputNomer, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(inputPoin, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batalkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputNamaActionPerformed

    private void inputNomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputNomerActionPerformed

    private void batalkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalkanActionPerformed
        this.setVisible(false);
        memberF.enabledButton();
    }//GEN-LAST:event_batalkanActionPerformed

    private void simpanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBtnActionPerformed
        memberF.enabledButton();
        if (editData()) {
            this.setVisible(false);
        } else {
            this.setVisible(false);
        }
    }//GEN-LAST:event_simpanBtnActionPerformed

    private void inputPoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPoinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputPoinActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button batalkan;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField inputNama;
    private javaswingdev.util.TextField inputNomer;
    private javaswingdev.util.TextField inputPoin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javaswingdev.util.Button simpanBtn;
    // End of variables declaration//GEN-END:variables
}
