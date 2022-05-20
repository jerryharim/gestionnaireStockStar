package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import modele.Tools;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlSuivitCommande extends GUI {
    @FXML ComboBox cbEtat;
    @FXML Label labTotal;
    @FXML FlowPane fpListCmd;
    @FXML Pane root;

    @FXML public void initialize() {
        cbEtat.getItems().add("En attente");

        labTotal.setText("Total : " + getTotal());
        // center
        refreshListCommande();
    }

    private String getTotal() {
        /* retroune la total des commande */

        String sql = "select count(*) as total from validationCommande where livrer=0";
        Hashtable<String, Long> totCmdNonLivrer = Tools.getLastRecord(sql);
        long total = totCmdNonLivrer.get("total");

        return String.valueOf(total);
    }

    private void refreshListCommande() {
        /* creer les box qui contient les commande en attente de livraison */

        fpListCmd.getChildren().clear();

        String sql = "select " +
                        "vc.idDmdProd as numeroCommande, " +
                        "vc.datePrevu as dateLivraison, " +
                        "us.nomUsine as fournisseur " +
                "from validationCommande as vc " +
                "join demandeProduit as dp on vc.idDmdProd = dp.idDmdProd " +
                "join usine as us on dp.idUsine = us.idUsine " +
                "where vc.livrer=0";
        ArrayList listCommande = Tools.getResult(sql);

        ListIterator<Hashtable> li = listCommande.listIterator();
        while( li.hasNext() ) {
            Hashtable commande = li.next();
            int numeroCommande = (int) commande.get("numeroCommande");
            String dateLivraison = ((Date)commande.get("dateLivraison")).toString();
            String fournisseur = (String) commande.get("fournisseur");

            fpListCmd.getChildren().add(createBoxList(numeroCommande, dateLivraison, fournisseur));
        }
    }

    private Pane createBoxList(int numCommande, String datePrevu, String fournisseur) {
        /* create la boite des commande */

        AnchorPane box = new AnchorPane();
        String style = "-fx-background-color: #f9f9f9;" +
                "-fx-border-color: lightgray;" +
                "-fx-border-width: 1;";
        box.setStyle(style);
        box.setPrefSize(190, 131);

        Label labNumCmd = new Label(String.valueOf(numCommande));
        labNumCmd.setLayoutX(10);
        labNumCmd.setLayoutY(10);
        labNumCmd.setStyle(
                "-fx-border-color: green;" +
                        "-fx-border-width: 0 0 4 0;"
        );
        labNumCmd.setFont(Font.font(25));
        labNumCmd.setPrefWidth(45);

        Label labDateLivraison = new Label(datePrevu);
        labDateLivraison.setLayoutX(10);
        labDateLivraison.setLayoutY(50);
        labDateLivraison.setFont(Font.font(12));

        Label labFrs = new Label("Par "+fournisseur);
        labFrs.setLayoutX(10);
        labFrs.setLayoutY(67);
        labFrs.setFont(Font.font(12));

        Button btnValidateCmd = new Button("Livrer");
        btnValidateCmd.setLayoutX(130);
        btnValidateCmd.setLayoutY(100);
        btnValidateCmd.getStyleClass().addAll("btn-basic", "btn-ok");
        btnValidateCmd.setOnAction(event -> {
            commandeLivrer(numCommande);
            refreshListCommande();
            updateStock(numCommande);
            WindowTools.alertInformation("Le commande numero " + numCommande + " a ete bien enregistrer en stock");
        });

        box.getChildren().addAll(
                labNumCmd, labDateLivraison, labFrs, btnValidateCmd
        );

        return box;
    }

    private void updateStock(int numCommande) {
        /*
        insert dans le stock le commande ayant le numero numCommande
        --
        - pour chaque produit appartenant a ce numCommande
        - recuper la quantite du produits demander et mettre a jour
        le nombre de produit dans le stock a l'additionant

        * */

        // recuperation ---
        String sql = "select " +
                        "ddp.idProd, ddp.qteDemander, " +
                        "prod.qteEnStock " +
                "from detailDemandeProduit as ddp " +
                "join produit as prod on ddp.idProd = prod.idProd " +
                "where ddp.idDmdProd = " + numCommande;
        ArrayList commandes = Tools.getResult(sql);

        ListIterator<Hashtable> li = commandes.listIterator();
        while( li.hasNext() ) {
            Hashtable<String,Integer> commande = li.next();
            int idProd = commande.get("idProd");
            int qteDemander = commande.get("qteDemander");
            int qteEnStock = commande.get("qteEnStock");

            int newQte = qteEnStock + qteDemander;

            // mis a jour ---
            sql = "update produit set qteEnStock="+newQte+" where idProd="+idProd;
            Tools.saveRecords(sql);
        }
    }

    private void commandeLivrer(int numCommande) {
        /* valider une commande apres livraison */

        String sql = "update validationCommande set livrer=1 where idDmdProd='"+numCommande+"'";
        Tools.saveRecords(sql);
    }

    @FXML public void afficherStock() {
        /* affiche le stock */

        Pane parent = (Pane) root.getParent();
        super.changeChildren(parent,"/view/stock");
    }
}
