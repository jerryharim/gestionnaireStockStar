package modele;

import java.util.ArrayList;

public class ModFournisseur {

    public static ArrayList getProduitInfo(String produit) {
        String query = "SELECT prod.designProd as produit, prod.volumeProd as volume, cat.nomCateg as categorie " +
                "FROM produit as prod " +
                "JOIN categorie as cat ON prod.idCateg = cat.idCateg " +
                "WHERE prod.designProd = '"+produit+"'";
        ArrayList infos = Tools.getResult(query);
        return infos;
    }

    public ArrayList getListFournisseur() {
        // retourne la liste de fournisseurs avec leur informations au complet

        String query = "select nomUsine as nom, " +
                            "villeUsine as ville, " +
                            "rueUsine as rue, " +
                            "addrUsine as addresse, " +
                            "telUsine as telephone, " +
                            "emailUsine as email " +
                        "from usine";

        return Tools.getResult(query);
    }

}
