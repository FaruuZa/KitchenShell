/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author MI TA
 */
public class ModelDataPengeluaran {

    /**
     * @return the bulan
     */
    public String getBulan() {
        return bulan;
    }

    /**
     * @param bulan the bulan to set
     */
    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    /**
     * @return the pengeluaran
     */
    public double getPengeluaran() {
        return pengeluaran;
    }

    /**
     * @param pengeluaran the pengeluaran to set
     */
    
    public ModelDataPengeluaran(String bulan, double pengeluaran) {
        this.bulan = bulan;
        this.pengeluaran = pengeluaran;
    }

    public ModelDataPengeluaran() {
    }

    public void setPengeluaran(double pengeluaran) {
        this.pengeluaran = pengeluaran;
    }
    private String bulan;
    private double pengeluaran;
}
