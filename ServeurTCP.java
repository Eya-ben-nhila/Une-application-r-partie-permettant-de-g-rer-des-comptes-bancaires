import java.io.*;
import java.net.*;

public class ServeurTCP {
    private static final int PORT = 1027;
    private static BanqueSimple banque = new BanqueSimple();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur bancaire TCP démarré sur le port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, banque)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final BanqueSimple banque;

        public ClientHandler(Socket socket, BanqueSimple banque) {
            this.clientSocket = socket;
            this.banque = banque;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                System.out.println("Nouvelle connexion TCP de: " + clientAddress);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = ProtocolHandler.processRequest(inputLine, banque);
                    out.println(response);
                }
            } catch (IOException e) {
                System.err.println("Erreur avec le client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Erreur fermeture socket: " + e.getMessage());
                }
            }
        }
    }
}
