package org.openjfx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.scene.robot.Robot;
import javafx.scene.input.KeyCode;

public class CommandHandler {

    static Properties prop = IOHandler.readConfigFile();
    static Robot robot = new Robot();
    static Runtime runtime = Runtime.getRuntime();

    interface invokeMethod {
         void invoke(String arg);
    }

    static Map<String, invokeMethod> funcMap = setFuncMap();

    static Map<String, invokeMethod> setFuncMap() {
        Map<String, invokeMethod> map = new HashMap<String, invokeMethod>();

        map.put("Preview", new invokeMethod() {
            public void invoke(String arg){
                robot.keyPress(KeyCode.CONTROL);
                robot.keyPress(KeyCode.LEFT);

                robot.keyRelease(KeyCode.CONTROL);
                robot.keyRelease(KeyCode.LEFT);
            }
        });

        map.put("Next", new invokeMethod() {
            public void invoke(String arg){
                robot.keyPress(KeyCode.CONTROL);
                robot.keyPress(KeyCode.RIGHT);

                robot.keyRelease(KeyCode.CONTROL);
                robot.keyRelease(KeyCode.RIGHT);
            }
        });

        map.put("Play/Pause", new invokeMethod() {
            public void invoke(String arg){
                robot.keyPress(KeyCode.SPACE);
                robot.keyRelease(KeyCode.SPACE);
            }
        });

        map.put("Lock", new invokeMethod() {
            public void invoke(String arg){
                //robot.keyPress(KeyCode.WINDOWS);
                //robot.keyPress(KeyCode.L);
        
                //robot.keyRelease(KeyCode.WINDOWS);
                //robot.keyRelease(KeyCode.L);
            }
        });

        map.put("Win+G", new invokeMethod() {
            public void invoke(String arg){
                //robot.keyPress(KeyCode.WINDOWS);
                //robot.keyPress(KeyCode.G);
        
                //robot.keyRelease(KeyCode.WINDOWS);
                //robot.keyRelease(KeyCode.G);
            }
        });

        map.put("Mute", new invokeMethod() {
            public void invoke(String arg){
                robot.keyPress(KeyCode.CONTROL);
                robot.keyPress(KeyCode.SHIFT);
                robot.keyPress(KeyCode.M);
        
                robot.keyRelease(KeyCode.CONTROL);
                robot.keyRelease(KeyCode.SHIFT);
                robot.keyRelease(KeyCode.M);
            }
        });

        map.put("Launch", new invokeMethod() {
            public void invoke(String arg){
                try {
                    String[] tab = {"calc.exe"};
                    runtime.exec(tab);
                } catch (IOException ioe) {
                    IOHandler.writeLog("ERROR", "Impossible de lancer calc.exe");
                }
            }
        });

        map.put("Vol+", new invokeMethod() {
            public void invoke(String arg){
                try {
                    String[] tab = {"nircmd.exe", "changesysvolume", "660"};
                    runtime.exec(tab);
                } catch (IOException ioe) {
                    IOHandler.writeLog("ERROR", "Impossible de lancer nircmd.exe setsysvolume");
                }
            }
        });

        map.put("Vol-", new invokeMethod() {
            public void invoke(String arg){
                try {
                    String[] tab = {"nircmd.exe", "changesysvolume", "-660"};
                    runtime.exec(tab);
                } catch (IOException ioe) {
                    IOHandler.writeLog("ERROR", "Impossible de lancer nircmd.exe setsysvolume");
                }
            }
        });

        map.put("SetSpeaker", new invokeMethod() {
            public void invoke(String arg){
                try {
                    String[] tab = {"nircmd.exe", "setsysvolume", String.valueOf(Integer.parseInt(arg)*665)};
                    runtime.exec(tab);
                } catch (IOException ioe) {
                    IOHandler.writeLog("ERROR", "Impossible de lancer nircmd.exe setsysvolume");
                }
            }
        });

        map.put("SetMic", new invokeMethod() {
            public void invoke(String arg){
            }
        });

        return map;
    }


    //Traitement d'un signal et appel de la méthode associé
    public static void signalManager(String signal){
        //signal = t1:
        //signal = t9:89
        //splited[0] = id | splited[1] = arg
        System.out.println(signal);
        String[] splited = signal.split(":");
        try {
            funcMap.get(prop.getProperty(splited[0])).invoke(splited[1]);
        } catch (Exception e) {
            funcMap.get(prop.getProperty(splited[0])).invoke(null);
        }
    }

}
