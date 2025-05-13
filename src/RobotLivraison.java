import java.util.Scanner;

public class RobotLivraison extends RobotConnecte {


    protected int colisActuel;
    protected String pid;
    protected String destination;
    protected Boolean enLivraison;
    static final int ENERGIE_LIVRAISON = 15;
    static final int ENERGIE_CHARGEMENT = 5;

    //edited , 5ater fama ambig fl énoncé
    public RobotLivraison(String id, int x, int y, int energie, int heureUtil) {
        super(id, x, y, energie, heureUtil);
        this.colisActuel = 0;
        this.pid = null;
        this.destination = null;
        this.enLivraison = false;
    }

    //hérités men robot

    @Override
    //cette methode deplace le robot vers les coordonnees x,y tt en verifiant l'enrgie
    //et en mettant a jour l'histo
    public void deplacer(int x, int y) throws RobotException{
        float dist = (float) Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
        if (dist>100) {
            throw new RobotException("Distance superieure a 100 unites!!");
        }
        
        VerifierEnergie((int)(0.3*dist));
        VerifierMaintenance();
        //energie !!
        int heureAUtiliser = (int) (dist/10); //une heure pour chaque 10 unites de distance
        this.heureUtilisation -= heureAUtiliser;
    
        AjouterHistorique(String.format("Déplacement de (%d, %d) vers (%d, %d)", this.x, this.y, x, y));

        this.x = x;
        this.y = y;

    }
    
    @Override
    public void effectuerTache() throws RobotException {
        Scanner input = new Scanner(System.in);
        if(!this.enMarche) throw new RobotException(String.format("(%s): Le robot doit etre demarre pour effectuer une tache!", this.id));
        else if(enLivraison == true) {
            System.out.println("Donner votre destination et coordonnees respectivement:");

            String dest = input.nextLine();
            int x = input.nextInt();
            int y = input.nextInt();

            try {
                faireLivraison(x, y, dest);
            } catch(RobotException e) { 
                System.out.println(e.getMessage());
            }

        }
        else {
            System.out.println("Chargement dun nouveau colis?");
            String rep = input.nextLine();
            if (rep.equals("oui")) {
                System.out.println("Donner votre destination et coordonnees  respectivement:");
                String dest = input.nextLine();
                int x = input.nextInt();
                int y = input.nextInt();
                try {
                    chargercolis(dest);
                    faireLivraison(x, y, dest);
                } catch(RobotException e) { 
                    System.out.println(e.getMessage());
                }
            } else {
                AjouterHistorique("En attente de colis");
            }
        }

    }

    @Override
    public String toString() {
        return "RobotIndustriel [ID : "+this.id+", Position : (" + this.x + "," + this.y + "), Énergie : " + this.energie +"%, Heures : " + this.heureUtilisation + ", Colis : "+ colisActuel + ", Destination: " + this.destination + ", Connecté: " + this.connecte + "]";
    }


    //spécifiques lel livraison
    public void faireLivraison(int destX, int destY, String dest) throws RobotException {
        deplacer(destX, destY);
        VerifierEnergie(ENERGIE_LIVRAISON);
        this.energie -= ENERGIE_LIVRAISON;
        this.colisActuel = 0;
        this.enLivraison = false;
        this.destination = dest;
        AjouterHistorique(String.format("Livraison terminee a %s (%d, %d).", destination, destX, destY));
    }

    public void chargercolis(String dest) throws RobotException {
        if (enLivraison || colisActuel != 0) throw new RobotException("Le robot " + id + " est deja en livraison!");
        else {
            VerifierEnergie(ENERGIE_CHARGEMENT);
            this.energie -= ENERGIE_CHARGEMENT;
            this.colisActuel = 1;
            this.destination = dest;
            AjouterHistorique("Chargement du colis à la destination " + destination);
        }
    }
     

}
