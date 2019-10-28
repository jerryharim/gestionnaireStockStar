package modele;

import principale.Main;

import java.sql.*;

public class ModLogin {
    private static Connection conn = Main.getConnection();

    public static int estMembre(String username, String password) {
        int id = -1;
        try {
            String query = "Select * from compte where username=? AND pass=?";
            PreparedStatement prepSta = conn.prepareStatement(query);
            prepSta.setString(1, username);
            prepSta.setString(2, password);
            prepSta.toString();
            ResultSet res = prepSta.executeQuery();
            if(res.next()) {
                // recuperation id
                id = res.getInt("idCmpt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return id;
        }
    }

}
