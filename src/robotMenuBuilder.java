import java.util.Map;
import javax.swing.*;

public class robotMenuBuilder {
    private final JFrame frame;
    private final JMenuBar menuBar;
    private final Map<String, RobotLivraison> robotMap;//Map qui associe l'ID d'un robot (String) à un objet RobotLivraison.
    private final Map<String, JMenu> robotMenus;// Map qui associe l'ID d'un robot à son menu correspondant dans l'interface graphique.


//constructeur
public robotMenuBuilder(JFrame frame, JMenuBar menuBar, Map<String, RobotLivraison> robotMap, Map<String, JMenu> robotMenus) {
        this.frame = frame;
        this.menuBar = menuBar;
        this.robotMap = robotMap;
        this.robotMenus = robotMenus;
}
//'ajouter un robot à la collection robotMap
public void addRobot(RobotLivraison robot, JFrame frame) {
        robotMap.put(robot.id, robot);
        try {
            JMenu menu = createMenuFor(robot, frame);
            robotMenus.put(robot.id, menu);
            menuBar.add(menu);
            refresh();
        } catch (RobotException ex) {
            JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur lors de la création du menu", JOptionPane.ERROR_MESSAGE);
        }
}
//creation d'un menu correspondant au robot via createMenuFor puis l'ajoute a la barre de menu
public void updateMenuFor(RobotLivraison robot) {
        JMenu oldMenu = robotMenus.get(robot.id);
        if (oldMenu != null) {
            menuBar.remove(oldMenu);
        }

        try {
            JMenu newMenu = createMenuFor(robot, frame);
            robotMenus.put(robot.id, newMenu);
            menuBar.add(newMenu);
            refresh();
        } catch (RobotException ex) {
            JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur lors de la création du menu", JOptionPane.ERROR_MESSAGE);
        }
}
public JMenu createMenuFor(RobotLivraison robot, JFrame frame) throws RobotException {
        //we're going to handle all the actions in the submenu of the robots here 
        //actions we need to perform:
        //history
        //status
        //delete
        //connect
        //disconnect
        //incerement energy
        //perform a shipment
        //envoi donnée

        //history
        JMenu robotSubMenu = new JMenu(robot.id);

        JMenuItem history = new JMenuItem("History");
        history.addActionListener(e -> {
            String historyList = robot.getHistorique();
            JOptionPane.showMessageDialog(frame, historyList, "History of " + robot.id, JOptionPane.INFORMATION_MESSAGE);
        });
        robotSubMenu.add(history);

        if(robot.enMarche == true) {
            //status
        JMenuItem status = new JMenuItem("Status");
        status.addActionListener(e -> {
            String details = robot.toString();
            JOptionPane.showMessageDialog(frame, details, "Details de " + robot.id, JOptionPane.INFORMATION_MESSAGE);
        });
        
        robotSubMenu.add(status);

        //connect
        JMenuItem connect = new JMenuItem("Se connecter sur un réseau");
        connect.addActionListener(e -> {
            String network = JOptionPane.showInputDialog(frame, "Entrer le réseau:");
            if (network != null && !network.isBlank()) {
                try {
                    robot.connecter(network);
                    System.out.println("Connecté au réseau: " + network);
                } catch (RobotException ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Cancelled or empty input.");
            }
        });

        robotSubMenu.add(connect);

        //disconenct
        JMenuItem disconnect = new JMenuItem("Se déconnecter du réseau");
        disconnect.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(frame, "Voulez vous se déconnecter du réseau " + robot.reseauConnecte+ " ?", "Déconnexion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(resp == JOptionPane.YES_OPTION) {
                robot.deconnecter();
                System.out.println("Déconncté du réseau.");
            } else {
            }
        
        });
        robotSubMenu.add(disconnect);

        //envoi donnée
        JMenuItem send = new JMenuItem("Envoyer des Données");
        send.addActionListener(e -> {
            //verifier que le robot est connecte d'abord
            if (robot.connecte == true) {
                String donnees = JOptionPane.showInputDialog(frame, "Entrer les données à envoyer:");
                if (donnees != null && !donnees.isBlank()) {
                    try {
                        robot.envoyerDonnees(donnees);
                        System.out.println("envoi des donnees: " + donnees);
                    } catch (RobotException ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur d'envoi", JOptionPane.ERROR_MESSAGE);
                    }

                } 

            } else {
                JOptionPane.showMessageDialog(frame, "Erreur: " + "Vous devez etre connectés sur un réseau pour envoyer des données!", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            }
        });

        robotSubMenu.add(send);

        //increment energy 
        JMenuItem increment = new JMenuItem("Booster l'énergie");
        increment.addActionListener(e -> {
    
            Integer newEnergy = EnergyDialog.showEnergyDialog(frame, robot.energie);
            if(newEnergy != null) {
                robot.Recharger(newEnergy-robot.energie);
                System.out.println("Nouvelle energie: " + newEnergy);
            } else {
                System.out.println("Level update cancelled.");
            }
        });
        robotSubMenu.add(increment);

        //livraison

        JMenuItem livrer = new JMenuItem("Faire une commande");
        livrer.addActionListener(e -> {
            Object[] infoLivraison = livraisonDialog.showDialog(frame);
            if(infoLivraison != null) {
                String dest = (String) infoLivraison[0];
                int x = (int) infoLivraison[1];
                int y = (int) infoLivraison[2];
                
                try {
                    robot.chargercolis(dest);
                    robot.faireLivraison(x, y, dest);
                } catch (RobotException ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        robotSubMenu.add(livrer);

        //mettre en veille 

        JMenuItem veilleItem = new JMenuItem("Mettre en veille");
        veilleItem.addActionListener(e -> {
                robot.Arreter();
                updateMenuFor(robot);
            });

        JMenuItem modeeco =new JMenuItem("Activer mode eco");
        modeeco.addActionListener(e ->{
            if(robot.isModeco()){robot.setModeco(false);modeeco.setText("Activer mode eco");robot.AjouterHistorique("mode eco desactivé");

            }
            else {robot.setModeco(true);modeeco.setText("desactiver mode eco");robot.AjouterHistorique("mode eco activé");}
        });
        robotSubMenu.add(modeeco);

        robotSubMenu.add(veilleItem);

        } else {
            //redemarrer
            JMenuItem restartItem = new JMenuItem("Redemarrer");
            restartItem.addActionListener(e -> {
                try {
                    robot.Demarrer();
                    updateMenuFor(robot);
                } catch (RobotException ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            robotSubMenu.add(restartItem);
    }

        
        

        return robotSubMenu;

    }

    private void refresh() {
        frame.revalidate();
        frame.repaint();
    }

}