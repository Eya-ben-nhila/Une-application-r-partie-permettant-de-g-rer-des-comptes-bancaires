import java.io.*;
import java.net.*;

public class ServeurUDP {
    private static final int PORT = 1028;
    private static BanqueSimple banque = new BanqueSimple();

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Serveur bancaire UDP démarré sur le port " + PORT);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                String request = new String(packet.getData(), 0, packet.getLength());
                String response = ProtocolHandler.processRequest(request, banque);
                
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                    responseData, responseData.length, 
                    packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur: " + e.getMessage());
        }
    }
}
