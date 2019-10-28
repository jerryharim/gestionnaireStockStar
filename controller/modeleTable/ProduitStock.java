package controller.modeleTable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProduitStock {
    private SimpleStringProperty produit, categorie, ranger, depot;
    private SimpleIntegerProperty quantite;

    public ProduitStock(String produit, String categorie, String ranger, String depot, int quantite) {
        this.produit = new SimpleStringProperty(produit);
        this.categorie = new SimpleStringProperty(categorie);
        this.ranger = new SimpleStringProperty(ranger);
        this.depot = new SimpleStringProperty(depot);
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

    public String getCategorie() {
        return categorie.get();
    }

    public SimpleStringProperty categorieProperty() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public String getRanger() {
        return ranger.get();
    }

    public SimpleStringProperty rangerProperty() {
        return ranger;
    }

    public void setRanger(String ranger) {
        this.ranger.set(ranger);
    }

    public String getDepot() {
        return depot.get();
    }

    public SimpleStringProperty depotProperty() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot.set(depot);
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
