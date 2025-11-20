import java.util.ArrayList;
import java.util.Scanner;

class CompteBancaire {
    // Attributs pour les informations personnelles
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String lieuNaissance;
    private String lieuResidence;
    private String telephone;
    private int age;
    
    // Attributs pour le compte
    private String numeroCompte;
    private String motDePasse;
    private String type;
    private double frais;
    private double solde;
    private ArrayList<String> historique;
    
    // Constructeur
    public CompteBancaire(String nom, String prenom, String dateNaissance, String lieuNaissance,
                         String lieuResidence, String telephone, int age, String numeroCompte,
                         String motDePasse, String type, double frais) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.lieuResidence = lieuResidence;
        this.telephone = telephone;
        this.age = age;
        this.numeroCompte = numeroCompte;
        this.motDePasse = motDePasse;
        this.type = type;
        this.frais = frais;
        this.solde = 0.0;
        this.historique = new ArrayList<>();
        this.historique.add("Compte créé - Solde initial: 0.0€");
    }
    
    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getDateNaissance() { return dateNaissance; }
    public String getLieuNaissance() { return lieuNaissance; }
    public String getLieuResidence() { return lieuResidence; }
    public String getTelephone() { return telephone; }
    public int getAge() { return age; }
    public String getNumeroCompte() { return numeroCompte; }
    public String getMotDePasse() { return motDePasse; }
    public String getType() { return type; }
    public double getFrais() { return frais; }
    public double getSolde() { return solde; }
    public ArrayList<String> getHistorique() { return historique; }
    
    // Setters pour la modification
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setLieuResidence(String lieuResidence) { this.lieuResidence = lieuResidence; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    
    // Méthodes pour les opérations bancaires
    public boolean deposer(double montant) {
        if (montant > 0) {
            solde += montant;
            historique.add("Dépôt: +" + montant + "€ - Nouveau solde: " + solde + "€");
            return true;
        }
        return false;
    }
    
    public boolean retirer(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            historique.add("Retrait: -" + montant + "€ - Nouveau solde: " + solde + "€");
            return true;
        }
        return false;
    }
    
    public boolean effectuerVirement(CompteBancaire destinataire, double montant) {
        if (montant > 0 && montant <= solde) {
            this.solde -= montant;
            destinataire.solde += montant;
            
            this.historique.add("Virement envoyé à " + destinataire.getNumeroCompte() + ": -" + montant + "€");
            destinataire.historique.add("Virement reçu de " + this.numeroCompte + ": +" + montant + "€");
            
            return true;
        }
        return false;
    }
    
    public void afficherSolde() {
        System.out.println("\n=== SOLDE DU COMPTE ===");
        System.out.println("Titulaire: " + prenom + " " + nom);
        System.out.println("N° Compte: " + numeroCompte);
        System.out.println("Solde: " + solde + "€");
        System.out.println("=======================");
    }
    
    public void afficherHistorique() {
        System.out.println("\n=== HISTORIQUE DU COMPTE " + numeroCompte + " ===");
        System.out.println("Titulaire: " + prenom + " " + nom);
        for (String operation : historique) {
            System.out.println("- " + operation);
        }
        System.out.println("=================================");
    }
}

public class SystemeBancaireComplet {
    private static ArrayList<CompteBancaire> listeComptes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static String nomBanque = "Banque Géniale";
    private static int compteurNumeros = 1;
    
