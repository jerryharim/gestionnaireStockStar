package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.Account;
import principale.Main;

import java.io.IOException;
import java.util.Optional;

import static jdk.nashorn.internal.objects.Global.Infinity;


public class CtrlMainGui extends GUI {

    @FXML private VBox mainCenter;
    @FXML private Pane root, center, navigation;
    @FXML private Label sideUsernameUser, topUsernameUser, fonctionUser;
    @FXML protected Label labWindowTitle;

    protected SimpleStringProperty centerView = new SimpleStringProperty();
    private Account account;
    private boolean hideNavigation = false;

    private double stageX, stageY;

    public CtrlMainGui() {
        account = Main.getAccount();

        // change mainCenter content
        centerView.addListener((obs, oldVal, nVal) -> {
            // nVal est un string contenant l'url du vue en cours
            super.changeChildren(mainCenter, nVal);
        });
    }

    @FXML public void initialize() {

        System.out.println("init main gui");
        initAccountInfo();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Pane pane = loader.load();
            VBox.setVgrow(mainCenter, Priority.ALWAYS);
            mainCenter.setMaxWidth(Infinity);
            super.changeChildren(mainCenter, pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void changeCenter(String fxmlChild) {
        super.changeChildren(mainCenter, fxmlChild);
    }

    private void initAccountInfo() {
		// initialisation des informations sur le compte
		
        String username = account.getUsername();
        String fonction = account.getDimFonction();
        sideUsernameUser.setText(username);
        topUsernameUser.setText(username);
        fonctionUser.setText(fonction);
    }

    // change view
    @FXML public void showHome()   { centerView.set("../view/home");   }
    @FXML public void showClient() { centerView.set("../view/client/client"); }
    @FXML public void showStock()  { centerView.set("../view/stock");  }
    @FXML public void showPersonnel()   { centerView.set("../view/responsable/responsable"); }
    @FXML public void showFournisseur() { centerView.set("../view/fournisseur/fournisseur"); }
    @FXML public void showPartenaire()  { centerView.set("../view/partenaire"); }
    @FXML public void showDashboard()   { centerView.set("../view/dashboard");  }
    @FXML public void showCommanderProduit() { centerView.set("../view/commanderProduit"); }
    @FXML public void showFormLivraison()    { centerView.set("../view/livraison");        }
    @FXML public void showCommandeHistory()  { centerView.set("../view/historiqueCommande"); }
    @FXML public void showSuivitCmd()  { centerView.set("/view/suivitCommande"); }

    // move state
    @FXML public void minimizeWindow(ActionEvent e) {
        System.out.println("Minimize stage not yet implement");
    }

    @FXML public void closeWindow() {
        if(confirmLogout())
            Platform.exit();
    }

    private boolean confirmLogout() {
		// confirmation before exiting application if not logged out

        Alert alertLogOut = new Alert(Alert.AlertType.CONFIRMATION);
        alertLogOut.setTitle("Deconnexion");
        alertLogOut.setHeaderText(null);
        alertLogOut.setContentText("Vous devez d'abord vous deconnecter");

        ButtonType deconnexion = new ButtonType("Deconnexion");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertLogOut.getButtonTypes().setAll(cancel, deconnexion);

        Optional<ButtonType> response = alertLogOut.showAndWait();
        if( response.get() == deconnexion )
            return true;

        return false;
    }

    public void collapseNav(ActionEvent event) {
        // collaspe side navigation

        if(!hideNavigation) {
			center.getChildren().remove(navigation);
            hideNavigation = true;
        } else {
            center.getChildren().add(0, navigation);
            hideNavigation = false;
        }

    }

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
