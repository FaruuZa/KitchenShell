/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import javaswingdev.util.TextFieldFilter;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author rayag
 */
public class popupAkun extends javax.swing.JFrame {

    Connection connection = null;
    Form_Akun karyawanF = null;
    boolean isEdit = false;
    String kode = "";

    public popupAkun(Form_Akun Fkaryawan, boolean isEdits) {
        getCon();
        initComponents();
        this.karyawanF = Fkaryawan;
        this.isEdit = isEdits;
        if (isEdit) {
            this.kode = karyawanF.kodeTerpilih;
            inputPassword.setEditable(false);
            jLabel1.setText("EDIT AKUN");
            loadData();
        } else {
            jLabel1.setText("TAMBAH AKUN");
            inputPassword.setEditable(true);
        }

        ((AbstractDocument) inputNama.getDocument()).setDocumentFilter(new TextFieldFilter("[a-zA-Z ] *"));
        ((AbstractDocument) inputUsername.getDocument()).setDocumentFilter(new TextFieldFilter("[a-zA-Z ] *"));
        ((AbstractDocument) inputPassword.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9a-zA-Z ] *"));
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
                System.out.println(kode);
                Statement st = connection.createStatement();
                String query = "SELECT * FROM user WHERE id = '" + kode + "';";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    inputNama.setText(rs.getString(2));
                    inputUsername.setText(rs.getString(3));
                    inputPassword.setText(rs.getString(4));
                    if (rs.getInt(5) == 1) {
                        rdAd.setSelected(true);
                    } else {
                        rdKar.setSelected(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createData() {
        try {
            int hak = -1;
            if (rdAd.isSelected()) {
                hak = 1;
            } else {
                hak = 0;
            }
            if (!inputNama.getText().equals("") && !inputUsername.getText().equals("") && !inputPassword.getText().equals("") && hak != -1) {
                Statement statement = connection.createStatement();
                String query = "INSERT INTO user (nama, username, password, level) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, inputNama.getText());
                ps.setString(2, inputUsername.getText());
                ps.setString(3, inputPassword.getText());
                ps.setInt(4, hak);
                ps.execute();
                karyawanF.loadDataKaryawan("");
                karyawanF.popupHandler("Data berhasil ditambahkan", 1, this, false);
            } else {
                throw new Exception("Data tidak boleh kosong");
            }
        } catch (Exception e) {
            karyawanF.popupHandler(e.getMessage(), 0, this, false);
        }
    }

    private void editData() {
        if (connection != null) {
            try {
                if (!inputNama.equals("") && !inputUsername.equals("") && !inputPassword.equals("")) {
                    Statement statement = connection.createStatement();
//                    String query = "UPDATE 'user' SET 'nama' = '" + inputNama.getText() + "', 'username' = '" + inputUsername.getText() + "', 'password' = '" + inputPassword.getText() + "' ";
                    String query = "UPDATE user SET nama = ?, username = ?, level = ? WHERE id=?";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, inputNama.getText());
                    ps.setString(2, inputUsername.getText());
//                    ps.setString(3, query);
                    if (rdAd.isSelected()) {
                        ps.setInt(3, 1);
                    } else {
                        ps.setInt(3, 0);
                    }
                    ps.setString(4, kode);
                    ps.executeUpdate();
                    statement.close();
                    karyawanF.loadDataKaryawan("");
                    karyawanF.enabledButton(0);
                    karyawanF.popupHandler("Data berhasil diedit!", 1, this, true);
                } else {
                    throw new Exception("Data tidak boleh kosong!");
                }
            } catch (Exception e) {
                karyawanF.popupHandler(e.getMessage(), 0, this, true);
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdHak = new javax.swing.ButtonGroup();
        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        inputNama = new javaswingdev.util.TextField();
        inputUsername = new javaswingdev.util.TextField();
        inputPassword = new javaswingdev.util.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnBatal = new javaswingdev.util.Button();
        btnSimpan = new javaswingdev.util.Button();
        rdAd = new javax.swing.JRadioButton();
        rdKar = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TAMBAH AKUN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Password");

        btnBatal.setText("BATAL");
        btnBatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setText("SIMPAN");
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        rdHak.add(rdAd);
        rdAd.setForeground(new java.awt.Color(255, 255, 255));
        rdAd.setText("Admin");
        rdAd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAdActionPerformed(evt);
            }
        });

        rdHak.add(rdKar);
        rdKar.setForeground(new java.awt.Color(255, 255, 255));
        rdKar.setSelected(true);
        rdKar.setText("Karyawan");
        rdKar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdKarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Hak Akses");

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, container1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(0, 1, Short.MAX_VALUE)
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(134, 134, 134)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(container1Layout.createSequentialGroup()
                                        .addComponent(rdAd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdKar))
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(inputUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdAd)
                    .addComponent(rdKar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rdKarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdKarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdKarActionPerformed

    private void rdAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdAdActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (isEdit) {
            editData();
        } else {
            createData();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
        karyawanF.enabledButton(0);
        karyawanF.aksi = 0;
    }//GEN-LAST:event_btnBatalActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btnBatal;
    private javaswingdev.util.Button btnSimpan;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField inputNama;
    private javaswingdev.util.TextField inputPassword;
    private javaswingdev.util.TextField inputUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton rdAd;
    private javax.swing.ButtonGroup rdHak;
    private javax.swing.JRadioButton rdKar;
    // End of variables declaration//GEN-END:variables
}
