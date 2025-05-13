import java.util.ArrayList;
import java.util.List;

//Classe abstraite représentant un robot générique
public abstract class Robot {
    //Les attributs
    protected String id;
    protected int x,y;
    protected int energie; //de 0 à 100
    protected int heureUtilisation;
    protected boolean enMarche;
    protected List<String> historiqueActions;

    //Constructeur
    public Robot(String id, int x, int y, int energie, int heureUtilisation) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.energie = energie;
        this.heureUtilisation = heureUtilisation;
        this.enMarche = false;
        this.historiqueActions = new ArrayList<>();
        AjouterHistorique("Robot créé");
    }
    //Ajout d'une action à l'historique avec la date et l'heure
    protected void AjouterHistorique(String action) {
        String date = java.time.LocalDate.now().toString();
        String heure = java.time.LocalTime.now().toString();
        historiqueActions.add(date + " " + heure + " " + action);
    }
    //Vérification s'il y a assez d'énergie
    protected void VerifierEnergie(int energieRequise) throws EnergieInsuffisanteException {
        if (this.energie < energieRequise) {
            throw new EnergieInsuffisanteException("Energie insuffisante pour effectuer l'action!");
        }
    }
    //Vérification si le robot a besoin de maintenance
    protected void VerifierMaintenance() throws MaintenanceRequiseException {
        if (this.heureUtilisation > 100){
            throw new MaintenanceRequiseException ("Maintenance requise !");
        }
    }
    //Démarrage du robot
    public void Demarrer() throws RobotException {
        if (this.energie < 10){
            throw new RobotException ("Pas assez d'énergie pour démarrer !");
        }
        enMarche= true;
        AjouterHistorique("Robot démarré");
    }
    //Arret du robot
    public void Arreter(){
        enMarche= false;
        AjouterHistorique("Robot arreté");
    }
    //Consommation de l'énergie du robot
    protected void ConsommerEnergie(int quantité){
        this.energie -= quantité;
        if (this.energie < 0){
            this.energie=0;
        }
    }
    //Recharge du robot
    protected void Recharger(int quantité){
        int old = this.energie;
        this.energie += quantité;
        if (this.energie > 100){
            this.energie=100;
        }
        AjouterHistorique("Changement d'énergie: de " + old + " à " + this.energie);
    }
    //Méthodes abstraites
    public abstract void deplacer(int x, int y) throws RobotException;
    public abstract void effectuerTache() throws RobotException;

    //Récupération de l'historique complet sous forme de string
    public String getHistorique(){
        String historique="";
        for (String action : historiqueActions) {
            historique += action + "\n"; // On ajoute l'action + retour à la ligne
        }
        return historique;
    }
    //Affichage
    @Override
    public String toString(){
        return "RobotIndustriel [ID : "+this.id+",Position : (" + this.x + "," + this.y + "), Énergie : " + this.energie +"%, Heures : " + this.heureUtilisation + "]";
    }

}
