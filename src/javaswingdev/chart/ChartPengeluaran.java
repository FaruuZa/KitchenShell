/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.chart;

import java.sql.*;
import config.DatabaseConfig;
import java.awt.Color;
import config.ModelDataPemasukan;
import config.ModelDataPengeluaran;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.chart.ModelChart;

/**
 *
 * @author MI TA
 */
public class ChartPengeluaran extends javax.swing.JPanel {
    Connection connection = DatabaseConfig.getConnection();

    /**
     * Creates new form ChartPemasukan
     */
    public ChartPengeluaran() {
        initComponents();
        chart.setTitle("Pengeluaran");
        chart.addLegend(" ", Color.decode("#e60b09"), Color.decode("#e9d022"));
//        chart.addLegend("Pengeluaran", Color.decode("#57ebde"), Color.decode("#aefb2a"));
        setData();
    }

    private void setData() {
        try {
            System.out.println("woi keluar");
            List<ModelDataPengeluaran> list = new ArrayList<>();
            String query = "SELECT * FROM v_pengeluaran LIMIT 7";
            PreparedStatement p = connection.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                String bulan = rs.getString("bulan");
                double pengeluaran = rs.getDouble("pengeluaran");

                list.add(new ModelDataPengeluaran(bulan, pengeluaran));
            }
            rs.close();
            p.close();

            for (int i = list.size() - 1; i >= 0; i--) {
                ModelDataPengeluaran d = list.get(i);
                chart.addData(new ModelChart(d.getBulan(), new double[]{d.getPengeluaran()}));
            }
            chart.start();
        } catch (Exception e) {
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

        chart = new javaswingdev.chart.CurveLineChart();

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaswingdev.chart.CurveLineChart chart;
    // End of variables declaration//GEN-END:variables
}
