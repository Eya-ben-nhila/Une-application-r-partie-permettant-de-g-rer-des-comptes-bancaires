import java.text.SimpleDateFormat;
import java.util.Date;

public class ProtocolHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String processRequest(String request, BanqueSimple banque) {
        try {
            String[] parts = request.split(" ");
            String command = parts[0].toUpperCase();
            String id = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "CREATION":
                    if (parts.length != 3) return "ERREUR Syntaxe: CREATION id somme_initiale";
                    double initAmount = Double.parseDouble(parts[2]);
                    if (banque.comptes.containsKey(id)) return "ERREUR Compte existe déjà";
                    if (initAmount < 0) return "ERREUR Somme initiale négative";
                    banque.creerCompte(id, initAmount);
                    return "OK CREATION " + id;

                case "POSITION":
                    if (parts.length != 2) return "ERREUR Syntaxe: POSITION id";
                    if (!banque.comptes.containsKey(id)) return "ERREUR Compte inexistant";
                    double balance = banque.getSolde(id);
                    Date lastOp = banque.getDerniereOperation(id);
                    return "POS " + balance + " " + dateFormat.format(lastOp);

                case "AJOUT":
                    if (parts.length != 3) return "ERREUR Syntaxe: AJOUT id somme";
                    double addAmount = Double.parseDouble(parts[2]);
                    if (!banque.comptes.containsKey(id)) return "ERREUR Compte inexistant";
                    if (addAmount <= 0) return "ERREUR Somme à ajouter doit être positive";
                    banque.ajouter(id, addAmount);
                    return "OK AJOUT " + id;

                case "RETRAIT":
                    if (parts.length != 3) return "ERREUR Syntaxe: RETRAIT id somme";
                    double withdrawAmount = Double.parseDouble(parts[2]);
                    if (!banque.comptes.containsKey(id)) return "ERREUR Compte inexistant";
                    if (withdrawAmount <= 0) return "ERREUR Somme à retirer doit être positive";
                    if (banque.getSolde(id) < withdrawAmount) return "ERREUR Solde insuffisant";
                    banque.retirer(id, withdrawAmount);
                    return "OK RETRAIT " + id;

                default:
                    return "ERREUR Commande inconnue";
            }
        } catch (NumberFormatException e) {
            return "ERREUR Montant invalide";
        } catch (Exception e) {
            return "ERREUR " + e.getMessage();
        }
    }
}
