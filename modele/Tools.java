package modele;

import principale.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public abstract class Tools {
    private static Connection conn = Main.getConnection();
    private static PreparedStatement prepFind;

    public static ArrayList getResult(String query) {
        /*
        Execute la requete et retourne un arrayList qui contient la liste des
        enregistrement, peut etre vide
        Imitation ResultSet pour eviter les TryCatch

        arryList
         :
            hastable :
                cle: nomCol
                valeur : valeurCol

        */

        // System.out.println("\nResult for Query : " + query);

        ArrayList list = new ArrayList();
        try {
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();

            while (result.next()) {
                Hashtable<String, Object> records = new Hashtable<>();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnLabel(i);
                    Object record = result.getObject(i);

                    if (record instanceof String) {
                        record = result.getString(i);
                    } else if (record instanceof Integer) {
                        record = result.getInt(i);
                    }

                    records.put(colName, record);
                }

                list.add(records);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            System.out.println("Result : "+list);
            return list;
        }
    }

    public static Hashtable getLastRecord(String query) {
        // retourne le dernier resultat d'un requete
        // peut etre vide
        // cle : nomColonne ; valeur : valeurColonne

        Hashtable record = new Hashtable();

        ArrayList records = Tools.getResult(query);
        System.out.println("record found : " + records);
        ListIterator liRecords = records.listIterator();
        while(liRecords.hasNext()) {
            record = (Hashtable) liRecords.next();
        }

        return record;
    }

    public static ArrayList getRecordsIn(String tableName, String colName) {
        // retourne tous element d'un colomne d'une table

        String query = "Select "+colName+" from "+tableName;
        return getFirstResult(query);
    }

    public static ArrayList getRecordsIn(String table, String colName, String searchCol, String keyword) {
        // recupere les enregistrement d'un champ d'une table en utilisant le keyword

        String query = "Select "+colName+
                " from "+table+
                " where " + searchCol + " = '"+keyword+"'";
        return getFirstResult(query);
    }

    private static ArrayList getFirstResult(String query) {
        // retourne les resultat d'une requete dans un arrayList
        // ne recupere que le premier champ trouver

        ArrayList data = new ArrayList();

        try {
            Statement state = conn.createStatement();
            ResultSet res = state.executeQuery(query);
            while(res.next())
                data.add(res.getObject(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    public static ResultSet find(String tableName, String keyword) {
        // recherche

        ResultSet res = null;

        return res;
    }

    public static void saveRecords(String query) {
        // sauvegarde les elements de listValue dans la tableName avec les colomnes de listCol

        System.out.println("\nSauvegarde en cours ... ");
        System.out.println("Execution du requete : " + query);

        try {
            Statement state = conn.createStatement();
            if( !state.execute(query) )
                System.out.println("Sauvegarde reussit");
            else
                System.out.println("Sauvegarde echouer");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
