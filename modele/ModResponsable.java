package modele;

import controller.FindOption;
import java.sql.*;
import java.util.ArrayList;

import principale.Main;

public class ModResponsable {
    private static Connection conn = Main.getConnection();
    private PreparedStatement prepNormalFind;
    private PreparedStatement prepAdvancedFind;

    public ModResponsable() {
        try {
            String query = "Select * from responsable where nomResp=? OR prenomResp=?";
            prepNormalFind = conn.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList getResponsableList() {
        // retourne un Hashtable qui contient la liste des responsable
        // avec comme cle : nomColonne, valeur : record list : arrayList

        String query = "SELECT rp.matriculeResp as matricule, " +
                            "rp.nomResp as nom, " +
                            "rp.prenomResp as prenom, " +
                            "f.nomFonc as fonction " +
                        "FROM responsable as rp " +
                        "JOIN fonction as f ON rp.idFonc = f.idFonc";

        return Tools.getResult(query);
    }

    public ResultSet find(FindOption option, String keyword) {
        // retourne tous le occurance ou est localiser ce mot cle
        // recherche normal : ne recherche que le nom et le prenom
        ResultSet res = null;

        if (option.equals(FindOption.NORMAL)) {
            try {
                for (int i = 1; i <= 2; i++)
                    prepNormalFind.setString(i, keyword);
                res = prepNormalFind.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // advanced
        }

        return res;
    }

    public void addResponsable(ArrayList listValue)
    {
//        // insert responsable information to database
//
//        // convertion
//        ArrayList<String> colList = new ArrayList<>();
//        colList.add("nomResp");
//        colList.add("prenomResp");
//        colList.add("sexeResp");
//        colList.add("CINResp");
//        colList.add("anivResp");
//        colList.add("villeResp");
//        colList.add("rueResp");
//        colList.add("addrResp");
//        colList.add("telResp");
//        colList.add("emailResp");
//        colList.add("idNationalite");
//        colList.add("idFonc");
//        WindowTools.saveRecords("responsable", colList, listValue);
    }
}
