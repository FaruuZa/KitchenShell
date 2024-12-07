package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import config.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.util.TextFieldFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import raven.alerts.MessageAlerts;

/**
 *
 * @author MI TA
 */
public class Form_Transaksi extends javax.swing.JPanel {

    DefaultTableModel tableModel, selectedTableMenu;
    Connection connection = DatabaseConfig.getConnection();
    private double totalBayar, token;

    public Form_Transaksi() {
        initComponents();
        String[] titleTblMenu = {"Kode menu ", "Nama Menu", "Stok", "Harga"};
        String[] titleTblPesanan = {"Kode menu ", "Nama Menu", "Jumlah", "Harga", "Total Harga"};
        ((AbstractDocument) txt_jumlah.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        ((AbstractDocument) txt_bayar.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        tableModel = new DefaultTableModel(titleTblMenu, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        selectedTableMenu = new DefaultTableModel(titleTblPesanan, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl_menu.setModel(tableModel);
        tbl_pesanan.setModel(selectedTableMenu);
        cbox_member.addItem("Pilih Member");
        cbox_member.setSelectedIndex(0);

        loadDataMenu();
        loadDataMember();
    }

    private void loadDataMenu() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM menu WHERE stok_menu != 0";
                ResultSet rs = st.executeQuery(query);
                tableModel.setRowCount(0);
                while (rs.next()) {
                    String[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                    tableModel.addRow(data);
                }
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDataMember() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM member";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String kode_member = rs.getString("kode_member");
                    String nama_member = rs.getString("nama_member");
                    double point = rs.getDouble("point");
                    cbox_member.addItem(kode_member + " | " + nama_member + " | " + point);
                }
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void tambahPesanan() {
        if (connection != null) {
            try {
                String kode_menu = txt_kodeMenu.getText();
                String query = "SELECT kode_menu, nama_menu , harga, stok_menu FROM menu WHERE kode_menu = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, kode_menu);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String kodeMenu = rs.getString(1);
                    String namaMenu = rs.getString(2);
                    int jumlah = Integer.parseInt(txt_jumlah.getText());
                    double harga = Double.parseDouble(rs.getString(3));
                    double totalHarga = jumlah * harga;
                    int stok = rs.getInt(4);
                    boolean cekStok = true;
                    boolean cekKode = false;
                    if (jumlah < stok) {
                        //Cek kode_menu jika sama akan menambah jumlah pesanan
                        int row = tbl_pesanan.getRowCount();
                        for (int i = 0; i < row; i++) {
                            int stokSelect = (int) tbl_pesanan.getValueAt(i, 2);
                            if (tbl_pesanan.getValueAt(i, 0).equals(kodeMenu) && stokSelect < stok) {
                                int jumlahBaru = Integer.parseInt(tbl_pesanan.getValueAt(i, 2).toString());
//                                if (stokSelect <= stok && jumlahBaru <= stok) {
                                tbl_pesanan.setValueAt(jumlahBaru + jumlah, i, 2);
                                double total = (jumlahBaru + jumlah) * harga;
                                tbl_pesanan.setValueAt(total, i, 4);
                                cekKode = true;
                                cekStok = false;
                                break;
//                                } else {
//                                    MessageAlerts.getInstance().showMessage("GAGAL MENAMBAH PESANAN!", "Tolong cek stok terlebih dahulu sebelum menambah pesanan.", MessageAlerts.MessageType.ERROR);
//                                }
                            }
                        }

                        if (!cekKode && cekStok) {
                            selectedTableMenu.addRow(new Object[]{kodeMenu, namaMenu, jumlah, harga, totalHarga});
                        }
                        totalBayar += totalHarga;
                        txt_totalBayar.setText(String.valueOf(totalBayar));

                    } else {
                        MessageAlerts.getInstance().showMessage("GAGAL MENAMBAH PESANAN!", "Tolong cek stok terlebih dahulu sebelum menambah pesanan.", MessageAlerts.MessageType.ERROR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String generateKodeTransaksi() {
        Connection conn = DatabaseConfig.getConnection();
        String kodeTransaksi = "";

        if (conn != null) {
            try {
                Statement statement = conn.createStatement();
                String query = "SELECT kode_transaksi FROM transaksi ORDER BY kode_transaksi DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String lastKode = resultSet.getString("kode_transaksi");
                    int kodeNum = Integer.parseInt(lastKode.substring(3)) + 1;
                    kodeTransaksi = String.format("TRS%03d", kodeNum);
                }

                resultSet.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kodeTransaksi;
    }

    private static void prosesTransaksi(String kodeTransaksi, String kodeMember, String namaPelanggan, String[] kodeMenu, String idAdmin, int[] jumlah, double bayar, double point) throws SQLException {
        Connection conn = DatabaseConfig.getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
                //Cek stok
                for (int i = 0; i < kodeMenu.length; i++) {
                    String cekStok = "SELECT stok_menu, harga FROM menu WHERE kode_menu = ?";
                    try (PreparedStatement cekStokStmt = conn.prepareStatement(cekStok)) {
                        cekStokStmt.setString(1, kodeMenu[i]);
                        try (ResultSet rs = cekStokStmt.executeQuery()) {
                            if (rs.next()) {
                                int stok = rs.getInt("stok_menu");
                                if (stok <= jumlah[i]) {
                                    throw new SQLException("Stok tidak mencukupi pada kode menu: " + kodeMenu[i]);
                                }
                            } else {
                                throw new SQLException("Kode tidak ditemukan: " + kodeMenu[i]);
                            }
                        }
                    }
                }
                //Insert transaksi
                String insertTransaksi = "INSERT INTO transaksi VALUES (?, ?, ?, NOW(), ?, ?)";
                try (PreparedStatement transaksiStmt = conn.prepareStatement(insertTransaksi)) {
                    transaksiStmt.setString(1, kodeTransaksi);
                    transaksiStmt.setString(2, idAdmin);
                    transaksiStmt.setString(3, kodeMember);
                    transaksiStmt.setString(4, namaPelanggan);
                    transaksiStmt.setDouble(5, bayar);
                    transaksiStmt.executeUpdate();
                }

                for (int j = 0; j < kodeMenu.length; j++) {
                    //Insert detail transaksi
//                    for (int x = 0; x < jumlah[j]; x++) {
                    String insertDetailTransaksi = "INSERT INTO detail_transaksi VALUES (?, ?, ?)";
                    try (PreparedStatement detailTrskStmt = conn.prepareStatement(insertDetailTransaksi)) {
                        detailTrskStmt.setString(1, kodeTransaksi);
                        detailTrskStmt.setString(2, kodeMenu[j]);
                        detailTrskStmt.setInt(3, jumlah[j]);
                        detailTrskStmt.executeUpdate();
                    }
//                    }

                    //Update stok pada tabel menu
                    String updateStokMenu = "UPDATE menu SET stok_menu = stok_menu - ? WHERE kode_menu = ?";
                    try (PreparedStatement upStokStmt = conn.prepareStatement(updateStokMenu)) {
                        upStokStmt.setInt(1, jumlah[j]);
                        upStokStmt.setString(2, kodeMenu[j]);
                        upStokStmt.executeUpdate();
                    }
                    //Insert point
                    System.out.println("Kode Member: " + kodeMember); // Log untuk memeriksa kode member 
                    System.out.println("Point: " + point);
                    String setPoint = "UPDATE member SET point = point + ? WHERE kode_member = ?";
                    try (PreparedStatement pointStmt = conn.prepareStatement(setPoint)) {
                        pointStmt.setDouble(1, point);
                        pointStmt.setString(2, kodeMember);
                        int rowUpdate = pointStmt.executeUpdate();
                        System.out.println("Row updates point: " + rowUpdate);
                        if (rowUpdate == 0) {
                            throw new SQLException("Gagal perbarui data pada member: " + kodeMember);
                        }
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
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

        searchOption1 = new javaswingdev.util.SearchOption();
        container1 = new javaswingdev.util.Container();
        txt_kodeMenu = new javaswingdev.util.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_namaMenu = new javaswingdev.util.TextField();
        jLabel3 = new javax.swing.JLabel();
        txt_harga = new javaswingdev.util.TextField();
        jLabel4 = new javax.swing.JLabel();
        txt_jumlah = new javaswingdev.util.TextField();
        btn_tambah = new javaswingdev.util.Button();
        btn_clear = new javaswingdev.util.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_pesanan = new javax.swing.JTable();
        cbox_member = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_namaPelanggan = new javaswingdev.util.TextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_totalBayar = new javaswingdev.util.TextField();
        jLabel8 = new javax.swing.JLabel();
        txt_bayar = new javaswingdev.util.TextField();
        btn_hapus = new javaswingdev.util.Button();
        btn_pesan = new javaswingdev.util.Button();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_menu = new javax.swing.JTable();
        cbox_point = new javax.swing.JCheckBox();

        setOpaque(false);

        container1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        container1.setToolTipText("");

        txt_kodeMenu.setEnabled(false);
        txt_kodeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodeMenuActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Kode Menu");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Menu");

        txt_namaMenu.setEnabled(false);
        txt_namaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaMenuActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Harga");

        txt_harga.setEnabled(false);
        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jumlah");

        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        tbl_pesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Menu", "Nama Menu", "Jumlah", "Harga", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_pesanan);
        if (tbl_pesanan.getColumnModel().getColumnCount() > 0) {
            tbl_pesanan.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        cbox_member.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbox_memberMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbox_memberMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cbox_memberMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Member");

        txt_namaPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaPelangganActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nama Pelanggan");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Bayar");

        txt_totalBayar.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Bayar");

        txt_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bayarActionPerformed(evt);
            }
        });

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_pesan.setText("Pesan");
        btn_pesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesanActionPerformed(evt);
            }
        });

        tbl_menu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_menu.getTableHeader().setReorderingAllowed(false);
        tbl_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_menuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_menu);

        cbox_point.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbox_point.setForeground(new java.awt.Color(255, 255, 255));
        cbox_point.setText("Gunakan point");
        cbox_point.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbox_pointActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel1))
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txt_kodeMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(container1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel3))
                                    .addComponent(txt_harga, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_namaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)))))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cbox_point)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbox_member, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_namaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(180, 180, 180)
                                .addComponent(jLabel8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                                .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_pesan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_bayar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_totalBayar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kodeMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cbox_point)
                        .addComponent(jLabel6))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_namaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbox_member, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_kodeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodeMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodeMenuActionPerformed

    private void txt_namaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaMenuActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        tambahPesanan();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_namaPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaPelangganActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        selectedTableMenu.setRowCount(0);
        txt_totalBayar.setText("");
        txt_bayar.setText("");
        totalBayar = 0;
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_pesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesanActionPerformed
        String kodeTransaksi, idAdmin, namaPelanggan;
        double point, bayar;
        String[] kodeMember;
        List<String> kodeMenuList = new ArrayList<>();
        List<Integer> jumlahList = new ArrayList<>();

        kodeTransaksi = generateKodeTransaksi();
        idAdmin = Session.getKode();
        kodeMember = cbox_member.getSelectedItem().toString().split("\\ ");
        bayar = Double.parseDouble(txt_bayar.getText());
        
        point = totalBayar * 0.1;
        namaPelanggan = txt_namaPelanggan.getText();
        for (int i = 0; i < tbl_pesanan.getRowCount(); i++) {
            kodeMenuList.add((String) tbl_pesanan.getValueAt(i, 0));
            jumlahList.add((Integer) tbl_pesanan.getValueAt(i, 2));
        }
        String[] kodeMenu = kodeMenuList.toArray(new String[0]);
        int[] jumlah = jumlahList.stream().mapToInt(Integer::intValue).toArray();
        try {
            if (tbl_pesanan.getRowCount() != 0) {
                if (txt_bayar.getText().equals("")) {
                    MessageAlerts.getInstance().showMessage("Gagal!", "Pastikan mengisi nominal pembayaran", MessageAlerts.MessageType.DEFAULT);
                } else {
                    if (Double.parseDouble(txt_bayar.getText()) >= totalBayar) {
                        prosesTransaksi(kodeTransaksi, kodeMember[0], namaPelanggan, kodeMenu, idAdmin, jumlah, bayar, point);
                        selectedTableMenu.setRowCount(0);
                        txt_kodeMenu.setText("");
                        txt_namaMenu.setText("");
                        txt_harga.setText("");
                        txt_totalBayar.setText("");
                        txt_bayar.setText("");
                        loadDataMenu();
                        MessageAlerts.getInstance().showMessage("Transaksi berhasil!", "", MessageAlerts.MessageType.SUCCESS);
                    } else {
                        MessageAlerts.getInstance().showMessage("Gagal!", "Pastikan nominal bayar mencukupi", MessageAlerts.MessageType.DEFAULT);
                    }
                }
            } else {
                MessageAlerts.getInstance().showMessage("Pesan menu terlebih dahulu!", "", MessageAlerts.MessageType.ERROR);
            }
        } catch (Exception ex) {
            MessageAlerts.getInstance().showMessage("Transaksi gagal!", "", MessageAlerts.MessageType.ERROR);
            Logger.getLogger(Form_Transaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_pesanActionPerformed

    private void txt_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bayarActionPerformed

    private void tbl_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_menuMouseClicked
        int selectedRow = tbl_menu.getSelectedRow();
        if (selectedRow != -1) {
            String kodeMenu = (String) tableModel.getValueAt(selectedRow, 0);
            String namaMenu = (String) tableModel.getValueAt(selectedRow, 1);
            String harga = (String) tableModel.getValueAt(selectedRow, 3);

            txt_kodeMenu.setText(kodeMenu);
            txt_namaMenu.setText(namaMenu);
            txt_harga.setText(harga);
            txt_jumlah.setText("1");
        }
    }//GEN-LAST:event_tbl_menuMouseClicked

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        txt_kodeMenu.setText("");
        txt_namaMenu.setText("");
        txt_harga.setText("");
        txt_jumlah.setText("");
    }//GEN-LAST:event_btn_clearActionPerformed

    private void cbox_memberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbox_memberMouseClicked

    }//GEN-LAST:event_cbox_memberMouseClicked

    private void cbox_memberMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbox_memberMouseExited

    }//GEN-LAST:event_cbox_memberMouseExited

    private void cbox_memberMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbox_memberMouseEntered
        int cmbox = cbox_member.getSelectedIndex();
