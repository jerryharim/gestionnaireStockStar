package controller.fournisseur;

import controller.GUI;
import controller.WindowTools;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Tools;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AddFournisseur extends Pane {
    /* conteneur mere pour l'ajout de fournisseur
    *  fonction : gestion des vues a afficher
    * */

    private Pane paneAddFrsInfo, paneAddFrsProds;
	private CtrlAddFrsInfo ctrlFrsInfo;
	private CtrlAddFrsProds ctrlFrsProds;
	private Button btnNext, btnPrevious, btnValidateAll;

    public AddFournisseur() {
        // instance des vues enfants

        FXMLLoader viewAddFrsInfo = new FXMLLoader(getClass().getResource("../../view/fournisseur/addFrsInfo.fxml"));
        FXMLLoader viewAddFrsProds = new FXMLLoader(getClass().getResource("../../view/fournisseur/addFrsProds.fxml"));
        try {
            paneAddFrsInfo = viewAddFrsInfo.load();
			ctrlFrsInfo = viewAddFrsInfo.getController();
            paneAddFrsProds = viewAddFrsProds.load();
			ctrlFrsProds = viewAddFrsProds.getController();

			// defaul view
            openView(paneAddFrsInfo);

			// next
            btnNext = ctrlFrsInfo.getNextBtn();
            btnNext.setOnAction(event -> {
                if( ctrlFrsInfo.okFields() )
                    openView(paneAddFrsProds);
            });

            // previous
            btnPrevious = ctrlFrsProds.getBtnPrevious();
            btnPrevious.setOnAction(event -> {
                this.getChildren().setAll(paneAddFrsInfo);
            });

            // validate all
            btnValidateAll = ctrlFrsProds.getBtnValidateAll();
            btnValidateAll.setOnAction(event -> {
                // recuperer les requetes de sauvegarde et les executer
                ctrlFrsInfo.getQuery();
                ctrlFrsProds.getQuery();

                // redirection
                boolean openFrsList = WindowTools.alertConfirmation("Afficher la liste des fournisseurs?");
                if(openFrsList) {
                    Pane parent = (Pane) this.getParent();
                    ctrlFrsInfo.changeChildren(parent, "/view/fournisseur/fournisseur");
                } else {
                    Pane parent = (Pane) this.getParent();
                    ctrlFrsInfo.changeChildren(parent, "/view/home");
                }

            });

        } catch (IOException e) {
			System.out.println("Exception founds in addFournisseur");
            e.printStackTrace();
        }
    }

    private void openView(Pane pane) {
        this.getChildren().setAll(pane);
    }
}
