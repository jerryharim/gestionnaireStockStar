package modele;

import com.mysql.jdbc.CommunicationsException;
import javafx.application.Platform;

import javax.naming.CommunicationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection conn;

    public Database(String dbName, String username, String pass) {
        try {
            System.out.print("Searching mysql driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Mysql driver found");

            System.out.print("connecting to [" + dbName + "]... ");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, pass);
            System.out.println("Connexion established");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver mysql introuvable");
            e.printStackTrace();
        } catch (CommunicationsException e) {
            System.out.println("Mysql commnunication error");
            Platform.exit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnexion() { return conn; }
}
