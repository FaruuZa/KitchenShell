/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author MI TA
 */
public class ModelDataPemasukan {

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
     * @return the pemasukkan
     */
    public double getPemasukkan() {
        return pemasukkan;
    }

    /**
     * @param pemasukkan the pemasukkan to set
     */
    public void setPemasukkan(double pemasukkan) {
        this.pemasukkan = pemasukkan;
    }

    public ModelDataPemasukan(String bulan, double pemasukkan) {
        this.bulan = bulan;
        this.pemasukkan = pemasukkan;
    }

    public ModelDataPemasukan() {
    }
    
    private String bulan;
    private double pemasukkan;
}
