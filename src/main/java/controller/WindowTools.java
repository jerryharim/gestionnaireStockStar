package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import javax.swing.text.html.Option;
import java.util.Optional;

public class WindowTools {
    private static Double xPos = 400d, yPos = 250d;

    public static void alertEmpty(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setX(xPos);
        alert.setX(yPos);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    public static void alertInformation(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setX(xPos);
        alert.setX(yPos);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    public static boolean alertConfirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setX(xPos);
        alert.setX(yPos);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        ButtonType btnYes = new ButtonType("Oui");
        ButtonType btnNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnYes, btnNo);
        Optional<ButtonType> rep = alert.showAndWait();

        if(rep.get() == btnYes) {
            return true;
        }

        return false;
    }
}
