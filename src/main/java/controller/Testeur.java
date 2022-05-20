package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Testeur {
    @FXML
    private TextField zoneDeSaisie;

    @FXML
    public void tester() {
        if(zoneDeSaisie.getText() != null || !zoneDeSaisie.getText().isEmpty()) {
            System.out.println("Not empty");
        } else {
            System.out.println("Empty");
        }
    }

    public void afficherSecond(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/testeur2.fxml"));
        scene.setRoot(parent);
    }

    public void afficherPremier(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/testeur.fxml"));
        scene.setRoot(parent);
    }
}
