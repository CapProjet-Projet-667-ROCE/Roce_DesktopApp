package org.openjfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.*;
import java.util.*;


public class mainController implements Initializable{

    //Définition FXML des composants JavaFX
    @FXML
    private Label connStatus;

    @FXML
    private ImageView imgStatus;

    @FXML
    private TextArea textArea;

    @FXML
    private Label labP1;

    @FXML
    private Label labP2;

    @FXML
    private Label sliderValue1;

    @FXML
    private Label sliderValue2;

    @FXML
    private TextField tfP1;

    @FXML
    private TextField tfP2;

    @FXML
    private TextField tfPUSB;

    @FXML
    private Button btnBtn;

    @FXML
    private Button btnLogs;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnP1;

    @FXML
    private Button btnP2;

    @FXML
    private Button btnPUSB;

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private Button b4;

    @FXML
    private Button b5;

    @FXML
    private Button b6;

    @FXML
    private Button b7;

    @FXML
    private Button b8;

    @FXML
    private Button setter1;

    @FXML
    private Button setter2;

    @FXML
    private Button setter3;

    @FXML
    private Button setter4;

    @FXML
    private Button setter5;

    @FXML
    private Button setter6;

    @FXML
    private Button setter7;

    @FXML
    private Button setter8;

    @FXML
    private AnchorPane pnBtn;

    @FXML
    private AnchorPane pnLogs;

    @FXML
    private AnchorPane pnSettings;

    @FXML
    private Slider slider1;

    @FXML
    private Slider slider2;

    @FXML
    private ChoiceBox<String> choiceBox;

    Image img_redDot = new Image(mainController.class.getResource("img/redDot.png").toString());
    
    Image img_orangeDot = new Image(mainController.class.getResource("img/orangeDot.png").toString());
    
    Image img_greenDot = new Image(mainController.class.getResource("img/greenDot.png").toString());

    
    //Initialisation des informations manquantes à certains composants
    //Initialize est appelée au démarrage de l'application
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Properties prop = CommandHandler.prop;
        tfP1.setText(prop.getProperty("p1"));
        tfP2.setText(prop.getProperty("p2"));
        tfPUSB.setText(prop.getProperty("usb"));
        labP1.setText(prop.getProperty("p1"));
        labP2.setText(prop.getProperty("p2"));
        b1.setText(prop.getProperty("b1"));
        b2.setText(prop.getProperty("b2"));
        b3.setText(prop.getProperty("b3"));
        b4.setText(prop.getProperty("b4"));
        b5.setText(prop.getProperty("b5"));
        b6.setText(prop.getProperty("b6"));
        b7.setText(prop.getProperty("b7"));
        b8.setText(prop.getProperty("b8"));
        setter1.setText(prop.getProperty("b1"));
        setter2.setText(prop.getProperty("b2"));
        setter3.setText(prop.getProperty("b3"));
        setter4.setText(prop.getProperty("b4"));
        setter5.setText(prop.getProperty("b5"));
        setter6.setText(prop.getProperty("b6"));
        setter7.setText(prop.getProperty("b7"));
        setter8.setText(prop.getProperty("b8"));

        textArea.setText(IOHandler.readLogs());

        //Alimentation des choix de la choiceBox de connexion
        choiceBox.getItems().addAll(getIPV4());
        //choiceBox.getItems().add("USB");
        choiceBox.setOnAction(this::startConnection);

