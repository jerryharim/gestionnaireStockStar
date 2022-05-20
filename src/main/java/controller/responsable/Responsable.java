package controller.responsable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Responsable {

    SimpleIntegerProperty numMatr;
    SimpleStringProperty nomResp, prenomResp, fonctionResp;

    public Responsable(int numMatr, String nomResp, String prenomResp, String fonctionResp) {
        this.numMatr = new SimpleIntegerProperty(numMatr);
        this.nomResp = new SimpleStringProperty(nomResp.toUpperCase());
        this.prenomResp = new SimpleStringProperty(prenomResp);
        this.fonctionResp = new SimpleStringProperty(fonctionResp);
    }

    public int getNumMatr() {
        return numMatr.get();
    }

    public SimpleIntegerProperty numMatrProperty() {
        return numMatr;
    }

    public void setNumMatr(int numMatr) {
        this.numMatr.set(numMatr);
    }

    public String getNomResp() {
        return nomResp.get();
    }

    public SimpleStringProperty nomRespProperty() {
        return nomResp;
    }

    public void setNomResp(String nomResp) {
        this.nomResp.set(nomResp);
    }

    public String getPrenomResp() {
        return prenomResp.get();
    }

    public SimpleStringProperty prenomRespProperty() {
        return prenomResp;
    }

    public void setPrenomResp(String prenomResp) {
        this.prenomResp.set(prenomResp);
    }

    public String getFonctionResp() {
        return fonctionResp.get();
    }

    public SimpleStringProperty fonctionRespProperty() {
        return fonctionResp;
    }

    public void setFonctionResp(String fonctionResp) {
        this.fonctionResp.set(fonctionResp);
    }
}
