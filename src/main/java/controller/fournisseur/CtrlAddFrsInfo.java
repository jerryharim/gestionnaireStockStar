package controller.fournisseur;

import controller.Controls;
import controller.GUI;
import controller.WindowTools;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Tools;

import java.util.ArrayList;


public class CtrlAddFrsInfo extends GUI {
    @FXML private Pane container, rootBody, fieldsContainer, paneError;
    @FXML private TextField tfNom, tfAdresse, tfRue, tfVille, tfTel, tfEmail;
    @FXML private Button btnAddFrsProds;
    @FXML private Label labError;
    private ArrayList<TextField> listEmptyFields;

	@FXML private void initialize() {
	    // invisible
	    fieldsContainer.getChildren().remove(paneError);
    }

	private void openPane(String path) {
		// remplace le panel en cours par une autre
        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, path);
	}

    @FXML
    public void cancelAdd() {
        openPane("../../view/fournisseur/fournisseur");
    }


    public void cancelAddFrs(ActionEvent event) {
    }

    public void validateAddFrs(ActionEvent event) {
    }

    public void selectProduits(ActionEvent event) {
        super.changeChildren(rootBody, "../../view/fournisseur/addProduitFrs");
    }

	// getters and setters ---

	public Button getNextBtn() {
        return this.btnAddFrsProds;
    }

    // save records -----

    public void saveRecords() {
	    // sauvegarde temporairement les contenus des inputs
    }

    // input validator ===========

    private boolean noFieldsEmpty() {
        // return arrayList of empty fields
        TextField[] fields = { tfNom, tfVille, tfRue, tfAdresse, tfTel, tfEmail };
        listEmptyFields = Controls.getEmptyFields(fields);

        if( !listEmptyFields.isEmpty() ) {
            // un des champs est incorrect

            // affichage paneError
            if( !fieldsContainer.getChildren().contains(paneError) ) {
                fieldsContainer.getChildren().add(0, paneError);
                labError.setText("Tous les champ sont obligatoire");
            }

            for(TextField tf : listEmptyFields )
                tf.setStyle("-fx-border-color: red; -fx-border-width: 1;");

            return false;
        }

        return true;
    }

    private void inputError(TextField tf) {
	    // change l'affichage du textField s'il y a une erreur

        String temp = tf.getText();
        tf.clear();
        tf.setPromptText("Incorrect : " + temp);
        tf.setStyle("-fx-border-color: red; -fx-border-width: 1;");
    }

    private boolean okFieldsInput() {
        // verifie si les donnees saisie dans les champs sont correct

        // telephone
        boolean telCorrect = Controls.findRegex(
                "03[2349]\\d{7}",
                tfTel.getText().trim()
        );
        boolean emailCorrect = Controls.findRegex(
                ".+?@.{2,20}\\.\\w{2,4}",
                tfEmail.getText().trim()
        );

        // affichage erreur
        if(!telCorrect)
            inputError(tfTel);
        if(!emailCorrect)
            inputError(tfEmail);

        return telCorrect && emailCorrect;
    }

    public boolean okFields() {
	    boolean empty = noFieldsEmpty();
	    boolean inputOk = okFieldsInput();

        return empty && inputOk;
    }

    public void getQuery() {
        // add fournisseur to db and switch scene to fournisseur list

        String sql = "INSERT INTO usine(nomUsine, villeUsine, rueUsine, addrUsine, telUsine, emailUsine) " +
                "VALUES(" +
                "'" + tfNom.getText() + "', " +
                "'" + tfVille.getText() + "', " +
                "'" + tfRue.getText() + "', " +
                "'" + tfAdresse.getText() + "', " +
                "'" + tfTel.getText() + "', " +
                "'" + tfEmail.getText() +
                "')";

        Tools.saveRecords(sql);
    }

}
