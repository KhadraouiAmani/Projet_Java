// Classe abstraite pour robots pouvant se connecter à des réseaux
public abstract class RobotConnecte extends Robot implements Connectable{
    protected boolean connecte;
    protected String reseauConnecte; //nom du réseau auquel le robot est connecté

    public RobotConnecte(String id,int x,int y,int energie, int heureUtilisation){
        super(id,x,y,energie,heureUtilisation);
        this.connecte = false;
        this.reseauConnecte=null;
    }

    @Override
    public void connecter(String reseau) throws RobotException{
        VerifierEnergie(5);
        this.connecte=true;
        this.reseauConnecte=reseau;
        ConsommerEnergie(5);
        AjouterHistorique("Connecté au réseau " + reseau);
    }

    @Override
    public void deconnecter(){
        this.connecte=false;
        AjouterHistorique("Déconnecté du réseau : " + this.reseauConnecte);
        this.reseauConnecte=null;
    }

    @Override
    public void envoyerDonnees(String donnees) throws RobotException{
        if (!this.connecte){
            throw new RobotException("Le robot n'est pas connecté à un réseau !");
        }
        VerifierEnergie(3);
        ConsommerEnergie(3);
        AjouterHistorique("Données envoyées : " + donnees);
    }

    public static void main(String[] args) throws RobotException{
        
        RobotLivraison rc = new RobotLivraison("3", 0, 0, 100, 4); 
        rc.connecter("insat.com");
        rc.envoyerDonnees("123456");
        System.out.println(rc.toString());
        System.out.println(rc.getHistorique());
    }

}
