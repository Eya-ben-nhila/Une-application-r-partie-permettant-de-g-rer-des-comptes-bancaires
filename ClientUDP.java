import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1028;
    private static final int TIMEOUT = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            
            System.out.println("Client bancaire UDP prêt");
            
            while (true) {
                System.out.print("\nCommande (CREATION/POSITION/AJOUT/RETRAIT/QUIT): ");
                String command = scanner.nextLine().trim();
                
                if (command.equalsIgnoreCase("QUIT")) {
                    break;
                }
                
                byte[] sendData = command.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, serverAddress, PORT);
                socket.send(sendPacket);
                
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Réponse: " + response);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout - Pas de réponse du serveur");
        } catch (IOException e) {
            System.out.println("Erreur de communication: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
