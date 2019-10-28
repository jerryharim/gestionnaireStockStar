package controller.client;

import javafx.beans.property.SimpleStringProperty;

public class Client {
    private SimpleStringProperty nom, addresse, ville, tel, email;

    public Client(String nom, String addresse, String ville, String tel, String email) {
        this.nom = new SimpleStringProperty(nom);
        this.addresse = new SimpleStringProperty(addresse);
        this.ville = new SimpleStringProperty(ville);
        this.tel = new SimpleStringProperty(tel);
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

    public String getAddresse() {
        return addresse.get();
    }

    public SimpleStringProperty addresseProperty() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse.set(addresse);
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

    public String getTel() {
        return tel.get();
    }

    public SimpleStringProperty telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel.set(tel);
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
