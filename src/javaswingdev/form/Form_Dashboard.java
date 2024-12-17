package javaswingdev.form;

import java.sql.*;
import javaswingdev.card.ModelCard;
import config.DatabaseConfig;
import config.ModelDataPemasukan;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.chart.ChartPemasukan;
import javaswingdev.chart.ChartPengeluaran;
import javaswingdev.chart.ModelChart;
import javaswingdev.main.Main;
import javax.swing.table.DefaultTableModel;

public class Form_Dashboard extends javax.swing.JPanel {

    Connection connection = DatabaseConfig.getConnection();
    DefaultTableModel model;

    public Form_Dashboard() {
        initComponents();
        init();
        showChart(new ChartPemasukan());
        String[] judul = {"Bahan baku", "Sisa bahan"};
        model = new DefaultTableModel(judul, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
       tblsisa.setModel(model);
       cekBahan();
    }

    public void cekBahan() {
        if (connection != null) {
            try {
                Statement st = connection.createStatement();
                String query = "SELECT * FROM bahanBaku WHERE stok_bahanbaku < 1000";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    String[] data = {rs.getString(2), rs.getString(3)+rs.getString(4)};
                    model.addRow(data);
                }

                rs.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String loadPendapatanSkrg() {
        if (connection != null) {
            try {
                Double jumlah = 0.0;
                Statement st = connection.createStatement();
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
                return Main.formatRupiah(jumlah);
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

    public String loadPengeluaranBulan() {
        if (connection != null) {
            try {
                Double jumlah = 0.0;
                Statement st = connection.createStatement();
                String query = "SELECT * FROM `v_pengeluaran` WHERE bulan=date_format(now(),'%b')";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getDouble(2);
                }
                rs.close();
                st.close();
                return Main.formatRupiah(jumlah);
//                System.out.println(jumlahKaryawan + jumlahMember + jumlahMenu);
            } catch (Exception e) {

            }
        }
        return "Rp. 0";
    }

    private void init() {
        card1.setData(new ModelCard(GoogleMaterialDesignIcon.RESTAURANT, null, null, loadMenu(), "Jumlah Menu"));
        card2.setData(new ModelCard(GoogleMaterialDesignIcon.PAYMENT, null, null, loadPendapatanSkrg(), "Pendapatan hari ini"));
        card3.setData(new ModelCard(GoogleMaterialDesignIcon.MONEY_OFF, null, null, loadPengeluaranBulan(), "Pengeluaran Bulan ini"));
    }

    public void showChart(Component com) {
        chart.removeAll();
        chart.add(com);
        chart.repaint();
        chart.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new javaswingdev.card.Card();
        card2 = new javaswingdev.card.Card();
        card3 = new javaswingdev.card.Card();
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        btn_pemasukan = new javaswingdev.util.Button();
        btn_pengeluaran = new javaswingdev.util.Button();
        chart = new javax.swing.JPanel();
        container1 = new javaswingdev.util.Container();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsisa = new javax.swing.JTable();

        setOpaque(false);

        card1.setColor1(new java.awt.Color(111, 227, 225));
        card1.setColor2(new java.awt.Color(82, 87, 229));
        card1.setIcon(javaswingdev.GoogleMaterialDesignIcon.GIF);

        card2.setColor1(new java.awt.Color(56, 249, 215));
        card2.setColor2(new java.awt.Color(67, 233, 123));
        card2.setIcon(javaswingdev.GoogleMaterialDesignIcon.PIE_CHART);

        card3.setColor1(new java.awt.Color(250, 112, 154));
        card3.setColor2(new java.awt.Color(254, 225, 64));
        card3.setIcon(javaswingdev.GoogleMaterialDesignIcon.RING_VOLUME);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        btn_pemasukan.setText("Pemasukan");
        btn_pemasukan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_pemasukan.setShadowColor(new java.awt.Color(10, 212, 9));
        btn_pemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pemasukanActionPerformed(evt);
            }
        });

        btn_pengeluaran.setText("Pengeluaran");
        btn_pengeluaran.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_pengeluaran.setShadowColor(new java.awt.Color(214, 15, 22));
        btn_pengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pengeluaranActionPerformed(evt);
            }
        });

        chart.setOpaque(false);
        chart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(0, 295, Short.MAX_VALUE)
                        .addComponent(btn_pemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_pemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );

        container1.setBackground(java.awt.SystemColor.controlLtHighlight);

        tblsisa.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tblsisa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblsisa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblsisa.setRowHeight(30);
        jScrollPane1.setViewportView(tblsisa);

        javax.swing.GroupLayout container1Layout = new javax.swing.GroupLayout(container1);
        container1.setLayout(container1Layout);
        container1Layout.setHorizontalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        container1Layout.setVerticalGroup(
            container1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(container1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addGap(5, 5, 5))
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
                    .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(container1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_pemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pemasukanActionPerformed
        showChart(new ChartPemasukan());
    }//GEN-LAST:event_btn_pemasukanActionPerformed

    private void btn_pengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pengeluaranActionPerformed
        showChart(new ChartPengeluaran());
    }//GEN-LAST:event_btn_pengeluaranActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.util.Button btn_pemasukan;
    private javaswingdev.util.Button btn_pengeluaran;
    private javaswingdev.card.Card card1;
    private javaswingdev.card.Card card2;
    private javaswingdev.card.Card card3;
    private javax.swing.JPanel chart;
    private javaswingdev.util.Container container1;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javax.swing.JTable tblsisa;
    // End of variables declaration//GEN-END:variables
}
