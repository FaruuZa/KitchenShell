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
import javaswingdev.util.TextFieldFilter;
import javax.swing.text.AbstractDocument;

public class popupMember extends javax.swing.JFrame {

    Connection connection = null;
    String kodeMember = "";
    Form_Member memberF = null;
    boolean isEdit = false;

    public popupMember(Form_Member member, boolean isEdits) {
        initComponents();
        getCon();
        ((AbstractDocument) inputNama.getDocument()).setDocumentFilter(new TextFieldFilter("[a-z A-Z]*"));
        ((AbstractDocument) inputNomer.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        this.memberF = member;
        this.isEdit = isEdits;
        if (isEdit) {
            jLabel1.setText("EDIT MEMBER");
            this.kodeMember = memberF.kodeTerpilih;
            loadData();
        } else {
            jLabel1.setText("TAMBAH MEMBER");
        }
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
                String query = "SELECT * FROM member WHERE kode_member='" + kodeMember + "';";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    inputNama.setText(rs.getString(2));
                    inputNomer.setText(rs.getString(3));
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String generateCode() {
        String kodeMenu = "";
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT kode_member FROM member ORDER BY kode_member DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String lastKode = resultSet.getString("kode_member");
                    int kodeNum = Integer.parseInt(lastKode.substring(4)) + 1;
                    kodeMenu = String.format("MB%04d", kodeNum);
                }

                resultSet.close();
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return kodeMenu;
    }

    private void createData() {
        if (connection != null) {
            try {
                if (!inputNama.getText().equals("") && !inputNomer.getText().equals("")) {
                    Statement statement = connection.createStatement();
                    String query = "INSERT INTO member VALUES ('" + generateCode() + "','" + inputNama.getText() + "','" + inputNomer.getText() + "','0',NOW())";
                    Boolean resultSet = statement.execute(query);
                    statement.close();
                    memberF.loadDataMember("");
                    memberF.popupHandler("data berhasil ditambah!", 1, this, false);

                } else {
                    throw new Exception("data tidak boleh kosong!");
                }
            } catch (Exception e) {
                memberF.popupHandler(e.getMessage(), 0, this, false);
            }
        }

    }

    public void editData() {
        if (connection != null) {
            try {
                if (!inputNama.equals("") && !inputNomer.equals("")) {
                    Statement statement = connection.createStatement();
                    String query = "UPDATE `member` SET `nama_member` = '" + inputNama.getText() + "', `noTelp_member` = '" + inputNomer.getText() + "' WHERE `member`.`kode_member` = '" + kodeMember + "';";
                    Boolean resultSet = statement.execute(query);
                    statement.close();
                    memberF.loadDataMember("");
                    memberF.enabledButton(1);
                    memberF.popupHandler("berhasil mengedit data", 1, this, true);
                }else{
                    throw new Exception("data tidak boleh kosong");
                }
            } catch (Exception e) {
                memberF.popupHandler(e.getMessage(), 0, this, true);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        systemColor1 = new javaswingdev.system.SystemColor();
        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inputNama = new javaswingdev.util.TextField();
        jLabel3 = new javax.swing.JLabel();
        inputNomer = new javaswingdev.util.TextField();
        batalkan = new javaswingdev.util.Button();
        button3 = new javaswingdev.util.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TAMBAH MEMBER");

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

        button3.setText("SIMPAN");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
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
                        .addComponent(batalkan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))))
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2)))
            .addGroup(container1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputNomer, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(inputNomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batalkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputNomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputNomerActionPerformed

    private void inputNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputNamaActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        if (isEdit) {
            editData();
        } else {
            createData();
        }
    }//GEN-LAST:event_button3ActionPerformed

    private void batalkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalkanActionPerformed
        this.dispose();
        memberF.enabledButton(0);
        memberF.aksi = 0;
    }//GEN-LAST:event_batalkanActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button batalkan;
    private javaswingdev.util.Button button3;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField inputNama;
    private javaswingdev.util.TextField inputNomer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javaswingdev.system.SystemColor systemColor1;
    // End of variables declaration//GEN-END:variables
}
