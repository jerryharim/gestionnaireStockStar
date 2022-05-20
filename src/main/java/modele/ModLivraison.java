package modele;

import java.util.ArrayList;

public class ModLivraison {
    public static ArrayList<String> getClients() {
        String sql = "SELECT idCli id, nomCli as client FROM client";
        return Tools.getResult(sql);
    }

    public static ArrayList<String> getLivreurs() {
        String sql = "SELECT resp.matriculeResp matricule, resp.nomResp nom, " +
                "fonc.idFonc idFonction, fonc.nomFonc fonction " +
                "FROM responsable resp " +
                "JOIN fonction fonc ON resp.idFonc = fonc.idFonc";
        return Tools.getResult(sql);
    }

    public static ArrayList<String> getProduits() {
        String sql = "SELECT idProd id, designProd nom FROM produit";
        return Tools.getResult(sql);
    }
}
