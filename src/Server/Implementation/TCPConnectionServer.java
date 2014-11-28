package Server.Implementation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Hero
 * Date: 28.11.14
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class TCPConnectionServer {

     /*
    * Dies ist die TCP-Vorlage des Servers aus Hübners Pub für RN
    */


    /* Server, der Verbindungsanfragen entgegennimmt */
    public static final int SERVER_PORT = 6789;

    public static void main(String[] args) {
        ServerSocket welcomeSocket; // TCP-Server-Socketklasse
        Socket connectionSocket; // TCP-Standard-Socketklasse

        int counter = 0; // Zählt die erzeugten Bearbeitungs-Threads

        try {
      /* Server-Socket erzeugen */
            welcomeSocket = new ServerSocket(SERVER_PORT);

            while (true) { // Server laufen IMMER
                System.out.println("TCP Server: Waiting for connection - listening TCP port " +
                        SERVER_PORT);
        /*
         * Blockiert auf Verbindungsanfrage warten --> nach
         * Verbindungsaufbau Standard-Socket erzeugen und
         * connectionSocket zuweisen
         */
                connectionSocket = welcomeSocket.accept();

        /* Neuen Arbeits-Thread erzeugen und den Socket übergeben */
                (new TCPServerThread(++counter, connectionSocket)).start();
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}


class TCPServerThread extends Thread {
    /* Arbeitsthread, der eine existierende Socket-Verbindung zur Bearbeitung erhält */
    private int name;
    private Socket socket;

    private BufferedReader inFromClient;
    private DataOutputStream outToClient;

    boolean serviceRequested = true; // Arbeitsthread beenden?

    public TCPServerThread(int num, Socket sock) {
    /* Konstruktor */
        this.name = num;
        this.socket = sock;
    }

    public void run() {
        String capitalizedSentence;

        System.out.println("TCP Server Thread " + name +
                " is running until QUIT is received!");

        try {
      /* Socket-Basisstreams durch spezielle Streams filtern */
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());

            while (serviceRequested) {
        /* String vom Client empfangen und in Großbuchstaben umwandeln */
                capitalizedSentence = readFromClient().toUpperCase();

        /* Modifizierten String an Client senden */
                writeToClient(capitalizedSentence);

        /* Test, ob Arbeitsthread beendet werden soll */
                if (capitalizedSentence.indexOf("QUIT") > -1) {
                    serviceRequested = false;
                }
            }

      /* Socket-Streams schließen --> Verbindungsabbau */
            socket.close();
        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }

        System.out.println("TCP Server Thread " + name + " stopped!");
    }

    private String readFromClient() {
    /* Liefere die nächste Anfrage-Zeile (request) vom Client */
        String request = "";

        try {
            request = inFromClient.readLine();
        } catch (IOException e) {
            System.err.println("Connection aborted by client!");
            serviceRequested = false;
        }
        System.out.println("TCP Server Thread detected job: " + request);
        return request;
    }

    private void writeToClient(String reply) {
    /* Sende den String als Antwortzeile (mit newline) zum Client */
        try {
            outToClient.writeBytes(reply + '\n');
        } catch (IOException e) {
            System.err.println(e.toString());
            serviceRequested = false;
        }
        System.out.println("TCP Server Thread " + name +
                " has written the message: " + reply);
    }
}