    public static void main(String[] args) {
        System.out.println("Bienvenue à la " + nomBanque + " !");
        
        while (true) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine(); // Vider le buffer
            
            switch (choix) {
                case 1:
                    creerCompte();
                    break;
                case 2:
                    modifierCompte();
                    break;
                case 3:
                    supprimerCompte();
                    break;
                case 4:
                    afficherTousLesComptes();
                    break;
                case 5:
                    effectuerDepot();
                    break;
                case 6:
                    effectuerRetrait();
                    break;
                case 7:
                    effectuerVirement();
                    break;
                case 8:
                    consulterSolde();
                    break;
                case 9:
                    consulterHistorique();
                    break;
                case 10:
                    System.out.println("Merci d'avoir utilisé " + nomBanque + " ! Au revoir !");
                    System.out.println("Nombre total de comptes créés: " + (compteurNumeros - 1));
                    return;
                default:
                    System.out.println("Option invalide ! Veuillez choisir entre 1 et 10");
            }
        }
    }
    
    private static void afficherMenu() {
        System.out.println("\n=== " + nomBanque + " ===");
        System.out.println("1. Créer un compte");
        System.out.println("2. Modifier un compte");
        System.out.println("3. Supprimer un compte");
        System.out.println("4. Afficher tous les comptes");
        System.out.println("5. Effectuer un dépôt");
        System.out.println("6. Effectuer un retrait");
        System.out.println("7. Effectuer un virement");
        System.out.println("8. Consulter un solde");
        System.out.println("9. Consulter l'historique");
        System.out.println("10. Quitter");
        System.out.print("Choisissez une option (1-10): ");
    }
    
    private static String genererNumeroCompte() {
        String numero = String.format("%09d", compteurNumeros);
        compteurNumeros++;
        return numero;
    }
    
    private static CompteBancaire trouverCompte(String numeroCompte) {
        for (CompteBancaire compte : listeComptes) {
            if (compte.getNumeroCompte().equals(numeroCompte)) {
                return compte;
            }
        }
        return null;
    }
    
    private static void creerCompte() {
        System.out.println("\n=== CRÉATION DE COMPTE ===");
        
        // Choix du type de compte
        System.out.println("Types de compte disponibles:");
        System.out.println("1. Compte Courant (frais: 2% par mois)");
        System.out.println("2. Compte Épargne (frais: 0.5% par mois)");
        System.out.println("3. Compte Professionnel (frais: 3% par mois)");
        System.out.print("Choisissez le type (1-3): ");
        int typeCompte = scanner.nextInt();
        scanner.nextLine();
        
        if (typeCompte < 1 || typeCompte > 3) {
            System.out.println("Type de compte invalide !");
            return;
        }
        
        // Informations personnelles
        System.out.println("--- Informations personnelles ---");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Date de naissance (jj/mm/aaaa): ");
        String dateNaissance = scanner.nextLine();
        System.out.print("Lieu de naissance: ");
        String lieuNaissance = scanner.nextLine();
        System.out.print("Lieu de résidence: ");
        String lieuResidence = scanner.nextLine();
        System.out.print("Numéro de téléphone: ");
        String telephone = scanner.nextLine();
        
        // Calcul de l'âge (simplifié)
        int age = calculerAge(dateNaissance);
        
        // Vérification âge
        if (age < 21) {
            System.out.println("Désolé " + prenom + " " + nom + ", vous êtes mineur, donc vous ne pouvez pas créer un compte bancaire");
            return;
        }
        
        // Génération automatique du numéro de compte
        String numeroCompte = genererNumeroCompte();
        
        System.out.println("--- Informations du compte ---");
        System.out.println("Votre numéro de compte généré: " + numeroCompte);
        System.out.print("Choisissez un mot de passe: ");
        String motDePasse = scanner.nextLine();
        
        // Détermination du type et frais
        String type = "";
        double frais = 0;
        
        switch (typeCompte) {
            case 1:
                frais = 2;
                type = "Courant";
                break;
            case 2:
                frais = 0.5;
                type = "Épargne";
                break;
            case 3:
                frais = 3;
                type = "Professionnel";
                break;
        }
        
        System.out.println("ATTENTION: Ce compte " + type + " subira des frais de " + frais + "% chaque mois");
        System.out.print("Confirmez-vous la création? (O/N): ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("O")) {
            CompteBancaire nouveauCompte = new CompteBancaire(
                nom, prenom, dateNaissance, lieuNaissance, lieuResidence,
                telephone, age, numeroCompte, motDePasse, type, frais
            );
            
            listeComptes.add(nouveauCompte);
            System.out.println("Compte créé avec succès pour " + prenom + " " + nom + " !");
            System.out.println("Votre numéro de compte: " + numeroCompte + " - Gardez-le précieusement !");
        } else {
            System.out.println("Création annulée");
            compteurNumeros--; // On décrémente le compteur si annulation
        }
    }
    
    private static void modifierCompte() {
        System.out.println("\n=== MODIFICATION DE COMPTE ===");
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null && compte.getMotDePasse().equals(mdp)) {
            System.out.println("Compte trouvé ! Informations actuelles:");
            System.out.println("1. Nom: " + compte.getNom());
            System.out.println("2. Prénom: " + compte.getPrenom());
            System.out.println("3. Lieu de résidence: " + compte.getLieuResidence());
            System.out.println("4. Numéro de téléphone: " + compte.getTelephone());
            System.out.println("5. Mot de passe: " + compte.getMotDePasse());
            System.out.print("Que voulez-vous modifier? (1-5): ");
            
            int champ = scanner.nextInt();
            scanner.nextLine();
            
            switch (champ) {
                case 1:
                    System.out.print("Nouveau nom: ");
                    compte.setNom(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Nouveau prénom: ");
                    compte.setPrenom(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Nouveau lieu de résidence: ");
                    compte.setLieuResidence(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nouveau numéro de téléphone: ");
                    compte.setTelephone(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Nouveau mot de passe: ");
                    compte.setMotDePasse(scanner.nextLine());
                    break;
                default:
                    System.out.println("Option invalide");
                    return;
            }
            
            System.out.println("Modification réussie !");
        } else {
            System.out.println("Compte non trouvé ou mot de passe incorrect");
        }
    }
    
    private static void supprimerCompte() {
        System.out.println("\n=== SUPPRESSION DE COMPTE ===");
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null && compte.getMotDePasse().equals(mdp)) {
            System.out.println("Compte trouvé !");
            System.out.println("Titulaire: " + compte.getPrenom() + " " + compte.getNom());
            System.out.println("Solde actuel: " + compte.getSolde() + "€");
            System.out.print("Êtes-vous SÛR de vouloir supprimer ce compte? (O/N): ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("O")) {
                listeComptes.remove(compte);
                System.out.println("Compte de " + compte.getPrenom() + " " + compte.getNom() + " supprimé avec succès");
            } else {
                System.out.println("Suppression annulée");
            }
        } else {
            System.out.println("Compte non trouvé ou mot de passe incorrect");
        }
    }
    
    private static void afficherTousLesComptes() {
        System.out.println("\n=== LISTE DES COMPTES ===");
        if (listeComptes.isEmpty()) {
            System.out.println("Aucun compte créé");
        } else {
            for (CompteBancaire compte : listeComptes) {
                System.out.println("N°: " + compte.getNumeroCompte() + 
                                 " | " + compte.getPrenom() + " " + compte.getNom() + 
                                 " | Type: " + compte.getType() + 
                                 " | Frais: " + compte.getFrais() + "%/mois" +
                                 " | Solde: " + compte.getSolde() + "€" +
                                 " | Résidence: " + compte.getLieuResidence());
            }
        }
    }
    
    private static void effectuerDepot() {
        System.out.println("\n=== EFFECTUER UN DÉPÔT ===");
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null) {
            System.out.print("Montant à déposer: ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            
            if (compte.deposer(montant)) {
                System.out.println("Dépôt réussi !");
                compte.afficherSolde();
            } else {
                System.out.println("Erreur: Le montant doit être positif");
            }
        } else {
            System.out.println("Compte non trouvé !");
        }
    }
    
    private static void effectuerRetrait() {
        System.out.println("\n=== EFFECTUER UN RETRAIT ===");
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null && compte.getMotDePasse().equals(mdp)) {
            System.out.print("Montant à retirer: ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            
            if (compte.retirer(montant)) {
                System.out.println("Retrait réussi !");
                compte.afficherSolde();
            } else {
                System.out.println("Erreur: Montant invalide ou solde insuffisant");
                System.out.println("Solde actuel: " + compte.getSolde() + "€");
            }
        } else {
            System.out.println("Compte non trouvé ou mot de passe incorrect");
        }
    }
    
    private static void effectuerVirement() {
        System.out.println("\n=== EFFECTUER UN VIREMENT ===");
        System.out.print("Numéro de compte émetteur: ");
        String numeroEmetteur = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();
        
        CompteBancaire emetteur = trouverCompte(numeroEmetteur);
        if (emetteur != null && emetteur.getMotDePasse().equals(mdp)) {
            System.out.print("Numéro de compte destinataire: ");
            String numeroDestinataire = scanner.nextLine();
            
            CompteBancaire destinataire = trouverCompte(numeroDestinataire);
            if (destinataire != null) {
                System.out.print("Montant du virement: ");
                double montant = scanner.nextDouble();
                scanner.nextLine();
                
                if (emetteur.effectuerVirement(destinataire, montant)) {
                    System.out.println("Virement réussi !");
                    System.out.println("Nouveau solde émetteur: " + emetteur.getSolde() + "€");
                } else {
                    System.out.println("Erreur: Montant invalide ou solde insuffisant");
                    System.out.println("Solde actuel: " + emetteur.getSolde() + "€");
                }
            } else {
                System.out.println("Compte destinataire non trouvé !");
            }
        } else {
            System.out.println("Compte émetteur non trouvé ou mot de passe incorrect");
        }
    }
    
    private static void consulterSolde() {
        System.out.println("\n=== CONSULTER UN SOLDE ===");
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null && compte.getMotDePasse().equals(mdp)) {
            compte.afficherSolde();
        } else {
            System.out.println("Compte non trouvé ou mot de passe incorrect");
        }
    }
    
    private static void consulterHistorique() {
        System.out.println("\n=== CONSULTER L'HISTORIQUE ===");
        System.out.print("Numéro de compte: ");
        String numero = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();
        
        CompteBancaire compte = trouverCompte(numero);
        if (compte != null && compte.getMotDePasse().equals(mdp)) {
            compte.afficherHistorique();
        } else {
            System.out.println("Compte non trouvé ou mot de passe incorrect");
        }
    }
    
    private static int calculerAge(String dateNaissance) {
        // Version simplifiée - dans la réalité il faudrait parser la date
        try {
            String[] parties = dateNaissance.split("/");
            if (parties.length >= 3) {
                int anneeNaissance = Integer.parseInt(parties[2]);
                int anneeActuelle = java.time.Year.now().getValue();
                return anneeActuelle - anneeNaissance;
            }
        } catch (Exception e) {
            System.out.println("Format de date invalide, utilisation de 25 ans par défaut");
        }
        return 25; // Valeur par défaut si erreur
    }
}