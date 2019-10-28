package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import modele.ModProduit;
import controller.modeleTable.Commande;
import modele.Tools;
import principale.Main;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class CtrlCommanderProduit extends GUI {
    @FXML private ComboBox cbProduit, cbFournisseur;
    @FXML private Spinner<Integer> spQuantite;
    @FXML private TableView<Commande> table;
    @FXML private TableColumn<Commande, String> colProduit;
    @FXML private DatePicker dpDateVoulu;
    @FXML private Pane container;

    private ObservableList<String> listProduit = FXCollections.observableArrayList();
    private ObservableList<String> listFournisseur = FXCollections.observableArrayList();
    private ObservableList<Commande> listCommande = FXCollections.observableArrayList();
    private LocalDate dateDemande, dateVoulu;

    @FXML public void initialize() {

        // date livraison voulu
        // default date;
        dpDateVoulu.setValue(LocalDate.now());

        // comboBox: populate produit ======
        ArrayList prods = ModProduit.getListProduit();
        ListIterator li = prods.listIterator();
        while(li.hasNext()) {
            Hashtable records = (Hashtable) li.next();
            String produit = (String) records.get("designation");
            listProduit.add(produit);
        }

        cbProduit.setItems(listProduit);

        // fournisseur
        // repopulate fournisseur from comboBox when new val existe
        cbProduit.valueProperty().addListener((obs, oVal, nVal) -> {
            listFournisseur.clear();
            String selectedProd = (String) nVal;
            ArrayList fournisseurs = ModProduit.getFournisseurFor(selectedProd);
            ListIterator liFournisseurs = fournisseurs.listIterator();
            while(liFournisseurs.hasNext()) {
                Hashtable fournisseur = (Hashtable) liFournisseurs.next();
                String nom = (String) fournisseur.get("nom");
                listFournisseur.add(nom);
            }
        });

        cbFournisseur.setItems(listFournisseur);

        // spinner : quantite cageot
        SpinnerValueFactory<Integer> spQteValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 5, 5);
        spQuantite.setValueFactory(spQteValue);
        spQuantite.setEditable(false);

        // table : list des commande a faire
        String[] propertyValues = {"produit", "fournisseur", "quantite"};
        super.initTable(table, listCommande, propertyValues);
    }

    @FXML public void updateCmd() {
        // update current commande in table view
        Commande selectedCmd = table.getSelectionModel().getSelectedItem();
        cbProduit.getSelectionModel().select(selectedCmd.getProduit());
        cbFournisseur.getSelectionModel().select(selectedCmd.getFournisseur());
        spQuantite.getEditor().setText(String.valueOf(selectedCmd.getQuantite()));

        // del from list : change this
        listCommande.remove(selectedCmd);
    }

    @FXML public void clearAddCmd() {
        clearField();
    }

    @FXML public void cancelCommande() {
        Pane parent = (Pane) container.getParent();
        super.changeChildren(parent, "../view/dashboard");
    }

    @FXML public void ajouterCommande() {
        // ajout une commande dans la liste des commande

        String produit = (String) cbProduit.getValue();
        String fournisseur = (String) cbFournisseur.getValue();
        int quantite = spQuantite.getValue();

        if(okFieldAddCommande()) {
            listCommande.add(new Commande(produit, fournisseur, quantite));
            clearField();
        } else {
            WindowTools.alertEmpty("Tous les champs sont obligatoire");
        }
    }

    private boolean okFieldAddCommande() {
        String produit = (String) cbProduit.getValue();
        String fournisseur = (String) cbFournisseur.getValue();
        int quantite = spQuantite.getValue();

        if(!cbProduit.getSelectionModel().isEmpty() &&
                !cbFournisseur.getSelectionModel().isEmpty() && quantite != 0)
            return true;
        return false;
    }

    private void clearField() {
        // clean add prod field
        cbProduit.getSelectionModel().clearSelection();
        cbFournisseur.getSelectionModel().clearSelection();
    }

    @FXML public void envoyerCommande() {
        // envoyer la commande au fournisseur

        if( listCommande.isEmpty() ) {
            WindowTools.alertEmpty("La liste des commande est vide, \nveuillez en choisir au moins un produit");
        } else {
            saveDemandeProduit();

            // redirection
            String msg = "Votre commande a ete bien envoye. \nAfficher l'historique?";
            boolean showHistory = WindowTools.alertConfirmation(msg);

            if (showHistory) {
                super.changeChildren(container, "/view/historiqueCommande");
            } else {
                super.changeChildren(container, "/view/home");
            }
        }
    }

    private void validerLastCommande() {
        /*
        sauvegarde les commande valider par le fournisseur
        par defaut : on valide tout
        */


        String sql = "select idDmdProd, dateLivraisonVoulu " +
                "from demandeProduit";
        Hashtable lastCommande = Tools.getLastRecord(sql);
        int idDmdProd = (int) lastCommande.get("idDmdProd");

        LocalDate dateValidation = LocalDate.now();
        String datePrevu = ((Date) lastCommande.get("dateLivraisonVoulu")).toString();

        // insertion
        sql = "insert into validationCommande(" +
                        "dateValidation, datePrevu, idDmdProd) " +
                        "values('"+dateValidation+"', " +
                        "'"+datePrevu+"', " +
                        idDmdProd +
                      ")";
        Tools.saveRecords(sql);
    }

    private void saveDemandeProduit() {
        /*
         * sauvegarde et envoye la demande de produit
         * -----
         *
         * on recupere chaque fournisseur dans la liste des commandes
         * et pour chacun d'entre eux
         * on l'insert dans la liste des demande de produit
         * puis on fait l'insertion des produits la liste des
         * detail de produit
         *
         * */

        String sql = "";
        System.out.print("envoye demande...");

        dateDemande = LocalDate.now();
        dateVoulu = dpDateVoulu.getValue();
        int idResp = Main.getAccount().getIdResp();

        // recuperation list sans doublons -----

        ArrayList<String> listFrsInCommande =  new ArrayList<>();
        ArrayList<String> listProdInCommande = new ArrayList<>();

        ListIterator<Commande> liCommande = listCommande.listIterator();
        while( liCommande.hasNext() ) {
            Commande commande=liCommande.next();
            String fournisseur=commande.getFournisseur();
            String produit = commande.getProduit();

            if( !listFrsInCommande.contains(fournisseur) )
                listFrsInCommande.add(fournisseur);
            if( !listProdInCommande.contains(produit))
                listProdInCommande.add(produit);
        }

        // creation couple frs/id et prod/id pour eviter la repetition de requete

        Hashtable<String, Integer> fournisseurIds = new Hashtable<>();
        ListIterator<String> liFrsInCmd = listFrsInCommande.listIterator();
        while( liFrsInCmd.hasNext() ) {
            String frs = liFrsInCmd.next();
            sql = "select idUsine from usine where nomUsine='"+frs+"'";
            int idFrs = (int) Tools.getLastRecord(sql).get("idUsine");
            fournisseurIds.put(frs, idFrs);
        }

        Hashtable<String, Integer> produitIds  = new Hashtable<>();
        ListIterator<String> liProdInCmd = listProdInCommande.listIterator();
        while( liProdInCmd.hasNext() ) {
            String prod = liProdInCmd.next();
            sql = "select idProd from produit where designProd='"+prod+"'";
            int idProd = (int) Tools.getLastRecord(sql).get("idProd");
            produitIds.put(prod, idProd);
        }

        // insertion par fournisseur ------

        sql = "select idDmdProd from demandeProduit order by idDmdProd";
        int lastDmdId = (int) Tools.getLastRecord(sql).get("idDmdProd");

        liFrsInCmd = listFrsInCommande.listIterator();
        while( liFrsInCmd.hasNext() ) {
            // insertion dans demandeProduit

            String curFournisseur = (String) liFrsInCmd.next();

            int idFrs = fournisseurIds.get(curFournisseur);
            ModProduit.saveDemandeProduit(idFrs, dateDemande.toString(), dateVoulu.toString(), idResp);

            // insertion des produits dans detailDemandProduit pour ce FRS

            lastDmdId++;
            ListIterator<Commande> liCmd = listCommande.listIterator();
            while( liCmd.hasNext() ) {
                Commande com = liCmd.next();
                String fournisseur = com.getFournisseur();
                if( fournisseur.equals(curFournisseur ) ) {
                    String produit = com.getProduit();
                    int idProd = produitIds.get(produit);
                    int quantite = com.getQuantite();

                    ModProduit.saveDetailDmdProd(lastDmdId, idProd, quantite);
                }
            }

            // Valider automatiquement les produits
            validerLastCommande();
        }
    }

    private Hashtable grouper(ObservableList list) {
        /* regroupe les produits suivant le fournisseur */

        Hashtable hashtable = new Hashtable();

        ListIterator<Commande> li = list.listIterator();
        while(li.hasNext()) {
            Commande com = li.next();
        }

        return hashtable;
    }

    @FXML
    public void delCurCmd(ActionEvent event) {
        // delete current selected commande
        Commande curCmd = table.getSelectionModel().getSelectedItem();
        listCommande.remove(curCmd);
    }
}

