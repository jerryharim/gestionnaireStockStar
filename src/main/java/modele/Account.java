package modele;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import principale.Main;

import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.Hashtable;
import java.util.ResourceBundle;

public class Account{
    private Connection conn = Main.getConnection();
    private String username, fonction, dimFonction;
    private Date dateCreation;
    private int id;
    private Hashtable respInfo;

    public Account() {

    }

    public Account(int id) {
        loadAccountInfo(id);
        respInfo = getRespInfo();
        System.out.println(">>>>>>>>>>>>> tentative in constr: respInfo");
    }

    public void loadAccountInfo(int id) {
        // charge les informations sur un utilisateur
        System.out.print("Load account info...");
        this.id = id;
        try {
            Hashtable accountInfo = getAccountInformation();
            System.out.println("Account info : "+accountInfo);
            System.out.println("Resp info : "+respInfo);

            username = (String) accountInfo.get("username");
            dateCreation = (Date) accountInfo.get("dateCreation");
            fonction = (String) respInfo.get("fonction");
            dimFonction = (String) respInfo.get("dimFonction");
            System.out.println("done");
            System.out.println("username account " + username);
        } catch (NullPointerException e ) {
            System.out.println("can't load account");
        }

    }

    public Hashtable getAccountInformation() {
        // retourne les informations sur un compte

        String query = "SELECT username, dateAjoutCmpt as dateCreation " +
                "FROM compte " +
                "WHERE idCmpt = "+id;
        Hashtable result = Tools.getLastRecord(query);

        return result;
    }

    public Hashtable getRespInfo() {
        // retourne les informations sur le responsable

        System.out.println("iiiiiiiiiiiiiiiinnnnnnnnnnnnnnnnnnnnn getRespInfo");
        String query = "SELECT resp.matriculeResp, resp.nomResp, " +
                            "nat.nomNationalite as nationalite, " +
                            "fonc.nomFonc as fonction, " +
                            "fonc.dimFonc as dimFonction " +
                        "FROM compte as cp " +
                        "JOIN responsable AS resp ON cp.matriculeResp = resp.matriculeResp " +
                        "JOIN fonction AS fonc ON resp.idFonc = fonc.idFonc " +
                        "JOIN nationalite AS nat ON resp.idNationalite = nat.idNationalite " +
                        "WHERE cp.idCmpt = "+id;
        System.out.println(query);

        System.out.println(">>>> by getREsult : "+Tools.getResult(query));
        Hashtable result = Tools.getLastRecord(query);

        return result;
    }

    // getter and setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFonction() {
        return fonction;
    }

    public String getDimFonction() {
        // retourne le diminiutif de la fonction
        return dimFonction;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public int getId() {
        return id;
    }

    public int getIdResp() {
        String sql = "select resp.matriculeResp as matriculeResp " +
                "from compte as cp " +
                "join responsable as resp on cp.matriculeResp = resp.matriculeResp " +
                "WHERE cp.idCmpt = "+id;
        System.out.println("recuperatin idResp : " + sql);
        return (int) Tools.getLastRecord(sql).get("matriculeResp");
    }

    public String toString() {
        String str = "";
        str += "Compte appartenant a "+username;
        str += "\nFonction: "+fonction;
        str += "\nDate creation : "+dateCreation;
        return str;
    }

}
