package principale;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;


public class MosaiquePreview extends AnchorPane {
    private double width, height;
    private String nomProd, categorieProd;
    private int volumeProd;
    private ImageView iconeChecked;

    public MosaiquePreview(String nomProd, String categorieProd, int volumeProd) {

        width = 146;
        height = 184;
        this.setPrefSize(width, height);
        this.nomProd = nomProd;
        this.categorieProd = categorieProd;
        this.volumeProd = volumeProd;

        create();

        // event
        this.setOnMouseClicked(event -> {
            // select or deselect box

            if(iconeChecked.isVisible()) {
                iconeChecked.setVisible(false);
            } else {
                iconeChecked.setVisible(true);
            }
        });
    }

    private void create() {
        // css
        this.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: lightgray;" +
                        "-fx-border-width: 1;"
        );

        // icone checked
        iconeChecked = new ImageView(
                new Image(getClass().getResourceAsStream("../image/icone/normal/greenValidate.png"))
        );
        iconeChecked.setFitWidth(16);
        iconeChecked.setFitHeight(16);
        iconeChecked.setPreserveRatio(true);
        iconeChecked.setLayoutX(125);
        iconeChecked.setLayoutY(10);
        iconeChecked.setVisible(false);
        this.getChildren().add(iconeChecked);

        VBox root = new VBox();

        // logo
        AnchorPane paneLogo = new AnchorPane();
        paneLogo.setPrefHeight(120);

        // desc prod
        VBox descProd = new VBox(4);
        descProd.setPadding(new Insets(10, 5, 10, 15));
        descProd.setPrefWidth(100);
        descProd.setStyle("-fx-background-color: #161616;");

        Label labProd = new Label(this.nomProd + "(" + this.volumeProd +")");
        Label labCateg = new Label(this.categorieProd);
        labProd.setTextFill(Paint.valueOf("#ffffff"));
        labCateg.setTextFill(Paint.valueOf("#ffffff"));

        descProd.getChildren().addAll(labProd, labCateg);
        root.getChildren().addAll(paneLogo, descProd);

        this.getChildren().add(root);
    }

    public Boolean isSelected() {
        return iconeChecked.isVisible();
    }

    public BooleanProperty selectedProperty() {
        return iconeChecked.visibleProperty();
    }

    public void setSelected(Boolean val) {
        iconeChecked.setVisible(val);
    }

    public String getNomProd() {
        return nomProd;
    }

    public String getCategorie() {
        return categorieProd;
    }

    @Override
    public String toString() {
        return this.nomProd;
    }

}
