package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import principale.Main;

import java.io.IOException;
import java.net.URL;


public abstract class GUI {
    /*
    * classe mere pour les IHM
    *
    * */

    public void initTable(TableView table, ObservableList items, String[] propertyValues) {
        // initialise un TableView

        if(table.getColumns().size() != 0) {
            if( table.getColumns().size() == propertyValues.length ) {

                table.setItems(items);
                for (int i = 0; i < table.getColumns().size(); i++) {
                    TableColumn col = (TableColumn) table.getColumns().get(i);
                    String propValue = propertyValues[i];
                    col.setCellValueFactory(new PropertyValueFactory<>(propValue));
                }
            } else {
                System.out.println("Verify your propertyValues list, they can more or less than column number");
            }
        } else {
            System.out.println("No column found in " + table.toString());
        }
    }

    public void changeChildren(Pane parent, String fxmlChildName) {
        // supprime ses enfants et le remplace par une autre vue (fxml)

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlChildName+".fxml"));
            Pane pane = loader.load();
            parent.getChildren().setAll(pane);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void changeChildren2(Pane container, URL fxmlChildName) {
        // supprime ses enfants et le remplace par une autre vue (fxml)

        try {
            FXMLLoader loader = new FXMLLoader(fxmlChildName);
            Pane pane = loader.load();
            container.getChildren().setAll(pane);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void changeChildren(Pane container, Pane children) {
        // supprime ses enfants et le remplace par une autre pane

        container.getChildren().setAll(children);
    }
}
