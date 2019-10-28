package controller.responsable;

import controller.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import modele.ModResponsable;
import modele.Tools;

import java.util.ArrayList;
import java.util.Hashtable;

public class CtrlAddResponsable extends GUI {

    @FXML private TextField tfNom, tfPrenom, tfAddresse, tfRue, tfVille,
                            tfTel, tfEmail, tfCIN;
    @FXML private ComboBox<String> cbNationalite, cbFonction;
    @FXML private Pane container;
    @FXML private DatePicker dpAniv;
    @FXML private RadioButton rbMasculin, rbFeminin;

    private Pane parent;
    private ModResponsable modResp = new ModResponsable();
    private ToggleGroup sexeToggle;
    private String sexe;

    @FXML public void initialize() {

        // sexe toggleGroup
        rbFeminin.setUserData("F");
        rbMasculin.setUserData("M");
        sexe = (String) rbMasculin.getUserData();

        sexeToggle = rbMasculin.getToggleGroup();
        sexeToggle.selectedToggleProperty().addListener((obs, oV, nV) -> {
            this.sexe = nV.getUserData().toString();
        });

        // comboBox : nationality and fonction
        ObservableList<String> obsFonction = getRecords("fonction", "nomFonc");
        cbFonction.setItems(obsFonction);

        ObservableList<String> obsNationalite = getRecords("nationalite", "nomNationalite");
        cbNationalite.setItems(obsNationalite);
    }

    private ObservableList getRecords(String tableName, String colName) {
        // recupere la liste des enregistrement dans un champ
        // et le convertit en observableList

        ObservableList obs = FXCollections.observableArrayList();
        ArrayList records = Tools.getRecordsIn(tableName, colName);

        for(int i=0; i<records.size(); i++)
            obs.add(records.get(i));

        return obs;
    }

    @FXML
    public void cancelAdd() {
        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent,"../../view/responsable/responsable");
    }

    @FXML
    public void validateAdd() {
        String nationalite = this.cbNationalite.getValue();
        String fonction = this.cbFonction.getValue();

        // recuperation des identifiant
        Hashtable records = Tools.getLastRecord("select idNationalite from nationalite " +
                "where nomNationalite = '"+nationalite+"'");
        int idNationalite = (int) records.get("idNationalite");

        records = Tools.getLastRecord("select idFonc from fonction " +
                "where nomFonc = '"+fonction+"'");
        int idFonc = (int) records.get("idFonc");

        String query = "INSERT INTO responsable(" +
                            "nomResp, prenomResp, sexeResp, CINResp, anivResp, " +
                            "villeResp, rueResp, addrResp, telResp, emailResp, idNationalite, idFonc) " +
                        "VALUES (" +
                            "'" + tfNom.getText() + "', " +
                            "'" + tfPrenom.getText() + "', " +
                            "'" + sexe + "', " +
                            "'" + tfCIN.getText() + "', " +
                            "'" + dpAniv.getValue() + "', " +
                            "'" + tfVille.getText() + "', " +
                            "'" + tfRue.getText() + "', " +
                            "'" + tfAddresse.getText() + "', " +
                            "'" + tfTel.getText() + "', " +
                            "'" + tfEmail.getText() + "', " +
                            idNationalite + ", " +
                            idFonc + ")";
        System.out.println("addResponable :"+query);

        Tools.saveRecords(query);
        System.out.println("ajout terminer");

        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/responsable/responsable");
    }

}
