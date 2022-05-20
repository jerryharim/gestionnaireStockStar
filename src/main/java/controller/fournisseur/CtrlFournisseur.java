package controller.fournisseur;

import controller.CtrlMainGui;
import controller.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import modele.ModFournisseur;

import java.util.Hashtable;
import java.util.ListIterator;


public class CtrlFournisseur extends GUI {
    /*
    *   Controler for addFournisseur and fournisseur container
    */

    @FXML private TableView<Fournisseur> tableFrs;
    @FXML private Pane container, rootBody;

    private ModFournisseur modFrs = new ModFournisseur();
    private ObservableList listFrs = FXCollections.observableArrayList();

    @FXML
    public void addNewFrns() {
        /*Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/fournisseur/addFrsInfo");*/
        super.changeChildren(container, new AddFournisseur());
    }

    @FXML
    public void delFrns() {
        /*Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/fournisseur/addFrsInfo");*/
        Fournisseur frs = tableFrs.getSelectionModel().getSelectedItem();
        listFrs.remove(frs);
    }


    @FXML public void initialize() {
        initTable();
    }

    private void initTable() {
        listFrs = getListFournisseur();
        String[] valuesProperty = {"nom", "ville", "rue", "addresse", "telephone", "email"};
        super.initTable(tableFrs, listFrs, valuesProperty);
    }


    private ObservableList getListFournisseur() {
        // a faire

        ObservableList<Fournisseur> list = FXCollections.observableArrayList();
        ListIterator li = modFrs.getListFournisseur().listIterator();

        while(li.hasNext()) {
            Hashtable records = (Hashtable) li.next();
            String nom = (String) records.get("nom");
            String rue = (String) records.get("rue");
            String ville = (String) records.get("ville");
            String tel = (String) records.get("telephone");
            String addresse = (String) records.get("addresse");
            String email = (String) records.get("email");
            list.add(new Fournisseur(nom,ville,rue,addresse,tel,email));
        }

        return list;
    }

}
