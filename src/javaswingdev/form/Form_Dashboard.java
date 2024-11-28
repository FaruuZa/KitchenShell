package javaswingdev.form;

import java.sql.*;
import javaswingdev.card.ModelCard;
import config.DatabaseConfig;

public class Form_Dashboard extends javax.swing.JPanel {

    Connection connection = DatabaseConfig.getConnection();

    public Form_Dashboard() {
        initComponents();
        init();
    }

    public String loadMember() {
        if (connection != null) {
            try {
                String jumlah = "";
                Statement st = connection.createStatement();
                String query = "SELECT COUNT(kode_member) AS jumlah FROM member";
                
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getString("jumlah");
                }
                rs.close();
                st.close();
                return jumlah;
//                System.out.println(jumlahKaryawan + jumlahMember + jumlahMenu);
            } catch (Exception e) {

            }
        }
        return "";
    }
    public String loadMenu() {
        if (connection != null) {
            try {
                String jumlah = "" ;
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
        return "";
    }
    public String loadKaryawan() {
        if (connection != null) {
            try {
                String jumlah = "";
                Statement st = connection.createStatement();
                String query = "SELECT COUNT(kode_akun) AS jumlah FROM akun WHERE role='0'";
                
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    jumlah = rs.getString("jumlah");
                }
                rs.close();
                st.close();
                return jumlah;
//                System.out.println(jumlahKaryawan + jumlahMember + jumlahMenu);
            } catch (Exception e) {

            }
        }
        return "";
    }

    private void init() {
        table.fixTable(jScrollPane1);
        table.addRow(new Object[]{"1", "Mike Bhand", "mikebhand@gmail.com", "Admin", "25 Apr,2018"});

        //  init card data
        card1.setData(new ModelCard(null, null, null, "MENU", loadMenu()));
        card2.setData(new ModelCard(null, null, null, "MEMBER", loadMember()));
        card3.setData(new ModelCard(null, null, null, "KARYAWAN", loadKaryawan()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new javaswingdev.card.Card();
        card2 = new javaswingdev.card.Card();
        card3 = new javaswingdev.card.Card();
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javaswingdev.swing.table.Table();

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

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Email", "Position", "Date Join"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(100, 100, 100))
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
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.card.Card card1;
    private javaswingdev.card.Card card2;
    private javaswingdev.card.Card card3;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.table.Table table;
    // End of variables declaration//GEN-END:variables
}