//        System.out.println(cmbox);
        if (cmbox != 0) {
            cbox_point.setEnabled(true);
            txt_namaPelanggan.setEnabled(false);
        } else {
            cbox_point.setSelected(false);
            cbox_point.setEnabled(false);
            txt_namaPelanggan.setEnabled(true);  
        }
    }//GEN-LAST:event_cbox_memberMouseEntered

    private void cbox_pointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbox_pointActionPerformed
        double getPoint;   
        String[] kodeMember;
        
        kodeMember = cbox_member.getSelectedItem().toString().split("\\ ");
        
        Double dObj = Double.valueOf(kodeMember[4]);
        getPoint = dObj.doubleValue();
        if(cbox_point.isSelected()){
            totalBayar -= getPoint;
            txt_totalBayar.setText(String.valueOf(totalBayar));
            cbox_member.setEnabled(false);
        }else{
            totalBayar += getPoint;
            txt_totalBayar.setText(String.valueOf(totalBayar));
            cbox_member.setEnabled(true);
        }
    }//GEN-LAST:event_cbox_pointActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btn_clear;
    private javaswingdev.util.Button btn_hapus;
    private javaswingdev.util.Button btn_pesan;
    private javaswingdev.util.Button btn_tambah;
    private javax.swing.JComboBox<String> cbox_member;
    private javax.swing.JCheckBox cbox_point;
    private javaswingdev.util.Container container1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javaswingdev.util.SearchOption searchOption1;
    private javax.swing.JTable tbl_menu;
    private javax.swing.JTable tbl_pesanan;
    private javaswingdev.util.TextField txt_bayar;
    private javaswingdev.util.TextField txt_harga;
    private javaswingdev.util.TextField txt_jumlah;
    private javaswingdev.util.TextField txt_kodeMenu;
    private javaswingdev.util.TextField txt_namaMenu;
    private javaswingdev.util.TextField txt_namaPelanggan;
    private javaswingdev.util.TextField txt_totalBayar;
    // End of variables declaration//GEN-END:variables
}
