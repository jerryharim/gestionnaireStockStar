package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import modele.Account;
import principale.Main;


public class CtrlHome extends CtrlMainGui {
    @FXML private Pane rootHome;
    @FXML private Label labUsername;

    private Pane parent;
    Account account = Main.getAccount();

    @FXML public void initialize() {
		// maximize Controls

		// welcome msg
        parent = (Pane) rootHome.getParent();
        labUsername.setText(account.getUsername()+"!");
    }

    @FXML public void showPartenaire() {
        super.changeCenter("../view/partenaire");
    }

    @FXML public void showPersonnel() {
        super.changeChildren(parent, "../view/responsable/responsable");
    }

    @FXML public void showStock() {
        super.changeChildren(parent, "../view/stock");
    }

    @FXML public void showCreation() {
        System.out.print("not yet implemented");
    }

    @FXML public void showHistory() {
        System.out.println("history not yet implemented");
    }

    public void showDashboard(ActionEvent event) {
    }
}
