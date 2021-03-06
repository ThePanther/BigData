package Client.Implementation;


import Client.GUI.ChatGUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageReceiver extends Thread {

    private int port;
    private int counter = 0;

    private ServerSocket welcomeSocket;
    private Socket connectionSocket;

    private ChatGUI chatGUI;

    private boolean serviceRequested = true;

    public MessageReceiver(int port,ChatGUI chatGUI) {
        this.port = port;
        this.chatGUI = chatGUI;

        try {
            welcomeSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                System.out.println("TCP Receiver: Waiting for connection - listening on TCP port: " + port);
                connectionSocket = welcomeSocket.accept();
                (new ReceiverThread(++counter,connectionSocket,chatGUI,this)).start();
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    public boolean getServiceRequested() {
        return serviceRequested;
    }

    public void setServiceRequested(boolean serviceRequested) {
        this.serviceRequested = serviceRequested;
    }
}
