package javaswingdev.form;

import java.sql.*;
import javaswingdev.card.ModelCard;
import config.DatabaseConfig;
import config.ModelData;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.chart.ModelChart;

public class Form_Dashboard extends javax.swing.JPanel {

    Connection connection = DatabaseConfig.getConnection();

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public Form_Dashboard() {
        initComponents();
        chart.setTitle("Chart Data");
        chart.addLegend("Pemasukan", Color.decode("#00ff87"), Color.decode("#60efff"));
        chart.addLegend("Pengeluaran", Color.decode("#57ebde"), Color.decode("#aefb2a"));
        setData();
        init();
    }

    public String loadPendapatanSkrg() {
        if (connection != null) {
            try {
                Double jumlah = 0.0;
                Statement st = connection.createStatement();
//                String query = "SELECT COUNT(kode_member) AS jumlah FROM member";
                String query = "SELECT  transaksi.tnggl_transaksi, SUM(detail_transaksi.total) AS jumlah"
                        + " FROM transaksi"
                        + " WHERE date(transaksi.tnggl_transaksi)=date(now())"
                        + " GROUP BY transaksi.tnggl_transaksi";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getDouble(2);
                }
                rs.close();
                st.close();
                return jumlah.toString();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "0";
    }

    private void setData() {
        try {
            System.out.println("woi");
            List<ModelData> list = new ArrayList<>();
            String query = "SELECT * FROM v_pemasukan LIMIT 7";
            PreparedStatement p = connection.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                String bulan = rs.getString("bulan");
                double pemasukan = rs.getDouble("pemasukan");
               
                list.add(new ModelData(bulan, pemasukan));
            }
            rs.close();
            p.close();
            
            for(int i = list.size() - 1; i >= 0; i--){
                ModelData d = list.get(i);
                chart.addData(new ModelChart(d.getBulan(), new double[]{d.getPemasukkan()}));
            }
            chart.start();
        } catch (Exception e) {
        }
    }

    public String loadPengeluaranBulan() {
        if (connection != null) {
            try {
                Double jumlah = 0.0;
                Statement st = connection.createStatement();
                String query = "SELECT EXTRACT(YEAR_MONTH FROM tnggl_transaksi), SUM(detail_transaksi.total) AS jumlah"
                        + " FROM transaksi"
                        + " JOIN detail_transaksi ON transaksi.kode_transaksi=detail_transaksi.kode_transaksi"
                        + " WHERE EXTRACT(YEAR_MONTH FROM tnggl_transaksi) = EXTRACT(YEAR_MONTH FROM NOW())"
                        + " GROUP BY EXTRACT(YEAR_MONTH FROM tnggl_transaksi)";
                
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getDouble(2);
                }
                rs.close();
                st.close();
                return jumlah.toString();
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
        chart = new javaswingdev.chart.CurveLineChart();
        roundPanel2 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"beras", "500g"},
                {"telur", "720g"},
                {"mie", "320g"},
                {"minyak", "870ml"}
            },
            new String [] {
                "Nama bahan", "Sisa stok"
            }
        ));
        jTable1.setRowHeight(30);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addContainerGap())
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
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
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
    private javaswingdev.chart.CurveLineChart chart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.RoundPanel roundPanel2;
    // End of variables declaration//GEN-END:variables
}
