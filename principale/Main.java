package principale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.Account;
import modele.Database;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    private Stage primaryStage;
    private static Database db = new Database("starGestionStock", "root", "toor");
    private static Account account;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("STAR Gestionaire de Stock");
        primaryStage.show();
    }

    private void loginScreen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.getScene().setRoot(root);
    }

    private void splashScreen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/splashScreen.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    public static void loadAccount(int idAccount) {
        account = new Account(idAccount);
    }

    public static Account getAccount() {
        return account;
    }

    public static Connection getConnection() {
        return db.getConnexion();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
