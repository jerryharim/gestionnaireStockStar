package modele;

import java.sql.*;

import principale.Main;

public class ModRespDetail {
    // modele detail sur un responsable

    private static Connection conn = Main.getConnection();

    public static ResultSet getAllInformation(int selectedResp) {
        ResultSet res = null;
        try {
            String query = "Select * from responsable where matriculeResp=?";
            PreparedStatement prepState = conn.prepareStatement(query);
            prepState.setInt(1, selectedResp);
            res = prepState.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
