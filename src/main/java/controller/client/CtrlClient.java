package controller.client;

import controller.CtrlMainGui;
import controller.FindOption;
import controller.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import modele.ModClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlClient extends GUI {

    @FXML private Pane paneFindMoreOpt;
    @FXML private TableView tableCli;
    @FXML private TableColumn<Client, String> colNom, colAdresse, colVille, colTel, colEmail;
    @FXML private TextField findInput;
    @FXML private Pane container;

    ObservableList listCli;
    FindOption option = FindOption.NORMAL;
    ModClient modCli = new ModClient();

    @FXML
    private void viewFindMoreOpt() {
        paneFindMoreOpt.setVisible(true);
    }

    @FXML
    private  void addNewClient() {
        System.out.println("add new Client");
        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../../view/client/addClient");
    }

    @FXML public void findClient() {
        // rechercher un personnel
        String keyword = findInput.getText();

        if(option.equals(FindOption.NORMAL)) {
            ResultSet res = modCli.find(FindOption.NORMAL, keyword);
            if(res != null) {
                this.listCli.clear();
                try {
                    while(res.next()) {
                        String nom = res.getString("nomCli");
                        String addr = res.getString("addrCli");
                        String rue = res.getString("rueCli");
                        String ville = res.getString("villeCli");
                        String addresse = addr + " " + rue + " " + ville;
                        String tel = res.getString("telCli");
                        String email = res.getString("emailCli");

                        this.listCli.add(new Client(nom, addresse, ville, tel, email));
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // advanced
        }
    }

    @FXML public void initialize() {
        // tableView
        String[] propertyValues = {"nom", "addresse", "ville", "tel", "email"};
        listCli = getListCli();
        System.out.println(listCli);
        super.initTable(tableCli, listCli, propertyValues);
    }

    private ObservableList getListCli() {
        // retourne la liste de tous les responsables

        ObservableList<Client> list = FXCollections.observableArrayList();
        ListIterator li = modCli.getClientList().listIterator();

        while(li.hasNext()) {
            Hashtable records = (Hashtable) li.next();
            String nom = (String) records.get("nom");
            String ville = (String) records.get("ville");
            String tel = (String) records.get("telephone");
            String email = (String) records.get("email");
            String rue = (String) records.get("rue");
            String addr = (String) records.get("addresse");
            String addresse = addr + " " + rue;
            list.add(new Client(nom,addresse,ville,tel,email));
        }

        return list;
    }
}
