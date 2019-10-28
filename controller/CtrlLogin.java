package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.ModLogin;
import principale.Main;

import java.io.IOException;

public class CtrlLogin extends GUI {
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private Label labError;

    private double stageX, stageY;

    @FXML public void getConnection(ActionEvent e) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        int id = ModLogin.estMembre(username, password);
        if(id >= 0) {
            // display main controller.gui
            try {
                System.out.println("access granted");
                chargerCompte(id);

                // change scene view
                Parent parent = FXMLLoader.load(getClass().getResource("/view/mainGui.fxml"));
                Scene nextScene = new Scene(parent);

                Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                window.setX(200);
                window.setY(100);
                window.setScene(nextScene);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            labError.setText("Access refuser");
        }
    }

    private void chargerCompte(int idCompte) {
        // creer l'objet compte
        System.out.println("chargement du compte avec l'identifiant numero "+idCompte);
        Main.loadAccount(idCompte);
    }

    @FXML public void exit() {
        Platform.exit();
    }

    /* deplacement fenetre */
    @FXML
    public void mousePressed(MouseEvent mouseEvent) {
        stageX = mouseEvent.getSceneX();
        stageY = mouseEvent.getSceneY();
    }

    @FXML
    public void mouseDragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - stageX);
        stage.setY(mouseEvent.getScreenY() - stageY);
    }
}
