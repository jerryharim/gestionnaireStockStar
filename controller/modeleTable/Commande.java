package controller.modeleTable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Commande {
    SimpleStringProperty produit, fournisseur;
    SimpleIntegerProperty quantite;

    public Commande(String produit, String fournisseur, int quantite) {
        this.produit = new SimpleStringProperty(produit);
        this.fournisseur = new SimpleStringProperty(fournisseur);
        this.quantite = new SimpleIntegerProperty(quantite);
    }

    public String getProduit() {
        return produit.get();
    }

    public SimpleStringProperty produitProperty() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit.set(produit);
    }

    public String getFournisseur() {
        return fournisseur.get();
    }

    public SimpleStringProperty fournisseurProperty() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur.set(fournisseur);
    }

    public int getQuantite() {
        return quantite.get();
    }

    public SimpleIntegerProperty quantiteProperty() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }
}
