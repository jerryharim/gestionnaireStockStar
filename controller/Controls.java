package controller;

import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controls {
    /* regroupe les fonctions de controls les plus courant

    */

    public static boolean empty(TextField tf) {
        /* getEmptyFields if a textfield is empty */

        return tf.getText().trim().isEmpty();
    }

    public static ArrayList getEmptyFields(TextField[] tfs) {
        // return list of emptyField

        ArrayList<TextField> emptyFields = new ArrayList();
        for( TextField tf : tfs ) {
               if( empty(tf) )
                   emptyFields.add(tf);
        }

        return emptyFields;
    }

    public static boolean findRegex(String motif, String texte) {
        // recher un motif dans un texte

        Pattern pat = Pattern.compile(motif);
        Matcher mat = pat.matcher(texte);

        return mat.matches();
    }

}
