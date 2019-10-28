package modele;

import principale.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ModStock {
    // modele pour le stock

    public ResultSet getTotal() {
        String query = "select count(pr.idProd) as totalProd, sum(pr.qteEnStock) as quantite " +
                "from produit as pr "   ;

        try {
            Connection conn = Main.getConnection();
            Statement state = conn.createStatement();
            return state.executeQuery(query);
        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    public ArrayList getListStock() {
        // return : produit designation, rangement, depot, quantite

        String query = "select pr.designProd as produit, pr.qteEnStock as quantite, " +
                "ca.nomCateg as categorie, " +
                "ra.refRang as ranger, " +
                "dp.refDepot as depot " +
                "from produit as pr " +
                "join categorie as ca on pr.idCateg = ca.idCateg " +
                "join rangement as ra on pr.idRang = ra.idRang " +
                "join depot as dp on ra.idDepot = dp.idDepot";

        return Tools.getResult(query);
    }

    public static ArrayList getLastCommande() {
        // return : historique de commande

        String query = "SELECT ddp.idDmdProd as numeroCommande, " +
                            "ddp.qteDemander as quantite, " +
                            "prod.designProd as produit, " +
                            "us.nomUsine as fournisseur, " +
                            "dp.dateDmdProd as dateDemande, " +
                            "dp.dateLivraisonVoulu as dateVoulu, " +
                            "resp.nomResp as nomResp, " +
                            "resp.prenomResp as prenomResp " +
                        "FROM detailDemandeProduit as ddp " +
                        "JOIN produit as prod ON ddp.idProd = prod.idProd " +
                        "JOIN demandeProduit as dp ON ddp.idDmdProd = dp.idDmdProd " +
                        "JOIN usine as us ON dp.idUsine = us.idUsine " +
                        "JOIN responsable as resp ON dp.matriculeResp = resp.matriculeResp";

        return Tools.getResult(query);
    }
}
