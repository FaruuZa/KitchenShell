package javaswingdev.form;

import java.sql.*;
import javaswingdev.card.ModelCard;
import config.DatabaseConfig;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.Locale;
import javaswingdev.GoogleMaterialDesignIcon;

public class Form_Dashboard extends javax.swing.JPanel {

    Connection connection = DatabaseConfig.getConnection();

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public Form_Dashboard() {
        initComponents();
        init();
    }

    public String loadPendapatanSkrg() {
        if (connection != null) {
            try {
                Double jumlah = 0.0;
                Statement st = connection.createStatement();
//                String query = "SELECT COUNT(kode_member) AS jumlah FROM member";
                String query = "SELECT  transaksi.tnggl_transaksi, SUM(transaksi.total_transaksi) AS jumlah"
                        + " FROM transaksi"
                        + " WHERE date(transaksi.tnggl_transaksi)=date(now())"
                        + " GROUP BY transaksi.tnggl_transaksi";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getDouble(2);
                }
                rs.close();
                st.close();
                return formatRupiah.format(jumlah);
//                System.out.println(jumlahKaryawan + jumlahMember + jumlahMenu);
            } catch (Exception e) {

            }
        }
        return "Rp. 0";
    }

    public String loadMenu() {
        if (connection != null) {
            try {
                String jumlah = "0";
                Statement st = connection.createStatement();
                String query = "SELECT COUNT(kode_menu) AS jumlah FROM menu";

                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getString("jumlah");
                }

                rs.close();
                st.close();
                return jumlah;
//                System.out.println(jumlahKaryawan + jumlahMember + jumlahMenu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "0";
    }

    public String loadPengeluaranBulan() {
        if (connection != null) {
            try {
                Double jumlah = 0.0;
                Statement st = connection.createStatement();
                String query = "SELECT EXTRACT(YEAR_MONTH FROM tnggl_pengeluaran), SUM(harga) AS jumlah"
                        + " FROM pengeluaran"
                        + " WHERE EXTRACT(YEAR_MONTH FROM tnggl_pengeluaran) = EXTRACT(YEAR_MONTH FROM NOW())"
                        + " GROUP BY EXTRACT(YEAR_MONTH FROM tnggl_pengeluaran)";
                System.out.println(query);
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getDouble(2);
                }
                rs.close();
                st.close();
                return formatRupiah.format(jumlah);
//                System.out.println(jumlahKaryawan + jumlahMember + jumlahMenu);
            } catch (Exception e) {

            }
        }
        return "Rp. 0";
    }

    private void init() {
//        table.fixTable(jScrollPane1);
//        table.addRow(new Object[]{"1", "Mike Bhand", "mikebhand@gmail.com", "Admin", "25 Apr,2018"});
//
//        //  init card data
        card1.setData(new ModelCard(null, null, null, loadMenu(), "Jumlah Menu"));
        card2.setData(new ModelCard(GoogleMaterialDesignIcon.ACCOUNT_BALANCE, null, null, loadPendapatanSkrg(), "Pendapatan hari ini"));
        card3.setData(new ModelCard(GoogleMaterialDesignIcon.ACCOUNT_BALANCE, null, null, loadPengeluaranBulan(), "Pengeluaran Bulan ini"));
//        new ModelCard()
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new javaswingdev.card.Card();
        card2 = new javaswingdev.card.Card();
        card3 = new javaswingdev.card.Card();
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        roundPanel2 = new javaswingdev.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();

        setOpaque(false);

        card1.setColor1(new java.awt.Color(50, 50, 50));
        card1.setColor2(new java.awt.Color(100, 75, 70));
        card1.setIcon(javaswingdev.GoogleMaterialDesignIcon.GIF);

        card2.setColor1(new java.awt.Color(95, 211, 226));
        card2.setColor2(new java.awt.Color(26, 166, 170));
        card2.setIcon(javaswingdev.GoogleMaterialDesignIcon.PIE_CHART);

        card3.setColor1(new java.awt.Color(95, 243, 140));
        card3.setColor2(new java.awt.Color(3, 157, 27));
        card3.setIcon(javaswingdev.GoogleMaterialDesignIcon.RING_VOLUME);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Grafik cart");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
        );

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Menu yang akan habis");

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel2)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.card.Card card1;
    private javaswingdev.card.Card card2;
    private javaswingdev.card.Card card3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.RoundPanel roundPanel2;
    // End of variables declaration//GEN-END:variables
}
