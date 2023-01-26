package org.openjfx;

import java.io.IOException;
import java.util.Properties;

import javafx.scene.robot.Robot;
import javafx.scene.input.KeyCode;

public class CommandHandler {

    static Robot robot = new Robot();
    static Runtime runtime = Runtime.getRuntime();
    static Properties prop = IOHandler.readConfigFile();


    //Traitement d'un signal et appel de la méthode associé
    public static void signalManager(String signal){
        //splited[0] = id | splited[1] = arg
        String[] splited = signal.split(":");

        //TODO : edit map<String,Function>
        //TODO : getFonction(splited[0])
        //TODO : callFontion(splited[1])
    }


    public static void launchCmd (String cmd){
        try {
            runtime.exec(cmd);
        } catch (IOException ioe) {
            //TODO : envoie d'un log : Impossible de lancer la commande [cmd]
        }
    }

    public static void preview (){
        robot.keyPress(KeyCode.LEFT);
        robot.keyRelease(KeyCode.LEFT);
    }

    public static void next(){
        robot.keyPress(KeyCode.RIGHT);
        robot.keyRelease(KeyCode.RIGHT);
    }

    public static void play(){
        robot.keyPress(KeyCode.SPACE);
        robot.keyRelease(KeyCode.SPACE);
    }

    public static void lock(){
        robot.keyPress(KeyCode.WINDOWS);
        robot.keyPress(KeyCode.WINDOWS);

        robot.keyRelease(KeyCode.L);
        robot.keyRelease(KeyCode.L);
    }

    public static void winG(){
        robot.keyPress(KeyCode.WINDOWS);
        robot.keyPress(KeyCode.G);

        robot.keyRelease(KeyCode.WINDOWS);
        robot.keyRelease(KeyCode.G);
    }

    public static void mute(){
        robot.keyPress(KeyCode.CONTROL);
        robot.keyPress(KeyCode.SHIFT);
        robot.keyPress(KeyCode.M);

        robot.keyRelease(KeyCode.CONTROL);
        robot.keyRelease(KeyCode.SHIFT);
        robot.keyRelease(KeyCode.M);
    }

    public static void volPlus(){
        launchCmd("nircmd.exe changesysvolume 2000");
    }

    public static void volMinus(){
        launchCmd("nircmd.exe changesysvolume -2000");
    }

}
