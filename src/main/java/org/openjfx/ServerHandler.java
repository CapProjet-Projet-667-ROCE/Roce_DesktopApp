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
            //Creation d'une nouvelle tâche
            task = new Task<Void>(){
                @Override
                public Void call(){
                    try {
                        while(!serverSocket.isClosed()){
                            System.out.println("En attente de connexion sur : "+serverSocket.toString());
                            updateMessage("WAIT");
                            Socket socket = serverSocket.accept();
                            //TODO : Update status label -> Connection partielle
                            //TODO : Connection sur le p2 du l'addresse récupérée dans socket.getInetAddress().getHostAddress()
                            //TODO : Envoie des labels de bouttons
                            //TODO : Update status label -> Connecté
                            //System.out.println("Connection du client : " + socket.getInetAddress().getHostAddress());
    
                            //Creation du lecteur de socket
                            InputStream input = socket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                            Thread server = new Thread(new Runnable() {
                                String signal;
                                public void run(){
                                    try{
                                        signal = reader.readLine();
                                        while(signal!=null){
                                            CommandHandler.signalManager(signal);
                                            signal = reader.readLine();
                                        }
                                        //Reception du signal NULL 
                                        reader.close();
                                        input.close();
                                    } catch (IOException ioe){
                                        //TODO : envoie d'un log : ioe.getMessage();
                                    }
                                }
                            });
    
                        //Lancement d'un Thread secondaire : server
                        server.start();
                        }
    
                    } catch (IOException e) {
                        updateMessage("CLOSE");
                    }
                    return null;
                }
            };
        } catch (IOException ioe) {
            //TODO : envoie d'un log : Erreur lors la création de l'objet ServerSocket
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
                //TODO : envoie d'un log : Erreur lors de la fermeture du socket
            }
        }
    }
}
