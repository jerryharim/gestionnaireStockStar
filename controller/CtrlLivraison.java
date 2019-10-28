package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlLivraison {
    @FXML private TextField tfClient, tfLivreur, tfProduit;

    private ObservableList<String> listClient = FXCollections.observableArrayList();
    private ObservableList<String> listLivreur = FXCollections.observableArrayList();
    private ObservableList<String> listProd = FXCollections.observableArrayList();

    @FXML public void initialize() {
    }

    public void initFieldClient() {
        ArrayList records = new ArrayList<>();
        ListIterator<Hashtable> li = records.listIterator();
        while(li.hasNext()) {
            Hashtable record = li.next();
            int id = (int) record.get("id");
            String nom = (String) record.get("nom");
            listClient.add(nom);
        }
    }

    public void initFieldLivreur() {

    }

    public void initFieldProduit() {

    }
}
