package controller.client;

import controller.CtrlMainGui;
import controller.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.Tools;

public class CtrlAddClient extends GUI {
    @FXML private TextField tfNom, tfAdresse, tfRue, tfVille, tfTel, tfEmail;
    @FXML private Pane container;

    @FXML
    public void cancelAdd() {
        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/client/client");
    }

    @FXML
    public void validateAdd() {
        String sql = "INSERT INTO client(nomCli, telCli, emailCli, villeCli, rueCli, addrCli) " +
                "VALUES (" +
                    "'"+tfNom.getText()+"', " +
                    "'"+tfTel.getText()+"', " +
                    "'"+tfEmail.getText()+"', " +
                    "'"+tfVille.getText()+"', " +
                    "'"+tfRue.getText()+"', " +
                    "'"+tfAdresse.getText()+
                "')";
        Tools.saveRecords(sql);

        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/client/client");
    }
}
