package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import javaswingdev.util.TextFieldFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author rayag
 */
public class tambahMenu extends javax.swing.JFrame {

    Connection connection = null;
    Form_Menu menuF = null;
    DefaultTableModel modelBahan;
    DefaultTableModel modelResep;
    String kodeEdit = "";

    public tambahMenu(Form_Menu Fmenu, String kode) {
        initComponents();
        getCon();
        this.menuF = Fmenu;
        ((AbstractDocument) inputMenu.getDocument()).setDocumentFilter(new TextFieldFilter("[a-zA-Z ]*"));
        ((AbstractDocument) inputHarga.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9.]*"));
        String[] judul = {"Kode Bahan", "Nama Bahan", "Jumlah", "Satuan"};
        modelResep = new DefaultTableModel(judul, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return switch (column) {
                    case 2 ->
                        true;
                    default ->
                        false;
                };
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 2 ->
                        Integer.class;
                    default ->
                        String.class;
                };
            }
        };
        modelResep.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int baris = e.getFirstRow();
                    if ((int) modelResep.getValueAt(baris, 2) <= 0) {
                        modelResep.removeRow(baris);
                    }
                }
            }
        });
        tblRsp.setModel(modelResep);
        loadBahan();
        if (!kode.equals("")) {
            this.kodeEdit = kode;
            loadMenu();
            loadResep();
        }
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBahan() {
        String[] judul = {"Kode Bahan", "Nama Bahan"};
        modelBahan = new DefaultTableModel(judul, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBahan.setModel(modelBahan);
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT kode_bahanBaku, nama_bahanBaku FROM bahanbaku";
                ResultSet rs = st.executeQuery(query);
                modelBahan.setRowCount(0);
                while (rs.next()) {
                    String[] data = {rs.getString(1), rs.getString(2)};
                    modelBahan.addRow(data);
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMenu() {
        if (connection != null) {
            try {
                String query = "SELECT * FROM menu WHERE kode_menu= ?";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, kodeEdit);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    inputMenu.setText(rs.getString(2));
                    inputHarga.setText(rs.getString(3));
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadResep() {
        if (connection != null) {
            try {
                String query = "SELECT detail_menu.kode_bahanbaku, bahanbaku.nama_bahanbaku, detail_menu.jumlah, bahanbaku.satuan"
                        + " FROM `detail_menu`"
                        + " JOIN bahanbaku ON bahanbaku.kode_bahanbaku=detail_menu.kode_bahanbaku"
                        + " WHERE detail_menu.kode_menu=?";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, kodeEdit);
                ResultSet rs = st.executeQuery();
                modelResep.setRowCount(0);
                while (rs.next()) {
                    Object[] data = {rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)};
                    modelResep.addRow(data);
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
                String query = "SELECT kode_menu FROM menu ORDER BY kode_menu DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String lastKode = resultSet.getString("kode_menu");
                    int kodeNum = Integer.parseInt(lastKode.substring(3)) + 1;
                    kodeMenu = String.format("MNU%03d", kodeNum);
                }

                resultSet.close();
                statement.close();
                return kodeMenu;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return kodeMenu;
    }

    private void createData() {
        if (connection != null) {
            try {
                if (!inputMenu.getText().equals("") && !inputHarga.getText().equals("")) {
                    String query = "INSERT INTO menu VALUES (?, ?, ?)";
                    String kode = generateCode();
                    PreparedStatement stMn = connection.prepareStatement(query);
                    stMn.setString(1, kode);
                    stMn.setString(2, inputMenu.getText());
                    stMn.setString(3, inputHarga.getText());
                    stMn.execute();
                    int reseps = tblRsp.getRowCount();
                    if (reseps > 0) {
                        for (int i = 0; i < reseps; i++) {
                            String queryBhn = "INSERT INTO detail_menu VALUES (?,?,?)";
                            PreparedStatement stBhn = connection.prepareStatement(queryBhn);
                            stBhn.setString(2, (String) tblRsp.getValueAt(i, 0));
                            stBhn.setString(1, kode);
                            stBhn.setInt(3, (int) tblRsp.getValueAt(i, 2));
                            stBhn.execute();
                        }
                    }
                    stMn.close();
                    menuF.loadDataMenu("");
                    menuF.popupHandler("Menu berhasil ditambahkan", 1, this, null);
                } else {
                    throw new Exception("data tidak boleh kosong");
                }
            } catch (Exception e) {
                menuF.popupHandler(e.getMessage(), 0, this, null);
            }

        }
    }

    private void editData() {
        if (connection != null) {
            try {
                if (!inputMenu.getText().equals("") && !inputHarga.getText().equals("")) {
                    String query = "UPDATE menu SET `nama_menu`= ?,`harga`= ? WHERE kode_menu=?";
                    PreparedStatement stMn = connection.prepareStatement(query);
                    stMn.setString(3, kodeEdit);
                    stMn.setString(1, inputMenu.getText());
                    stMn.setString(2, inputHarga.getText());
                    stMn.execute();
                    int reseps = tblRsp.getRowCount();
                    if (reseps > 0) {
                        String queryHps = "DELETE FROM `detail_menu` WHERE `kode_menu`=?";
                        PreparedStatement stHps = connection.prepareStatement(queryHps);
                        stHps.setString(1, kodeEdit);
                        stHps.execute();
                        for (int i = 0; i < reseps; i++) {
                            String queryBhn = "INSERT INTO detail_menu VALUES (?,?,?)";
                            PreparedStatement stBhn = connection.prepareStatement(queryBhn);
                            stBhn.setString(2, (String) tblRsp.getValueAt(i, 0));
                            stBhn.setString(1, kodeEdit);
                            stBhn.setInt(3, (int) tblRsp.getValueAt(i, 2));
                            stBhn.execute();
                        }
                    }
                    stMn.close();
                    menuF.loadDataMenu("");
                    menuF.popupHandler("Menu berhasil ditambahkan", 1, this, null);
                } else {
                    throw new Exception("data tidak boleh kosong");
                }
            } catch (Exception e) {
                menuF.popupHandler(e.getMessage(), 0, this, null);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        container1 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        inputMenu = new javaswingdev.util.TextField();
        inputHarga = new javaswingdev.util.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_batal = new javaswingdev.util.Button();
        btn_simpan = new javaswingdev.util.Button();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRsp = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBahan = new javax.swing.JTable();
        btn_tambah = new javaswingdev.util.Button();
        jLabel6 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TAMBAH MENU");

        inputMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputMenuActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Menu");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Harga");

        btn_batal.setText("BATAL");
        btn_batal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        btn_simpan.setText("SIMPAN");
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed1(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("RESEP : ");

        tblRsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama Bahan", "Jumlah", "Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRsp.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblRsp);

        tblBahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Bahan", "Nama Bahan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBahan.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblBahan);

        btn_tambah.setText("TAMBAH");
        btn_tambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahbutton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("BAHAN : ");

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(container1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(542, 542, 542))
                                .addGroup(container1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(449, 449, 449)
                                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(container1Layout.createSequentialGroup()
                                    .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(inputHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(340, 340, 340)))
                            .addGroup(container1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(183, 183, 183)
                                .addComponent(jLabel6)))))
                .addGap(25, 25, 25))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addGap(0, 0, 0)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addComponent(inputMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3)
                        .addGap(0, 0, 0)
                        .addComponent(inputHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, container1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
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

    private void inputMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputMenuActionPerformed

    }//GEN-LAST:event_inputMenuActionPerformed


    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        this.dispose();
        menuF.enabledButton(1);
        menuF.aksi = 0;
    }//GEN-LAST:event_button1ActionPerformed

    private void btn_simpanActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed1
        if (kodeEdit.equals("")) {
            createData();
        } else {
            editData();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed1

    private void btn_tambahbutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahbutton1ActionPerformed
        int selected = tblBahan.getSelectedRow();
        String kode = (String) tblBahan.getValueAt(selected, 0);
        int row = tblRsp.getRowCount();
        int a = 0;
        for (int i = 0; i < row; i++) {
            if (tblRsp.getValueAt(i, 0).equals(kode)) {
                a = 1;
                break;
            }
        }
        if (a == 0) {
            try {
                String query = "SELECT * FROM bahanbaku WHERE kode_bahanBaku= ?";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, kode);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Object[] data = {rs.getString(1), rs.getString(2), 0, rs.getString(4)};
                    modelResep.addRow(data);
                }
                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_tambahbutton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btn_batal;
    private javaswingdev.util.Button btn_simpan;
    private javaswingdev.util.Button btn_tambah;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField inputHarga;
    private javaswingdev.util.TextField inputMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblBahan;
    private javax.swing.JTable tblRsp;
    // End of variables declaration//GEN-END:variables
}
