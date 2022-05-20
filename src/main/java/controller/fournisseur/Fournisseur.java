package controller.fournisseur;

import javafx.beans.property.SimpleStringProperty;

public class Fournisseur  {

    SimpleStringProperty nom, ville, rue, addresse, telephone, email;

    public Fournisseur(String nom, String ville, String rue, String addresse, String telephone, String email) {
        this.nom = new SimpleStringProperty(nom);
        this.ville = new SimpleStringProperty(ville);
        this.rue = new SimpleStringProperty(rue);
        this.addresse = new SimpleStringProperty(addresse);
        this.telephone = new SimpleStringProperty(telephone);
        this.email = new SimpleStringProperty(email);
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getVille() {
        return ville.get();
    }

    public SimpleStringProperty villeProperty() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }

    public String getRue() {
        return rue.get();
    }

    public SimpleStringProperty rueProperty() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue.set(rue);
    }

    public String getAddresse() {
        return addresse.get();
    }

    public SimpleStringProperty addresseProperty() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse.set(addresse);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
