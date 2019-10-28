package controller.fournisseur;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProduitsFrs {
    private SimpleStringProperty produit, categorie;
    private SimpleIntegerProperty volume;

    public ProduitsFrs(String produit, String categorie, int volume) {
        this.produit = new SimpleStringProperty(produit);
        this.categorie = new SimpleStringProperty(categorie);
        this.volume = new SimpleIntegerProperty(volume);
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

    public int getVolume() {
        return volume.get();
    }

    public SimpleIntegerProperty volumeProperty() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume.set(volume);
    }
}