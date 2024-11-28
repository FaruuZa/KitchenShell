/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package config;

import javaswingdev.main.Main;
        
public class Session {
    public static String kodeMember = "";
    public static int role = 0;
    public static Main halamanUtama;
    
    public static String getKode(){
        return kodeMember;
    }
    public static void setKode(String kode){
        Session.kodeMember = kode;
    }
    
    public static Main getHalamanUtama(){
        return halamanUtama;
    }
    
    public static void setHalamanUtama(Main halaman){
        Session.halamanUtama = halaman;
    }
    
}
