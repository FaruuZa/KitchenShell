

package config;
        
public class Session {
    public static String kodeMember = "";
    public static int role = -1;
    
    public static String getKode(){
        return kodeMember;
    }
    public static void setKode(String kode){
        Session.kodeMember = kode;
    }
    public static int getRole(){
        return role;
    }
    public static void setRole(int roles){
        Session.role= roles;
    }
    
    public static void logout(){
        Session.kodeMember = "";
        Session.role = -1;
    }
}
