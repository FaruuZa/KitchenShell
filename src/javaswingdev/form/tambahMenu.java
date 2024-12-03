package javaswingdev.form;

import java.sql.*;
import config.DatabaseConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import javaswingdev.util.TextFieldFilter;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author rayag
 */
public class tambahMenu extends javax.swing.JFrame {

    Connection connection = null;
    Form_Menu menuF = null;

    public tambahMenu(Form_Menu Fmenu) {
        initComponents();
        getCon();
        this.menuF = Fmenu;
        ((AbstractDocument) inputMenu.getDocument()).setDocumentFilter(new TextFieldFilter("[a-zA-Z ]*"));
        ((AbstractDocument) inputHarga.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
        ((AbstractDocument) inputJumlah.getDocument()).setDocumentFilter(new TextFieldFilter("[0-9]*"));
    }

    private void getCon() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
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
                if (!inputMenu.getText().equals("") || !inputHarga.getText().equals("") || !inputJumlah.getText().equals("")) {
                    Statement statement = connection.createStatement();
                    String query = "INSERT INTO menu VALUES ('" + generateCode() + "', '" + inputMenu.getText() + "', '" + inputJumlah.getText() + "', '" + inputHarga.getText() + "')";
                    statement.execute(query);
                    statement.close();
                    menuF.loadDataMenu("");
                    menuF.popupHandler("Menu berhasil ditambahkan", 1, this, null);
//                  
                }else{
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
        inputJumlah = new javaswingdev.util.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_batall = new javaswingdev.util.Button();
        btn_simpan = new javaswingdev.util.Button();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Stok");

        btn_batall.setText("BATAL");
        btn_batall.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_batall.addActionListener(new java.awt.event.ActionListener() {
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

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(container1Layout.createSequentialGroup()
                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_batall, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(container1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(container1Layout.createSequentialGroup()
                                        .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(205, 205, 205)))))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(inputMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(inputHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(inputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batall, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void inputMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputMenuActionPerformed

    }//GEN-LAST:event_inputMenuActionPerformed


    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        this.dispose();
        menuF.enabledButton(1);
        menuF.aksi=0;
    }//GEN-LAST:event_button1ActionPerformed

    private void btn_simpanActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed1
        createData();
    }//GEN-LAST:event_btn_simpanActionPerformed1

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btn_batall;
    private javaswingdev.util.Button btn_simpan;
    private javaswingdev.util.Container container1;
    private javaswingdev.util.TextField inputHarga;
    private javaswingdev.util.TextField inputJumlah;
    private javaswingdev.util.TextField inputMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
