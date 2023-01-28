package org.openjfx;

import java.io.IOException;
import java.util.Properties;

import javafx.scene.robot.Robot;
import javafx.scene.input.KeyCode;

public class CommandHandler {

    static Properties prop = IOHandler.readConfigFile();
    Robot robot = new Robot();
    Runtime runtime = Runtime.getRuntime();

    //hashMap<String, Interface>
    
    private interface invokeMethod {
        void method();
    } 


    //Traitement d'un signal et appel de la méthode associé
    public static void signalManager(String signal){
        //signal = t1:
        //signal = t9:89
        //splited[0] = id | splited[1] = arg
        String[] splited = signal.split(":");

        String func = prop.getProperty(splited[0]);
        //TODO : edit map<String,Function>
        //TODO : getFonction(splited[0])
        //TODO : callFontion(splited[1])
    }


    public void launchCmd (String cmd){
        try {
            runtime.exec(cmd);
        } catch (IOException ioe) {
            //TODO : envoie d'un log : Impossible de lancer la commande [cmd]
        }
    }

    public void preview (){
        robot.keyPress(KeyCode.LEFT);
        robot.keyRelease(KeyCode.LEFT);
    }

    public void next(){
        robot.keyPress(KeyCode.RIGHT);
        robot.keyRelease(KeyCode.RIGHT);
    }

    public void play(){
        robot.keyPress(KeyCode.SPACE);
        robot.keyRelease(KeyCode.SPACE);
    }

    public void lock(){
        robot.keyPress(KeyCode.WINDOWS);
        robot.keyPress(KeyCode.WINDOWS);

        robot.keyRelease(KeyCode.L);
        robot.keyRelease(KeyCode.L);
    }

    public void winG(){
        robot.keyPress(KeyCode.WINDOWS);
        robot.keyPress(KeyCode.G);

        robot.keyRelease(KeyCode.WINDOWS);
        robot.keyRelease(KeyCode.G);
    }

    public void mute(){
        robot.keyPress(KeyCode.CONTROL);
        robot.keyPress(KeyCode.SHIFT);
        robot.keyPress(KeyCode.M);

        robot.keyRelease(KeyCode.CONTROL);
        robot.keyRelease(KeyCode.SHIFT);
        robot.keyRelease(KeyCode.M);
    }

    public void volPlus(){
        launchCmd("nircmd.exe changesysvolume 2000");
    }

    public void volMinus(){
        launchCmd("nircmd.exe changesysvolume -2000");
    }

    public void setVol(){

    }

    public void setMic(){

    }

}
