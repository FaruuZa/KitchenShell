/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import javaswingdev.util.TextFieldFilter;
import javax.swing.text.AbstractDocument;

public class popupBahanbaku extends javax.swing.JFrame {

    Connection connection = null;
    Form_BahanBaku bahanBakuF = null;
    boolean isEdit = false;
    String kode = "";

    public popupBahanbaku(Form_BahanBaku FbahanBaku, String kode, boolean isEdits) {
        initComponents();
        getCon();
        this.isEdit = isEdits;
        if (isEdit) {
            loadData(kode);
            jLabel1.setText("EDIT");
        } else {
            jLabel1.setText("TAMBAH");
            rdgram.setSelected(true);
        }
        this.bahanBakuF = FbahanBaku;
        ((AbstractDocument) inputNama.getDocument()).setDocumentFilter(new TextFieldFilter("[a-zA-Z ]*"));
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateCode() {
        String kodebahanBaku = "";
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT kode_bahanbaku FROM bahanbaku ORDER BY kode_bahanbaku DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String lastKode = resultSet.getString("kode_bahanbaku");
                    int kodeNum = Integer.parseInt(lastKode.substring(3)) + 1;
                    kodebahanBaku = String.format("BHN%03d", kodeNum);
                }
                resultSet.close();
                statement.close();
                return kodebahanBaku;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kodebahanBaku;
    }

    private void loadData(String kode) {
        if (connection != null) {
            try {
                this.kode = kode;
//                System.out.println(kode);
                Statement st = connection.createStatement();
                String query = "SELECT * FROM bahanbaku WHERE kode_bahanbaku = '" + kode + "';";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    inputNama.setText(rs.getString(2));
                    if (rs.getString(4).equals("g")) {
                        rdgram.setSelected(true);
                    } else {
                        rdml.setSelected(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createData() {
        if (connection != null) {
            try {
                if (!inputNama.getText().equals("")) {
                    String query = "INSERT INTO bahanbaku VALUES (?, ?, 0, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, generateCode());
                    statement.setString(2, inputNama.getText());
                    statement.setString(3, rdgram.isSelected() ? "g" : "ml");
                    statement.execute();
                    statement.close();
                    bahanBakuF.loadDatabahanBaku("");
                    bahanBakuF.popupHandler("Bahan berhasil ditambah", 1, false);
                    this.dispose();
                } else {
                    throw new Exception("Bahan tidak boleh kosong");
                }
            } catch (Exception e) {
                bahanBakuF.popupHandler(e.getMessage(), 0, false);
                this.dispose();
            }
        }
    }

    private void editData() {
        if (connection != null) {
            try {
                if (!inputNama.equals("")) {
                    String query = "UPDATE bahanbaku SET nama_bahanbaku = ?, satuan = ? WHERE kode_bahanbaku = ? ;";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, inputNama.getText());
                    statement.setString(2, rdgram.isSelected() ? "g" : "ml");
                    statement.setString(3, kode);
                    statement.execute();
                    bahanBakuF.loadDatabahanBaku("");
                    bahanBakuF.popupHandler("Data berhasil diedit!", 1, true);
                    this.dispose();
                } else {
                    throw new Exception("Data tidak boleh kosong!");
                }
            } catch (Exception e) {
                bahanBakuF.popupHandler(e.getMessage(), 0, true);
                this.dispose();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inputNama = new javaswingdev.util.TextField();
        jLabel4 = new javax.swing.JLabel();
        batalBtn = new javaswingdev.util.Button();
        simpanBtn = new javaswingdev.util.Button();
        rdgram = new javax.swing.JRadioButton();
        rdml = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TAMBAH");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("BAHAN BAKU");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Bahan");

        inputNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNamaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Satuan");

        batalBtn.setText("BATAL");
        batalBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        batalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalBtnActionPerformed(evt);
            }
        });

        simpanBtn.setText("SIMPAN");
        simpanBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        simpanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBtnActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdgram);
        rdgram.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rdgram.setForeground(new java.awt.Color(255, 255, 255));
        rdgram.setText("gram");

        buttonGroup1.add(rdml);
        rdml.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rdml.setForeground(new java.awt.Color(255, 255, 255));
        rdml.setText("ml");

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(rdgram)
                        .addGap(20, 20, 20)
                        .addComponent(rdml))
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(container1Layout.createSequentialGroup()
                            .addComponent(batalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                            .addComponent(simpanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(inputNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdgram)
                    .addComponent(rdml))
                .addGap(38, 38, 38)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
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
            .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputNamaActionPerformed

    private void batalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalBtnActionPerformed
        bahanBakuF.enabledButton(0);
        bahanBakuF.aksi = 0;
        this.dispose();
    }//GEN-LAST:event_batalBtnActionPerformed

    private void simpanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBtnActionPerformed

        if (isEdit) {
            editData();
        } else {
            createData();
        }
    }//GEN-LAST:event_simpanBtnActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button batalBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField inputNama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton rdgram;
    private javax.swing.JRadioButton rdml;
    private javaswingdev.util.Button simpanBtn;
    // End of variables declaration//GEN-END:variables
}
