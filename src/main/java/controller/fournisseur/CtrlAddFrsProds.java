package controller.fournisseur;


import controller.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import modele.ModFournisseur;
import modele.ModProduit;
import modele.Tools;
import principale.MosaiquePreview;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlAddFrsProds extends GUI {
    @FXML private ComboBox<String> cbCategorieProd;
    @FXML private ListView<String> lvProdFrs;
    @FXML private TableView<ProduitsFrs> tabProdFrs;
    @FXML private FlowPane fpListeProds;
    @FXML private Button btnPrevious;
    @FXML private Button btnValidateAdd;

    private ObservableList<String> listCategorieProd = FXCollections.observableArrayList();
    private ObservableList listProduitFrs = FXCollections.observableArrayList();
    private ObservableList listProduits = FXCollections.observableArrayList();
    private ObservableList listProduits2 = FXCollections.observableArrayList();

    public CtrlAddFrsProds() {
        initListProduits();
    }

    @FXML public void initialize() {
        lvProdFrs.setItems(listProduitFrs); // <<<<<<<<<<<<<<<,

        initCategProd();
        displayProduits("Tout");

        cbCategorieProd.valueProperty().addListener((observable, oldValue, newValue) -> {
            displayProduits(newValue);
        });

    }

    private void initListProduits() {
        // creer des objets MosaiquePreview
        // et les inserts dans la liste de produits

        ArrayList produits = ModProduit.getProduitsMinInfo();
        ListIterator liProd = produits.listIterator();
        while (liProd.hasNext()) {
            Hashtable resultat = (Hashtable) liProd.next();
            String produit = (String) resultat.get("designation");
            String categ = (String) resultat.get("categorie");
            int volume = (int) resultat.get("volume");

            // ajout au liste de produits du fournisseur des produits selectionner
            MosaiquePreview boxProduit = new MosaiquePreview(produit, categ, volume);
            listProduits.add(boxProduit);
        }
    }

    private void displayProduits(String categorie) {
        // affiche les produits appartenant a ce categorie

        // nettoyage
        fpListeProds.getChildren().clear();

        // recuperation des produits
        ArrayList produits;
        if( categorie.toString() == "Tout") {
            produits = ModProduit.getProduitsMinInfo();
        } else {
            produits = ModProduit.getProduitsMinInfo(categorie);
        }

        ListIterator liProd = produits.listIterator();
        if(liProd.hasNext()) {
            while (liProd.hasNext()) {
                Hashtable produit = (Hashtable) liProd.next();
                String nomProduit = (String) produit.get("designation");
                String categ = (String) produit.get("categorie");
                int volume = (int) produit.get("volume");
                // ajout au liste de produits du fournisseur des produits selectionner
                MosaiquePreview boxProduit = new MosaiquePreview(nomProduit, categ, volume);

                if( listProduitFrs.contains(nomProduit) )
                    boxProduit.setSelected(true);

                boxProduit.selectedProperty().addListener(observable -> {
                    if( boxProduit.isSelected() ) {
                        if( !listProduitFrs.contains(nomProduit) )
                            listProduitFrs.add(nomProduit);
                    } else {
                        listProduitFrs.remove(nomProduit);
                    }
                });

                fpListeProds.getChildren().add(boxProduit);

            }
        }
        else {

            // if empty result
            Label labInformation = new Label("Aucun resultat trouver");
            labInformation.setPadding(new Insets(10, 15, 10, 15));
            labInformation.setFont(Font.font(15));
            fpListeProds.getChildren().add(labInformation);
        }

    }

    private void initCategProd() {
        // initiliase les valuers par defaut du comboBox categorie

        listCategorieProd.add("Tout");
        listCategorieProd.addAll(getCategories());
        cbCategorieProd.setItems(listCategorieProd);
        cbCategorieProd.getSelectionModel().selectFirst();
    }

    private ObservableList<String> getCategories() {
        ObservableList obs = FXCollections.observableArrayList();
        ArrayList res = ModProduit.getCategProduit();

        ListIterator<Hashtable> li = res.listIterator();
        while(li.hasNext()) {
            Hashtable<String, String> records = li.next();
            String categorie = records.get("categorie");
            obs.add(categorie);
        }

        return obs;
    }

    private void updateTableProd() {
        String[] propertyValues = {"produit", "categorie", "volume"};
        System.out.println("udpate table produit : " + this.listProduitFrs.toString());

        super.initTable(tabProdFrs, this.listProduitFrs, propertyValues);
    }

    private void delFrsProd() {
        // supprime un produit selectionner dans la listeView

        lvProdFrs.getSelectionModel().getSelectedItem();
    }

    private Hashtable getProduitInfo(String produit) {
        // retourne les information relative a un produit

        Hashtable res = new Hashtable();

        ArrayList infos = ModFournisseur.getProduitInfo(produit);
        ListIterator<Hashtable> liInfos = infos.listIterator();
        while (liInfos.hasNext())
            res = liInfos.next();

        return  res;
    }

    @FXML public void delFrsProds() {

    }

    public Button getBtnPrevious() {
        return this.btnPrevious;
    }

    public Button getBtnValidateAll() {
        return btnValidateAdd;
    }

    // requete en attente d'execution
    public void getQuery() {
        /*
        requette pour enregistrer les produits du fournisseur
        --
        >> est executer apres l'enregistrement des informations du frs

        get first last frsId because : we save first frs informations, after get her id
        then save this in fournirProduit table
        * */

        int lastIdFrs = (int) Tools.getLastRecord("select idUsine from usine").get("idUsine");

        String sql = "";
        ListIterator<String> li = listProduitFrs.listIterator();
        while(li.hasNext()) {
            String produit = li.next();

            // recuperation id
            sql = "select idProd from produit where designProd = '"+produit+"'";
            int idProd = (int) Tools.getLastRecord(sql).get("idProd");

            // insertion
            sql = "insert into fournirProduit values("+lastIdFrs+", "+idProd+")";
            Tools.saveRecords(sql);
        }

    }

}
