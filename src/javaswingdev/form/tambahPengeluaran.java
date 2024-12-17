/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import config.Session;
import java.text.SimpleDateFormat;
import javaswingdev.util.TextFieldFilter;
import javax.swing.text.AbstractDocument;
import java.util.Date;

public class tambahPengeluaran extends javax.swing.JFrame {

    Connection connection = null;
    Date date = new Date();
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Form_Pengeluaran pengeluaranF = null;
    Form_BahanBaku bahanF = null;

    public tambahPengeluaran(Form_Pengeluaran pengeluaran, Form_BahanBaku bahan) {
        initComponents();
        getCon();
        ((AbstractDocument) inputKeterangan.getDocument()).setDocumentFilter(new TextFieldFilter("[a-z A-Z]*"));
        ((AbstractDocument) jumlah.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9a-zA-Z ]*"));
        ((AbstractDocument) inputTotal.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        inputBahanBaku.addItem("Pilih bahan baku");
        inputBahanBaku.setSelectedIndex(0);
        loadDatabahanBaku();
        jumlah.setRound(0);
        this.pengeluaranF = pengeluaran;
        this.bahanF = bahan;
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateCode() {
        String kodePengeluaran = "";
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT kode_pengeluaran FROM pengeluaran ORDER BY kode_pengeluaran DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String lastkode = resultSet.getString("kode_pengeluaran");
                    int kodenum = Integer.parseInt(lastkode.substring(3)) + 1;
                    kodePengeluaran = String.format("PGN%03d", kodenum);
                }
                resultSet.close();
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kodePengeluaran;
    }

    private void createData() {

        if (connection != null) {
            try {
                String idAdmin = Session.getKode();
                if (!inputKeterangan.getText().equals("") && !jumlah.getText().equals("") && !inputTotal.getText().equals("")) {
                    String[] kodeBahan = inputBahanBaku.getSelectedItem().toString().split(" | ");
//                    String query1 = "INSERT INTO pengeluaran VALUES ('" + generateCode() + "','" + idAdmin + "', '" + kodeBahan[0] + "', NOW(), '" + inputKeterangan.getText() + "', '" + jumlah.getText() + "', '" + inputTotal.getText() + "' ) ";
                    String query = "INSERT INTO pengeluaran VALUES (?, ?, ?, NOW(), ?, ?, ? )";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, generateCode());
                    statement.setString(2, idAdmin);
                    statement.setString(4, inputKeterangan.getText());
                    statement.setString(6, inputTotal.getText());
                    if (inputBahanBaku.getSelectedIndex() != 0) {
                        statement.setString(3, kodeBahan[0]);
                        try {
                            String queryBahan = "UPDATE `bahanbaku` SET `stok_bahanbaku`= stok_bahanBaku + ? WHERE kode_bahanbaku= ?";
                            PreparedStatement stBahan = connection.prepareStatement(queryBahan);
                            if (satuan.getSelectedIndex() > 1) {
                                stBahan.setString(1, jumlah.getText());
                                Double a = Double.parseDouble(jumlah.getText());
                                String b = a >= 1000 ? Double.toString(a / 1000) : Double.toString(a);
                                statement.setString(5, b + (satuan.getSelectedIndex() == 2 ? "Kg" : "L"));
                            } else {
                                stBahan.setString(1, Double.toString(Double.parseDouble(jumlah.getText()) * 1000));
                                statement.setString(5, jumlah.getText() + satuan.getSelectedItem());
                            }
                            stBahan.setString(2, kodeBahan[0]);
                            stBahan.executeUpdate();
                            stBahan.close();
                        } catch (Exception e) {
                            throw new Exception(e.getMessage());
                        }
                    } else {
                        statement.setNull(3, java.sql.Types.NULL);
                        statement.setString(5, jumlah.getText());
                    }
                    statement.execute();
                    statement.close();
                    if (pengeluaranF != null) {
                        pengeluaranF.loadDataPengeluaran("");
                        pengeluaranF.popupHandler("data berhasil ditambah!", 1);
                    } else {
                        bahanF.loadDatabahanBaku("");
                        bahanF.popupHandler("berhasil membeli bahan", 1, false);
                    }
                    this.dispose();
                } else {
                    throw new Exception("data tidak boleh kosong");
                }
            } catch (Exception e) {
                if (pengeluaranF != null) {
                    pengeluaranF.popupHandler(e.getMessage(), 0);
                } else {
                    bahanF.popupHandler(e.getMessage(), 0, false);
                }
            }
        }
    }

