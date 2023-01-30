package org.openjfx;

import java.io.*;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class IOHandler {

    //Récupération des chemins d'accès à conf et logs
    static String conf = IOHandler.class.getResource("conf.properties").getFile();
    static String logs = IOHandler.class.getResource("conf.properties").getFile();

    public static Properties readConfigFile() {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(conf);
            prop = new Properties();
            prop.load(fis);
            fis.close();
        }
        //Fichier conf introuvable
        catch (FileNotFoundException fnfe) {
            //TODO : envoie d'un log : Le fichier de config : [conf] est introuvable
        }
        //Erreur lors du chargement du fichier
        catch (IOException ioe) {
            //TODO : envoie d'un log : Erreur lors du chargement du fichier : [conf]
        } 
        return prop;
    }

    public static void writeConfigFile(String key, String value){
        try{
            Properties prop = readConfigFile();
            FileOutputStream out = new FileOutputStream(conf);
            prop.setProperty(key, value);
            prop.store(out,null);
            out.close();
            throwAlert(AlertType.INFORMATION, "Le port a été modifié avec succès", "ATTENTION : Ce changement sera pris en compte que lors du prochain démarrage de l'application");
        }
        //Erreur lors du chargement du fichier.
        catch (IOException ioe){
            //TODO : envoie d'un log : Erreur lors du chargement du fichier : [conf]
        }
    }

    //Génaration d'une fenêtre d'alerte
    public static void throwAlert(AlertType type, String Hmsg, String msg){
        Alert a = new Alert(type);
        a.setHeaderText(Hmsg);
        a.setContentText(msg);
        a.showAndWait();
        //TODO : bonus : Customiser la fenêtre d'alerte (JavaFX + CSS)
    }

    //TODO : Implémenter readLogFile

    //TODO : Implémenter writeLog
        //FORMAT
        //2023-30-01.750 [TYPE: Error, Connexion, Info] 
}
