package org.openjfx;

import java.io.*;
import java.util.Properties;
import java.text.SimpleDateFormat;  
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class IOHandler {

    //Récupération des chemins d'accès à conf et logs
    static String conf = IOHandler.class.getResource("conf.properties").getFile();
    static String logs = IOHandler.class.getResource("logs.txt").getFile();

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
            writeLog("ERROR", conf+" est introuvable");
        }
        //Erreur lors du chargement du fichier
        catch (IOException ioe) {
            writeLog("ERROR", "Erreur lors du chargement de "+conf);
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
            writeLog("ERROR", "Erreur lors de l'écriture de "+conf);
        }
    }

    //Génaration d'une fenêtre d'alerte
    public static void throwAlert(AlertType type, String Hmsg, String msg){
        Alert a = new Alert(type);
        a.setHeaderText(Hmsg);
        a.setContentText(msg);
        a.showAndWait();
    }

    public static String readLogs(){
        String datastr = "";
        try {
            FileInputStream in = new FileInputStream(logs);
            DataInputStream datain = new DataInputStream(in);
            BufferedReader br = new BufferedReader(new InputStreamReader(datain));
            char c;
            while ((c = (char) br.read()) != (char) -1){
                String temp = Character.toString(c);
                datastr = datastr.concat(temp);
            }
            in.close();
        } 
        catch (IOException ioe){
            System.out.println("error reading logs file");
        }
        return datastr;
    }

    public static void writeLog (String type, String msg){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String strDate = formatter.format(date);

        String data = strDate + " [" + type + "] " + msg;

        try {
            FileOutputStream out = new FileOutputStream(logs, true);
            BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(out));
            bout.write(data);
            bout.newLine();
            bout.close();
        }
        catch (IOException ioe) {
        }
    }
}
