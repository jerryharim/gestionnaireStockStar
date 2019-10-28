package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HistoriqueCommande {

    public SimpleStringProperty produit, dateDemande, dateVoulu, responsable, fournisseur;
    public SimpleIntegerProperty numero, quantite;

    public HistoriqueCommande(int numero, String produit, int quantite, String dateDemande, String dateVoulu, String fournisseur, String responsable) {
        this.produit = new SimpleStringProperty(produit);
        this.dateDemande = new SimpleStringProperty(dateDemande);
        this.dateVoulu = new SimpleStringProperty(dateVoulu);
        this.fournisseur = new SimpleStringProperty(fournisseur);
        this.responsable = new SimpleStringProperty(responsable);
        this.numero = new SimpleIntegerProperty(numero);
        this.quantite = new SimpleIntegerProperty(quantite);
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

    public String getProduit() {
        return produit.get();
    }

    public SimpleStringProperty produitProperty() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit.set(produit);
    }

    public String getDateDemande() {
        return dateDemande.get();
    }

    public SimpleStringProperty dateDemandeProperty() {
        return dateDemande;
    }

    public void setDateDemande(String dateDemande) {
        this.dateDemande.set(dateDemande);
    }

    public String getDateVoulu() {
        return dateVoulu.get();
    }

    public SimpleStringProperty dateVouluProperty() {
        return dateVoulu;
    }

    public void setDateVoulu(String dateVoulu) {
        this.dateVoulu.set(dateVoulu);
    }

    public String getResponsable() {
        return responsable.get();
    }

    public SimpleStringProperty responsableProperty() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable.set(responsable);
    }

    public int getNumero() {
        return numero.get();
    }

    public SimpleIntegerProperty numeroProperty() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
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
