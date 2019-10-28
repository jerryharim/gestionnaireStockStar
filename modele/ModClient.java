package modele;

import controller.FindOption;
import principale.Main;

import java.sql.*;
import java.util.ArrayList;

public class ModClient {
    private static Connection conn = Main.getConnection();
    private PreparedStatement prepNormalFind;
    private PreparedStatement prepAdvancedFind;

    public ModClient() {
        try {
            String query = "Select * from client where nomCli=?";
            prepNormalFind = conn.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList getClientList() {
        // retourne un le resultat de la Requete
        String query = "SELECT nomCli as nom, " +
                            "telCli as telephone, " +
                            "emailCli as email, " +
                            "villeCli as ville, " +
                            "rueCli as rue, " +
                            "addrCli as addresse " +
                        "FROM client";
        return Tools.getResult(query);
    }

    public void saveClient(String nom, String addr, String rue, String ville, String tel, String email) {
        // save a fournisseur to db

        String query = "insert into usine(nomUsine, villeUsine, rueUsine, addrUsine, telUsine, emailUsine) " +
                "values(?,?,?,?,?,?)";
        try {
            PreparedStatement prepaSta = conn.prepareStatement(query);

            prepaSta.setString(1, nom);
            prepaSta.setString(2, addr);
            prepaSta.setString(3, rue);
            prepaSta.setString(4, ville);
            prepaSta.setString(5, tel);
            prepaSta.setString(6, email);

            prepaSta.execute();
        } catch (SQLException e) {
            System.out.println("Probleme sql rencontrer : sauvegarde impossible");
            e.printStackTrace();
        }
    }

    public ResultSet find(FindOption option, String keyword) {
        // retourne tous le occurance ou est localiser ce mot cle
        // recherche normal : ne recherche que le nom et le prenom
        ResultSet res = null;

        if(option.equals(FindOption.NORMAL)) {
            try {
                for(int i=1; i<=2; i++)
                    prepNormalFind.setString(i, keyword);
                res = prepNormalFind.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            // advanced
        }

        return res;
    }
}
