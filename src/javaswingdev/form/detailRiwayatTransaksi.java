/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaswingdev.form;

import config.DatabaseConfig;
import java.sql.*;
import javaswingdev.main.Main;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author rayag
 */
public class detailRiwayatTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form detailRiwayatTransaksi
     */
    String kodeTr = "";
    Connection connection = DatabaseConfig.getConnection();
    DefaultTableModel model;

    public detailRiwayatTransaksi(String kodeT) {
        initComponents();
        String[] judul = {"No", "Nama", "Jumlah", "Total"};
        this.kodeTr = kodeT;
        model = new DefaultTableModel(judul, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMenu.setModel(model);
        loadData();
    }

    private void loadData() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM v_struk_transaksi WHERE kode_transaksi = '" + kodeTr + "';";
                ResultSet rs = st.executeQuery(query);
                int index = 1;
                while (rs.next()) {
                    String pelanggan = rs.getString(3) == null ? rs.getString(4) : rs.getString(3) + " (member)";
                    txtPelanggan.setText(": " + pelanggan);
                    txtKasir.setText(": " + rs.getString(2));
                    fieldKode.setText(kodeTr);
                    txtTgl.setText("TANGGAL: " + rs.getString(9));
                    txtTotal.setText(": " + Main.formatRupiah(rs.getDouble(8)));
                    txtDibayar.setText(": " + "Rp0");
                    txtKembalian.setText(": Rp0");
                    String[] data = {Integer.toString(index++),rs.getString(5),rs.getString(6), Main.formatRupiah(rs.getDouble(7))};
                    model.addRow(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        container1 = new javaswingdev.util.Container();
        fieldKode = new javaswingdev.util.TextField();
        txtTgl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();
        button1 = new javaswingdev.util.Button();
        txtKasir = new javax.swing.JLabel();
        txtPelanggan = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        txtDibayar = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DETAIL TRANSAKSI");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        fieldKode.setEditable(false);
        fieldKode.setText("T2400001");
        fieldKode.setEnabled(false);
        fieldKode.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        fieldKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldKodeActionPerformed(evt);
            }
        });

        txtTgl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTgl.setForeground(new java.awt.Color(255, 255, 255));
        txtTgl.setText("TANGGAL : 15/12/2024");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("KASIR");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PELANGGAN");

        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Jumlah", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMenu.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblMenu);
        if (tblMenu.getColumnModel().getColumnCount() > 0) {
            tblMenu.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblMenu.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblMenu.getColumnModel().getColumn(2).setPreferredWidth(10);
            tblMenu.getColumnModel().getColumn(3).setPreferredWidth(20);
        }

        button1.setText("BATAL");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        txtKasir.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtKasir.setForeground(new java.awt.Color(255, 255, 255));
        txtKasir.setText(": Yuviar");

        txtPelanggan.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        txtPelanggan.setText(": Raya (member)");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TOTAL");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("DIBAYAR");

        txtTotal.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(255, 255, 255));
        txtTotal.setText(": Rp200.000,00");

        txtDibayar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtDibayar.setForeground(new java.awt.Color(255, 255, 255));
        txtDibayar.setText(": Rp200.000,00");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("KEMBALIAN");

        txtKembalian.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtKembalian.setForeground(new java.awt.Color(255, 255, 255));
        txtKembalian.setText(": Rp0");

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal)
                    .addComponent(txtDibayar)
                    .addComponent(txtKembalian))
                .addGap(30, 30, 30))
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(container1Layout.createSequentialGroup()
                            .addComponent(fieldKode, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(280, 280, 280)
                            .addComponent(txtTgl))
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(container1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, 0)
                                        .addComponent(txtPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                                    .addGroup(container1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(48, 48, 48)
                                        .addComponent(txtKasir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTgl)
                    .addComponent(fieldKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKasir)
                    .addComponent(jLabel4))
                .addGap(5, 5, 5)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPelanggan))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDibayar)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKembalian)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void fieldKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldKodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldKodeActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button button1;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField fieldKode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblMenu;
    private javax.swing.JLabel txtDibayar;
    private javax.swing.JLabel txtKasir;
    private javax.swing.JLabel txtKembalian;
    private javax.swing.JLabel txtPelanggan;
    private javax.swing.JLabel txtTgl;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
