import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTCP {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1027;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            System.out.println("Connecté au serveur bancaire TCP");
            
            while (true) {
                System.out.print("\nCommande (CREATION/POSITION/AJOUT/RETRAIT/QUIT): ");
                String command = scanner.nextLine().trim();
                
                if (command.equalsIgnoreCase("QUIT")) {
                    break;
                }
                
                out.println(command);
                String response = in.readLine();
                System.out.println("Réponse: " + response);
            }
            
        } catch (ConnectException e) {
            System.out.println("Serveur indisponible");
        } catch (IOException e) {
            System.out.println("Erreur de communication: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
