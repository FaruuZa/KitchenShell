/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.form;

import config.DatabaseConfig;
import java.sql.*;
import javaswingdev.main.Main;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MI TA
 */
public class Form_RiwayatTransaksi extends javax.swing.JPanel {

    DefaultTableModel tableModel;
    Connection connection = null;
    String kodeTerpilih = "";
    int aksi = 0;

    public Form_RiwayatTransaksi() {
        initComponents();
        getCon();
        String[] judul = {"Kode Transaksi", "Nama Pelanggan", "Total Transaksi" , "Tanggal Transaksi"};
        tableModel = new DefaultTableModel(judul, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadDataRiwayatTransaksi();
        tbl_riwayatTransaksi.setModel(tableModel);
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadDataRiwayatTransaksi() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT transaksi.kode_transaksi,member.nama_member, transaksi.nama_pelanggan, transaksi.total_transaksi, transaksi.tnggl_transaksi FROM transaksi"
                        + " LEFT JOIN member ON member.kode_member=transaksi.kode_member";
                ResultSet rs = st.executeQuery(query);
                tableModel.setRowCount(0);
                while (rs.next()) {
                    String pelanggan = rs.getString(2) == null ? rs.getString(3) : "👤 "+rs.getString(2);
                    String[] data = {rs.getString(1), pelanggan, Main.formatRupiah(rs.getDouble(4)) , rs.getString(5)};
                    tableModel.addRow(data);
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
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

        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        button1 = new javaswingdev.util.Button();
        textFieldSearchOption1 = new javaswingdev.util.TextFieldSearchOption();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_riwayatTransaksi = new javax.swing.JTable();

        setOpaque(false);

        container1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("RIWAYAT TRANSAKSI");

        button1.setText("DETAIL");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        textFieldSearchOption1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSearchOption1ActionPerformed(evt);
            }
        });

        tbl_riwayatTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_riwayatTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_riwayatTransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_riwayatTransaksi);

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(15, 15, 15))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(270, 270, 270)
                        .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        if(!kodeTerpilih.equals("")){
            new detailRiwayatTransaksi(kodeTerpilih).setVisible(true);
            tbl_riwayatTransaksi.clearSelection();
        }
    }//GEN-LAST:event_button1ActionPerformed

    private void textFieldSearchOption1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSearchOption1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldSearchOption1ActionPerformed

    private void tbl_riwayatTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_riwayatTransaksiMouseClicked
        int selectedRow = tbl_riwayatTransaksi.getSelectedRow();

        if (selectedRow != -1 && selectedRow < tbl_riwayatTransaksi.getRowCount()) {
            kodeTerpilih = (String) tableModel.getValueAt(selectedRow, 0);
            
        }
    }//GEN-LAST:event_tbl_riwayatTransaksiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button button1;
    private javaswingdev.util.Container container1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_riwayatTransaksi;
    private javaswingdev.util.TextFieldSearchOption textFieldSearchOption1;
    // End of variables declaration//GEN-END:variables
}
