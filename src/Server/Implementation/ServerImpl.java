package Server.Implementation;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl extends Thread {

    private int port;
    private int counter = 0;

    private ServerSocket welcomeSocket;
    private Socket connectionSocket;
    private boolean serviceRequested = true;

    public ServerImpl(int port) {
        this.port = port;

        try {
            welcomeSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(serviceRequested) {
                System.out.println("TCP Server: Waiting for connection - listening on TCP port: " + port);
                connectionSocket = welcomeSocket.accept();
                (new ServerThread(++counter,connectionSocket)).start();
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        System.out.println("Server: Bye Bye.");

    }

    public void setServiceRequested(boolean serviceRequested) {
        this.serviceRequested = serviceRequested;
    }

}
