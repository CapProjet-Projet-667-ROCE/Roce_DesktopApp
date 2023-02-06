package org.openjfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.concurrent.Task;



public class ServerHandler {

    static ServerHandler instance;
    static ServerSocket serverSocket;
    static Task<Void> task;
    InetAddress desktopIP;
    int port1;
    int port2;

    //Constructeur de classe
    private ServerHandler(InetAddress addr, int p1, int p2){

        desktopIP = addr;
        port1 = p1;
        port2 = p2;
        try {
            //Creation d'un nouveau server
            serverSocket = new ServerSocket(port1, 1, desktopIP);
            IOHandler.writeLog("INFO", "Création de  "+serverSocket.toString());
            //Creation d'une nouvelle tâche
            task = new Task<Void>(){
                @Override
                public Void call(){
                    try {
                        boolean connected = false;
                        String toggle = "0";
                        while(!serverSocket.isClosed()){
                            if (!connected){
                                updateMessage("WAIT");
                            }
                            Socket socket = serverSocket.accept();
                            IOHandler.writeLog("INFO", "Connection au device : "+ socket.getInetAddress().getHostAddress());
                            connected = true;
                            updateMessage("FULL");
    
                            //Creation du lecteur de socket
                            InputStream input = socket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                            String signal;
                            try{
                                signal = reader.readLine();
                                while(signal!=null){
                                    updateMessage(toggle+";"+signal);
                                    toggle = toggle == "1" ? "0" : "1";
                                    signal = reader.readLine();
                                }
                                //Reception du signal NULL 
                                reader.close();
                                input.close();
                                updateMessage("CLOSE");
                                IOHandler.writeLog("INFO", "Déconnection du device : "+ socket.getInetAddress().getHostAddress());
                            } catch (IOException ioe){
                                ioe.printStackTrace();
                                IOHandler.writeLog("ERREUR", ioe.getMessage());
                            }

                        }
    
                    } catch (IOException ioe) {
                        updateMessage("CLOSE");
                    }
                    return null;
                }
            };
        } catch (IOException ioe) {
            IOHandler.writeLog("ERREUR", "Erreur lors de la création du socket");
        }
    }

    static public ServerHandler getInstance(InetAddress addr, int p1, int p2){
        instance = new ServerHandler(addr, p1, p2);
        return instance;
    }

    //Ferme le serverSocket si l'instance existe
    static public void close(){
        if (instance != null){
            try {
                serverSocket.close();
            } catch (IOException ioe) {
                IOHandler.writeLog("ERREUR", "Erreur lors de la fermeture du socket");
            }
        }
    }
}
