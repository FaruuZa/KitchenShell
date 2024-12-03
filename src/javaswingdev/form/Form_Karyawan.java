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

/**
 *
 * @author MI TA
 */
public class Form_Karyawan extends javax.swing.JPanel {

    DefaultTableModel tableModel;
    Connection connection = null;
    String kodeTerpilih = "";
    int aksi = 0;

    public Form_Karyawan() {
        getCon();
        String[] judul = {"Nama", "Username"};
        tableModel = new DefaultTableModel(judul, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
            } 
        };
        loadDataKaryawan("");
        initComponents();
        tbl_karyawan.setModel(tableModel);
        editBtn.setEnabled(false);
        hapusBtn.setEnabled(false);
    }
    
    protected void popupHandler(String errorMsg, int status, tambahKaryawan asd, EditMenu dsa) {
        if (asd != null) {
            asd.dispose();
            addBtn.setEnabled(true);
        } else {
            dsa.dispose();
            enabledButton(1);
        }
        JOptionPane.showMessageDialog(null, errorMsg);
        aksi=0;
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
    
    protected void loadDataKaryawan(String cari) {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM user WHERE level = 0 AND nama LIKE '%" + cari + "%'";
                ResultSet rs = st.executeQuery(query);
                tableModel.setRowCount(0);
                while (rs.next()) {
                    String[] data = {rs.getString(2), rs.getString(3)};
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
    // </editor-fold>
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_karyawan = new javax.swing.JTable();
        editBtn = new javaswingdev.util.Button();
        hapusBtn = new javaswingdev.util.Button();
        addBtn = new javaswingdev.util.Button();
        textFieldSearchOption1 = new javaswingdev.util.TextFieldSearchOption();

        jCheckBox1.setText("jCheckBox1");

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setOpaque(false);

        container1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("KARYAWAN");

        tbl_karyawan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tbl_karyawan.setModel(tableModel);
        tbl_karyawan.setRowHeight(40);
        tbl_karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_karyawanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_karyawan);

        editBtn.setText("EDIT");
        editBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        hapusBtn.setText("HAPUS");
        hapusBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hapusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBtnActionPerformed(evt);
            }
        });

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, container1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(537, 537, 537)
                        .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hapusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(673, 673, 673)
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(textFieldSearchOption1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        tambahKaryawan tKaryawan = new tambahKaryawan(this);
        tKaryawan.setVisible(true);
        tKaryawan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        disabledButton();
    }//GEN-LAST:event_addBtnActionPerformed

    private void tbl_karyawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_karyawanMouseClicked
        int selectedRow = tbl_karyawan.getSelectedRow();
        
        if (selectedRow != -1 && selectedRow < tbl_karyawan.getRowCount()) {
            kodeTerpilih = (String) tableModel.getValueAt(selectedRow, 0);
            editBtn.setEnabled(true);
            hapusBtn.setEnabled(true);
            aksi = 1;
        }
    }//GEN-LAST:event_tbl_karyawanMouseClicked

    private void hapusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBtnActionPerformed
        int konfirm = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus Data tersebut?");
        if (konfirm == 0) {
            try {
                Statement st = connection.createStatement();
                String query = "DELETE FROM user WHERE nama='" + kodeTerpilih + "'";
                st.execute(query);
                kodeTerpilih = "";
                disabledButton();
                loadDataKaryawan("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_hapusBtnActionPerformed
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button addBtn;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.Button editBtn;
    private javaswingdev.util.Button hapusBtn;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_karyawan;
    private javaswingdev.util.TextFieldSearchOption textFieldSearchOption1;
    // End of variables declaration//GEN-END:variables

//    private void initComponents() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