    private void loadDatabahanBaku() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM bahanbaku";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String kode_bahanbaku = rs.getString("kode_bahanbaku");
                    String nama_bahanbaku = rs.getString("nama_bahanbaku");

                    inputBahanBaku.addItem(kode_bahanbaku + " | " + nama_bahanbaku);
                }
                st.close();
                rs.close();
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

        container2 = new javaswingdev.util.Container();
        jLabel1 = new javax.swing.JLabel();
        inputKeterangan = new javaswingdev.util.TextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_batal = new javaswingdev.util.Button();
        btn_simpan = new javaswingdev.util.Button();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        inputTotal = new javaswingdev.util.TextField();
        inputBahanBaku = new javax.swing.JComboBox<>();
        inputJumlah = new javax.swing.JLayeredPane();
        satuan = new javax.swing.JComboBox<>();
        jumlah = new javaswingdev.util.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TAMBAH PENGELUARAN");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bahan Baku");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Keterangan");

        btn_batal.setText("BATAL");
        btn_batal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_simpan.setText("SIMPAN");
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Jumlah");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total harga");

        inputBahanBaku.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        inputBahanBaku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputBahanBakuActionPerformed(evt);
            }
        });

        inputJumlah.setBackground(new java.awt.Color(255, 255, 255));
        inputJumlah.setToolTipText("");
        inputJumlah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        satuan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        satuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kg", "L", "g", "ml" }));
        satuan.setBorder(null);
        satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                satuanActionPerformed(evt);
            }
        });
        inputJumlah.add(satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 2, 60, 30));

        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        inputJumlah.add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 40));

        javax.swing.GroupLayout container2Layout = new javax.swing.GroupLayout(container2);
        container2.setLayout(container2Layout);
        container2Layout.setHorizontalGroup(
            container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(container2Layout.createSequentialGroup()
                        .addGroup(container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addGroup(container2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(inputBahanBaku, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(inputKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(inputTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(container2Layout.createSequentialGroup()
                        .addGroup(container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(inputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(39, Short.MAX_VALUE))))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        container2Layout.setVerticalGroup(
            container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(inputBahanBaku, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(inputKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(inputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addGap(0, 0, 0)
                .addComponent(inputTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(container2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        createData();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        if (pengeluaranF != null) {
            pengeluaranF.enabledButton(0);
            pengeluaranF.aksi = 0;
        }else{
            bahanF.enabledButton(0);
            bahanF.aksi = 0;
        }
        this.dispose();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void inputBahanBakuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputBahanBakuActionPerformed
        if (inputBahanBaku.getSelectedIndex() == 0) {
            satuan.setVisible(false);
            inputKeterangan.setEnabled(true);
            inputKeterangan.setText("");
        } else {
            inputKeterangan.setEnabled(false);
            String[] kodeMember = inputBahanBaku.getSelectedItem().toString().split(" | ");
            inputKeterangan.setText("Menambah stok " + kodeMember[2]);
            satuan.setVisible(true);
        }
    }//GEN-LAST:event_inputBahanBakuActionPerformed

    private void satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_satuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_satuanActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btn_batal;
    private javaswingdev.util.Button btn_simpan;
    private javaswingdev.util.Container container2;
    private javax.swing.JComboBox<String> inputBahanBaku;
    private javax.swing.JLayeredPane inputJumlah;
    private javaswingdev.util.TextField inputKeterangan;
    private javaswingdev.util.TextField inputTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javaswingdev.util.TextField jumlah;
    private javax.swing.JComboBox<String> satuan;
    // End of variables declaration//GEN-END:variables
}
