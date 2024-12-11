/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.form;

import config.DatabaseConfig;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import raven.alerts.MessageAlerts;

/**
 *
 * @author rayag
 */
public class Form_Pengeluaran extends javax.swing.JPanel {

    DefaultTableModel tableModel;
    Connection connection = null;
    String kodeTerpilih = "";
    int aksi = 0;
    
    public Form_Pengeluaran() {
        getCon();
        String[] judul = {"Kode Pengeluaran", "Nama", "Tanggal Pengeluaran","Nama Barang", "Harga"};
        tableModel = new DefaultTableModel(judul, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
        loadDataPengeluaran("");
        initComponents();
        tbl_pengeluaran.setModel(tableModel);
    }
    
    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void enabledButton(int cc) {
        if (cc == 1) {
             addBtn.setEnabled(true);
        }
    }

    public void disabledButton() {
        addBtn.setEnabled(true);
        
    }
    
     protected void popupHandler(String popupMsg, int status, tambahPengeluaran asd) {
        if (asd != null) {
            asd.dispose();
            addBtn.setEnabled(true);
        }
        if (status == 1) {
            MessageAlerts.getInstance().showMessage("SUCCESS", popupMsg, MessageAlerts.MessageType.SUCCESS);
        } else {
            MessageAlerts.getInstance().showMessage("ERROR", popupMsg, MessageAlerts.MessageType.ERROR);
        }
        aksi = 0;
    }
    
    protected void loadDataPengeluaran(String cari){
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM pengeluaran WHERE nama_pengeluaran LIKE '%" + cari + "%'";
                ResultSet rs = st.executeQuery(query);
                tableModel.setRowCount(0);
                while (rs.next()) {
                    String[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5)};
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
        textFieldSearchOption1 = new javaswingdev.util.TextFieldSearchOption();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pengeluaran = new javax.swing.JTable();
        addBtn = new javaswingdev.util.Button();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PENGELUARAN");

        tbl_pengeluaran.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tbl_pengeluaran.setModel(tableModel);
        tbl_pengeluaran.setRowHeight(40);
        tbl_pengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pengeluaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_pengeluaran);

        addBtn.setText("TAMBAH");
        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(container1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 447, Short.MAX_VALUE)
                            .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(37, 37, 37)
                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
          tambahPengeluaran tpengeluaran = new tambahPengeluaran(this);
          tpengeluaran.setVisible(true);
          tpengeluaran.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          aksi = 1;
    }//GEN-LAST:event_addBtnActionPerformed

    private void tbl_pengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pengeluaranMouseClicked
         int selectedRow = tbl_pengeluaran.getSelectedRow();
        if (aksi == 0) {
            if (selectedRow != -1 && selectedRow < tbl_pengeluaran.getRowCount()) {
                kodeTerpilih = (String) tableModel.getValueAt(selectedRow, 0);
            }
        }
    }//GEN-LAST:event_tbl_pengeluaranMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button addBtn;
    private javaswingdev.util.Container container1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_pengeluaran;
    private javaswingdev.util.TextFieldSearchOption textFieldSearchOption1;
    // End of variables declaration//GEN-END:variables
}