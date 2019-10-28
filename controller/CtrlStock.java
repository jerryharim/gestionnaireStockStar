package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modele.ModStock;
import controller.modeleTable.ProduitStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlStock extends GUI {
    @FXML private TableView tabStock;
    @FXML private TableColumn<ProduitStock, String> colProduit, colCategorie, colRangement, colDepot;
    @FXML private TableColumn<ProduitStock, Integer> colQuantite;
    @FXML private Label lblTotTypProd, lblTotEnStock, lblTotDepA, lblTotDepB, lblTotDepC;

    private ModStock modStock = new ModStock();

    private ObservableList<ProduitStock> listStock = FXCollections.observableArrayList();

    @FXML public void initialize() {
        refreshTableStock();
        refreshStatistic();
    }

    private void refreshStatistic() {
        ResultSet resTotal = modStock.getTotal();
        if(resTotal != null) {
            try {
                if (resTotal.isFirst()) {
                    lblTotTypProd.setText(resTotal.getString("totalProd"));
                    lblTotEnStock.setText(resTotal.getString("quantite"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshTableStock() {
        // get data in database

        System.out.println("refreshing table");
        listStock.clear();
        ArrayList res = modStock.getListStock();
        ListIterator li = res.listIterator();

        while(li.hasNext()) {
            Hashtable row = (Hashtable) li.next();
            String produit = (String) row.get("produit");
            String categorie = (String) row.get("categorie");
            String ranger = (String) row.get("ranger");
            String depot = (String) row.get("depot");
            int quantite = (int) row.get("quantite");
            listStock.add(new ProduitStock(produit, categorie, ranger, depot, quantite));
        }

        // implement data to tableView content
        String[] propertyValues = {"produit", "categorie", "ranger", "depot", "quantite"};
        super.initTable(tabStock, listStock, propertyValues);
    }
}
