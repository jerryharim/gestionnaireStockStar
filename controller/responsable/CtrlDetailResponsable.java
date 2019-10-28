package controller.responsable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modele.ModRespDetail;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class CtrlDetailResponsable implements Initializable {
    private int selectedResp = 2;

    @FXML private Label lblNomPrenom, lblFonction, lblCIN,
            lblAniv, lblAddresse, lblVille, lblTel, lblEmail,
            lblNationalite;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet res = ModRespDetail.getAllInformation(selectedResp);
        System.out.println(res);

        if(res != null) {
            try {
                if (res.first()) {
                    String sexe = res.getString("sexeResp").equals("masculin") ? "Mr" : "Mme";
                    String nom = res.getString("nomResp");
                    String prenom = res.getString("prenomResp");
                    String CIN = res.getString("CINResp");
                    Date aniv = res.getDate("anivResp");
                    String ville = res.getString("villeResp");
                    String rue = res.getString("rueResp");
                    String addresse = res.getString("addrResp");
                    String tel = res.getString("telResp");
                    String email = res.getString("emailResp");
                    String nationalite = res.getString("nationaliteResp");

                    lblNomPrenom.setText(sexe + " " + nom.toUpperCase() + " " +prenom);
                    lblFonction.setText("inconnu");
                    lblCIN.setText(CIN);
                    lblAniv.setText(aniv.toString());
                    lblAddresse.setText(addresse + " " + rue);
                    lblVille.setText(ville);
                    lblTel.setText(tel);
                    lblEmail.setText(email);
                    lblNationalite.setText(nationalite);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
