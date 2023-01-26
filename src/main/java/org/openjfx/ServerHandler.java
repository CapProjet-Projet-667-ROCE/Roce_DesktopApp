package org.openjfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class ServerHandler {

    static ServerHandler instance;
    static ServerSocket serverSocket;
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
            serverSocket = new ServerSocket(port1, 10, desktopIP);
        } catch (IOException ioe) {
            //TODO : envoie d'un log : Erreur lors la création de l'objet ServerSocket
        }
    }

    static public ServerHandler getInstance(InetAddress addr, int p1, int p2){
        instance = new ServerHandler(addr, p1, p2);
        return instance;
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    while(!serverSocket.isClosed()){
                        //System.out.println("En attente de connexion ...");
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
                                        //System.out.println(signal);

                                        CommandHandler.signalManager(signal);
                                        signal = reader.readLine();
                                    }
                                    //Reception du signal NULL 
                                    reader.close();
                                    input.close();
                                    //TODO : Update labal status -> Non connecté
                                    //System.out.println("Client déconnecté");
                                } catch (IOException ioe){
                                    //TODO : envoie d'un log : ioe.getMessage();
                                }
                            }
                        });

                    //Lancement d'un Thread secondaire : server
                    server.start();
                    }

                } catch (IOException e) {
                    //LOG fermeture du serverSocket.
                }
            }
           //Lancement du Thread principal 
        }).start();
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
