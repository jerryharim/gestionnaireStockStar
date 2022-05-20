package controller.responsable;

import controller.FindOption;
import controller.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modele.ModResponsable;

import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

public class CtrlResponsable extends GUI {
    @FXML private Pane paneFindMoreOpt;
    @FXML private TableView respTable;
    @FXML private TableColumn<Responsable, String> respColNom, respColPrenom, respColMatric,respColFonction;
    @FXML private TextField findInput;
    @FXML private Button btnAddPersonnel;
    @FXML private HBox hbOptBarListPers;
    @FXML private HBox hbOptBarAddPers;
    @FXML private AnchorPane optionBar;
    @FXML private VBox container;

    private ObservableList listResp;
    private FindOption option = FindOption.NORMAL;
    private ModResponsable modResp = new ModResponsable();

    @FXML
    private void viewFindMoreOpt() {
        paneFindMoreOpt.setVisible(true);
    }

    @FXML
    public  void addNewPersonnel() {
        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/responsable/addResponsable");
        System.out.println(parent.getChildren());
    }

    @FXML public void findPersonnel() {
        // rechercher un personnel
        String keyword = findInput.getText();

        if(option.equals(FindOption.NORMAL)) {
            ResultSet res = modResp.find(FindOption.NORMAL, keyword);
            if(res != null) {
                this.listResp.clear();
                try {
                    while(res.next()) {
                        int matricule = res.getInt("matriculeResp");
                        String nomResp = res.getString("nomResp");
                        String prenomResp = res.getString("prenomResp");

                        this.listResp.add(new Responsable(matricule, nomResp, prenomResp, "Resp Vente"));
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // advanced
        }
    }
    // table:

    // for initializing tableView content
    @FXML public void initialize() {
        initTable();
    }

    private void initTable() {
        String[] propertyValues = {"numMatr", "nomResp", "prenomResp", "fonctionResp"};
        super.initTable(respTable, getListResp(), propertyValues);

    }

    private ObservableList getListResp() {
        // retourne une list d'objet Responsable

        ObservableList<Responsable> list = FXCollections.observableArrayList();
        ListIterator li = modResp.getResponsableList().listIterator();

        while(li.hasNext()) {
            Hashtable records = (Hashtable) li.next();
            int matricule = (int) records.get("matricule");
            String nom = (String) records.get("nom");
            String prenom = (String) records.get("prenom");
            String fonction = (String) records.get("fonction");
            list.add(new Responsable(matricule, nom, prenom, fonction));
        }

        return list;
    }
}
