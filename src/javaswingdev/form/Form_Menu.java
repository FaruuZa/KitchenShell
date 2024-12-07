/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.alerts.MessageAlerts;

/**
 *
 * @author MI TA
 */
public class Form_Menu extends javax.swing.JPanel {

    DefaultTableModel tableModel;
    Connection connection = null;
    String kodeTerpilih = "";
    int aksi = 0;

    public Form_Menu() {
        getCon();
        String[] judul = {"Kode Menu ", "Menu", "Stok", "Harga"};
        tableModel = new DefaultTableModel(judul, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadDataMenu("");
        initComponents();
        tbl_menu.setModel(tableModel);
        editBtn.setEnabled(false);
        hapusBtn.setEnabled(false);
    }

    protected void popupHandler(String popupMsg, int status, tambahMenu asd, EditMenu dsa) {
        if (asd != null) {
            asd.dispose();
            addBtn.setEnabled(true);
        } else if (dsa != null) {
            dsa.dispose();
            enabledButton(1);
        }
        if (status == 1) {
            MessageAlerts.getInstance().showMessage("SUCCESS", popupMsg, MessageAlerts.MessageType.SUCCESS);
        } else {
            MessageAlerts.getInstance().showMessage("ERROR", popupMsg, MessageAlerts.MessageType.ERROR);
        }
//        JOptionPane.showMessageDialog(null, popupMsg);
        aksi = 0;
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
            editBtn.setEnabled(true);
            hapusBtn.setEnabled(true);
        }
        addBtn.setEnabled(true);
    }

    public void disabledButton() {
        addBtn.setEnabled(false);
        editBtn.setEnabled(false);
        hapusBtn.setEnabled(false);
    }

    protected void loadDataMenu(String cari) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM menu WHERE nama_menu LIKE '%" + cari + "%'";
                ResultSet rs = st.executeQuery(query);
                tableModel.setRowCount(0);
                while (rs.next()) {
                    String[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                    tableModel.addRow(data);
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("kon");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container4 = new javaswingdev.util.Container();
        addBtn = new javaswingdev.util.Button();
        textFieldSearchOption1 = new javaswingdev.util.TextFieldSearchOption();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_menu = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        hapusBtn = new javaswingdev.util.Button();
        editBtn = new javaswingdev.util.Button();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(false);

        container4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addBtn.setText("TAMBAH");
        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        textFieldSearchOption1.setColorOverlay1(new java.awt.Color(255, 255, 55));
        textFieldSearchOption1.setColorOverlay2(new java.awt.Color(0, 220, 0));
        textFieldSearchOption1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSearchOption1ActionPerformed(evt);
            }
        });

        tbl_menu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_menu.setModel(tableModel);
        tbl_menu.setRowHeight(40);
        tbl_menu.setSelectionBackground(new java.awt.Color(51, 204, 0));
        tbl_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_menuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_menu);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MENU");

        hapusBtn.setText("HAPUS");
        hapusBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hapusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBtnActionPerformed(evt);
            }
        });

        editBtn.setText("EDIT");
        editBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout container4Layout = new javax.swing.GroupLayout(container4);
        container4.setLayout(container4Layout);
        container4Layout.setHorizontalGroup(
            container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(container4Layout.createSequentialGroup()
                        .addGroup(container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(container4Layout.createSequentialGroup()
                                .addComponent(hapusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(600, 600, 600)
                        .addGroup(container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        container4Layout.setVerticalGroup(
            container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(container4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hapusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed

        tambahMenu tmenu = new tambahMenu(this);
        tmenu.setVisible(true);
        tmenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        disabledButton();
        aksi = 1;
    }//GEN-LAST:event_addBtnActionPerformed

    private void textFieldSearchOption1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSearchOption1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldSearchOption1ActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        EditMenu emenu = new EditMenu(this, kodeTerpilih);
        emenu.setVisible(true);
        emenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        disabledButton();
        aksi = 1;
    }//GEN-LAST:event_editBtnActionPerformed

    private void tbl_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_menuMouseClicked
        int selectedRow = tbl_menu.getSelectedRow();
        if (aksi == 0) {
            if (selectedRow != -1 && selectedRow < tbl_menu.getRowCount()) {
                kodeTerpilih = (String) tableModel.getValueAt(selectedRow, 0);
                editBtn.setEnabled(true);
                hapusBtn.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tbl_menuMouseClicked

    private void hapusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBtnActionPerformed
        int konfirm = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus Data tersebut?");
        if (konfirm == 0) {
            try {
                Statement st = connection.createStatement();
                String query = "DELETE FROM menu WHERE kode_menu='" + kodeTerpilih + "'";
                st.execute(query);
                kodeTerpilih = "";
                disabledButton();
                enabledButton(0);
                loadDataMenu("");
                popupHandler("berhasil menghapus data", 1, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_hapusBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button addBtn;
    private javaswingdev.util.Container container4;
    private javaswingdev.util.Button editBtn;
    private javaswingdev.util.Button hapusBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_menu;
    private javaswingdev.util.TextFieldSearchOption textFieldSearchOption1;
    // End of variables declaration//GEN-END:variables

//    private void disabledButton() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//    private void loadDataMember() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
