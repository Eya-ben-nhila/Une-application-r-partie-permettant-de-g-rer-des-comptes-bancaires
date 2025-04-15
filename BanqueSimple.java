import java.util.Date;
import java.util.HashMap;

public class BanqueSimple {
    public class CompteEnBanque {
        private double solde;
        private Date derniereOperation;
        
        public CompteEnBanque(double solde) {
            this.solde = solde;
            this.derniereOperation = new Date();
        }
        
        public double getSolde() {
            return solde;
        }
        
        public Date getDerniereOperation() {
            return derniereOperation;
        }
        
        public void ajouter(double somme) {
            solde += somme;
            derniereOperation = new Date();
        }
        
        public void retirer(double somme) {
            solde -= somme;
            derniereOperation = new Date();
        }
    }
    
    public HashMap<String, CompteEnBanque> comptes;
    
    public BanqueSimple() {
        comptes = new HashMap<>();
    }
    
    public void creerCompte(String id, double somme) {
        comptes.put(id, new CompteEnBanque(somme));
    }
    
    public void ajouter(String id, double somme) {
        CompteEnBanque cpt = comptes.get(id);
        if (cpt != null) {
            cpt.ajouter(somme);
        }
    }
    
    public void retirer(String id, double somme) {
        CompteEnBanque cpt = comptes.get(id);
        if (cpt != null) {
            cpt.retirer(somme);
        }
    }
    
    public double getSolde(String id) {
        CompteEnBanque cpt = comptes.get(id);
        return (cpt != null) ? cpt.getSolde() : 0;
    }
    
    public Date getDerniereOperation(String id) {
        CompteEnBanque cpt = comptes.get(id);
        return (cpt != null) ? cpt.getDerniereOperation() : null;
    }
}