        //Ajout des listener pour modifier les labels en fonction de la valeur des sliders
        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2 ){
                sliderValue1.setText(Integer.toString((int) slider1.getValue()));
            }
        });
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2 ){
                sliderValue2.setText(Integer.toString((int) slider2.getValue()));
            }
        });
        
    }

    //Gestion des boutons du menu principal
    @FXML
    public void handleMenuClicks(ActionEvent event){
        if(event.getSource() == btnBtn){
            pnBtn.toFront();
        }
        else if(event.getSource() == btnLogs){
            textArea.setText(IOHandler.readLogs());
            pnLogs.toFront();
        } 
        else if(event.getSource() == btnSettings){
            pnSettings.toFront();
        }
    }

    //Gestion des boutons de paramètrage des ports
    @FXML
    public void handlePortsClicks(ActionEvent event){
        String value = null;
        if(event.getSource() == btnP1){
            value = tfP1.getText();
            if(checkPortValue(value)){
                IOHandler.writeConfigFile("p1", value);
            }
        }
        else if (event.getSource() == btnP2){
            value = tfP2.getText();
            if(checkPortValue(value)){
                IOHandler.writeConfigFile("p2", value);
            }
        }
        else if (event.getSource() == btnPUSB){
            value = tfPUSB.getText();
            if(checkPortValue(value)){
                IOHandler.writeConfigFile("usb", value);
            }
        }
    }

    //Gestion des clicks "Triggers"
    @FXML
    public void handleTriggersClicks(ActionEvent event){
        CommandHandler.signalManager(((Button)event.getSource()).getId() + ":");
    }

    //Lance un protocole de connexion en fonction de la valeur de la choiceBox
    public void startConnection(ActionEvent event){

        //Fermeture des connexions précédentes si elles existent
        ServerHandler.close();

        String choice = choiceBox.getValue();
        if (choice == "USB"){
            //System.out.println("USB Connexion is not accessible yet");
        }
        else{
            //Lancement du protocole de connexion Wi-Fi

            //Récupération des ports de connexion
            int p1 = Integer.parseInt(labP1.getText());
            int p2 = Integer.parseInt(labP2.getText());

            try {
                //Récupération de l'InetAddress avec l'ip choisie
                InetAddress serverIP = InetAddress.getByName(choice);

                //Genration d'une nouvelle instance ServerHandler
                ServerHandler.getInstance(serverIP, p1, p2);

                ServerHandler.task.messageProperty().addListener((obs, oldMsg, newMsg) -> {
                    switch(newMsg){
                        case "WAIT":
                            connStatus.setText("En attente ...");
                            imgStatus.setImage(img_orangeDot);
                            break;
                        case "FULL":
                            connStatus.setText("Connecté");
                            imgStatus.setImage(img_greenDot);
                            break;
                        case "CLOSE":
                            connStatus.setText("Non connecté");
                            imgStatus.setImage(img_redDot);
                             break;
                        default:
                            String[] splited = newMsg.split(";");
                            String[] splited2 = splited[1].split(":");
                            if (splited2.length > 1){
                                switch(splited2[0]){
                                    case "b9":
                                        slider1.setValue(Double.parseDouble(splited2[1]));
                                        break;
                                    case "b10":
                                        slider2.setValue(Double.parseDouble(splited2[1]));
                                }
                            }
                            CommandHandler.signalManager(splited[1]);
                    }
                });
                new Thread(ServerHandler.task).start();
            } catch (UnknownHostException uhe) {
                IOHandler.writeLog("ERROR", "Impossible de trouver "+choice+"sur l'interface réseau");
            }
        }
        
    }

    //Récupération de la liste des IPv4 disponibles sur le réseau.
    public List<String> getIPV4(){
        List<String> listIPv4 = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()){
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()){
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet6Address) continue;
                    listIPv4.add(addr.getHostAddress());
                }
            }
        } catch (SocketException se) {
            IOHandler.writeLog("ERROR", "Impossible d'établir la liste des IPv4 disponibles");
        }
        return listIPv4;
    }

    //Verifie sur le port entrée est un entier compris dans l'interval [1025;65534]
    public boolean checkPortValue (String value) {
        try{
            int test = Integer.parseInt(value);
            if (test > 1024 && test < 65535){
                return true;
            }
            IOHandler.throwAlert(AlertType.ERROR, null, "Le port doit se trouver dans la range [1025;65534]");
            return false;
        }
        //Exception si le parsing de value en entier echoue
        catch(NumberFormatException nfe){
            IOHandler.throwAlert(AlertType.ERROR, null,"Le numéro saisi n'est pas un entier");
            return false;
        }
    }

}
