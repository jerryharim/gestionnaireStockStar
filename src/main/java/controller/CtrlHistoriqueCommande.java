package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import modele.ModStock;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlHistoriqueCommande extends GUI {
    @FXML private TableView tableHistory;
    @FXML private Pane root;

    private ObservableList<HistoriqueCommande> listHistory = FXCollections.observableArrayList();

    @FXML public void initialize() {
        initTableHistory();
    }

    private void initTableHistory() {
        listHistory = getListHistory();
        String[] properyValues = {"numero", "produit", "quantite", "dateDemande", "dateVoulu", "fournisseur", "responsable"};
        super.initTable(tableHistory, listHistory, properyValues);
    }

    private ObservableList<HistoriqueCommande> getListHistory() {
        ObservableList obs = FXCollections.observableArrayList();

        ArrayList records = ModStock.getLastCommande();
        ListIterator<Hashtable> li = records.listIterator();

        while(li.hasNext()) {
            Hashtable commandHistory = li.next();
            int numero = (int) commandHistory.get("numeroCommande");
            int quantite = (int) commandHistory.get("quantite");
            String produit = (String) commandHistory.get("produit");
            String dateDmd = commandHistory.get("dateDemande").toString();
            String dateVoulu = commandHistory.get("dateVoulu").toString();
            String nomResp = (String) commandHistory.get("nomResp");
            String prenomResp = (String) commandHistory.get("prenomResp");
            String responsable = nomResp.toUpperCase().charAt(0)+". "+prenomResp;
            String fournisseur = (String) commandHistory.get("fournisseur");

            obs.add(new HistoriqueCommande(numero, produit, quantite, dateDmd, dateVoulu, fournisseur, responsable));
        }

        return obs;
    }
}
