package modele;

import principale.Main;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class ModProduit {
    Connection conn = Main.getConnection();

    public static ArrayList getListProduit() {
        // retourne la liste des noms de produit
        String query = "select designProd as designation from produit";
        return Tools.getResult(query);
    }

    public static ArrayList getCategProduit() {
        // retourne les categories des produits

        String query = "SELECT nomCateg as categorie FROM categorie";
        ArrayList res = Tools.getResult(query);
        return  res;
    }

    public static void saveDemandeProduit(int idFrs, String dateDemande, String dateVoulu, int idResp) {
        /* sauvegarde d'une commande de produit */
        String sql = "insert into demandeProduit(dateDmdProd, dateLivraisonVoulu, idUsine, matriculeResp) " +
                "values(" +
                    "'"+ dateDemande +"', " +
                    "'"+ dateVoulu +"', " +
                    idFrs +", " +
                    idResp +
                ")";
        Tools.saveRecords(sql);
    }

    public static void saveDetailDmdProd(int idDmdProd, int idProd, int quantite) {
        /* sauvegarde des detail de commande */

        String sql = "insert into detailDemandeProduit values(" +
                        idDmdProd + ", " +
                        idProd + "," +
                        quantite +
                      ")";
        Tools.saveRecords(sql);
    }

    public int getMaxCageot(String produitName) {
        String query = "select pr.qteEnStock, ca.tailleCageot" +
                "from produit as pr " +
                "join cageot as ca on pr.idCageot = ca.idCageot " +
                "where pr.designProd=?";
        try {
            PreparedStatement prepaSta = conn.prepareStatement(query);
            prepaSta.setString(1, produitName);
            ResultSet res = prepaSta.executeQuery();
            return res.getInt("qteEnStock") / res.getInt("tailleCageot");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // min value
        return 0;
    }

    public static ArrayList getFournisseurFor(String produitName) {
        // retourne la liste de nom des fournisseur qui livre ce produit

        ArrayList res;
        String query =  "SELECT us.nomUsine AS nom " +
                        "FROM fournirProduit AS fp " +
                        "JOIN usine AS us ON fp.idUsine = us.idUsine " +
                        "JOIN produit AS pr ON fp.idProd = pr.idProd " +
                        "WHERE pr.designProd = '"+produitName+"'";
        res = Tools.getResult(query);

        return res;
    }

    public static ArrayList getListFrs() {
        String query = "select nomUsine as nom from usine";
        return Tools.getResult(query);
    }

    public static ArrayList getProduitsMinInfo() {
        // retourne la designation, le volume, la categorie des produits

        String query = "SELECT designProd as designation, " +
                "volumeProd as volume, " +
                "cat.nomCateg categorie " +
                "FROM produit as prod " +
                "JOIN categorie as cat ON prod.idCateg = cat.idCateg ";
        return Tools.getResult(query);
    }

    public static ArrayList getProduitsMinInfo(String categorie) {
        // retourne la liste des produits ayant ce categorie

        String query = "SELECT designProd as designation, " +
                "volumeProd as volume, " +
                "cat.nomCateg categorie " +
                "FROM produit as prod " +
                "JOIN categorie as cat ON prod.idCateg = cat.idCateg " +
                "WHERE cat.nomCateg = '"+ categorie +"'";
        return Tools.getResult(query);
    }

    public static void addCommandeProduit(
            LocalDate dateDmd, LocalDate dateVoulu, String produit,
            String fournisseur, int matriculeResp, int quantite
            ) {

        /* ajout des commandes de produit a destination d'un fournisseur
        * ---
        * insertiion du demande dans demandeProduit
        * et enregistrer ses produits dans la table detailDemandeProduit
        *
        * */

        // recuperation identifiants
        String sql = "SELECT idUsine FROM usine WHERE nomUsine='"+fournisseur+"'";
        Hashtable rep = Tools.getLastRecord(sql);
        int idFrs = (int) rep.get("idUsine");

        sql = "SELECT idProd FROM produit WHERE designProd='"+produit+"'";
        rep = Tools.getLastRecord(sql);
        int idProd = (int) rep.get("idProd");

        // into demandeProduit
        sql = "INSERT INTO demandeProduit ( " +
                    "dateDmdProd, dateLivraisonVoulu, idUsine, matriculeResp ) " +
                "VALUES (" +
                        "'"+dateDmd+"', " +
                        "'"+dateVoulu+"', " +
                        idFrs+", " +
                        matriculeResp+
                ")";
        System.out.println(sql);
        Tools.saveRecords(sql);

        // into detailDemandeProduit
        sql = "SELECT idDmdProd FROM demandeProduit order by idDmdProd";
        Hashtable lastIdDmdProd = Tools.getLastRecord(sql);
        int idDmdProd = (int) lastIdDmdProd.get("idDmdProd");
        System.out.println("last id Demande : " + idDmdProd);

        sql = "INSERT INTO detailDemandeProduit VALUES(" +
                idDmdProd+", " +
                idProd+", " +
                quantite +
                ")";
        Tools.saveRecords(sql);

    }
}
