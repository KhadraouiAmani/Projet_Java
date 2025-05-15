import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// Classe principale de l'application
public class RobotManager {

    // Map pour stocker les robots créés
    static Map<String, RobotLivraison> robotMap = new HashMap<>();
    static JPanel contentPanel;
    static JMenu robotMenu;
    static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RobotManager().createUI());
    }

    // Méthode pour créer l'interface utilisateur
    public void createUI() {
        frame = new JFrame("Gestionnaire de Robots");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Création de la barre de menus
        JMenuBar menuBar = new JMenuBar();
        robotMenu = new JMenu("Robots");
        menuBar.add(robotMenu);

        JMenu aboutMenu = new JMenu("À propos");
        JMenuItem aboutPageItem = new JMenuItem("Page d'accueil");
        aboutPageItem.addActionListener(e -> showAboutPage());
        aboutMenu.add(aboutPageItem);

        menuBar.add(aboutMenu);
        frame.setJMenuBar(menuBar);

        // Panneau principal
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Afficher la page d'accueil au démarrage
        showAboutPage();
        frame.setVisible(true);
    }

    // Méthode pour afficher la page d'accueil
    public void showAboutPage() {
        contentPanel.removeAll();
        JPanel aboutPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel label = new JLabel("Page d'accueil - Gestion des Robots");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        aboutPanel.add(label, gbc);

        gbc.gridy++;
        JButton createRobotButton = new JButton("➕ Créer un Robot");
        createRobotButton.setPreferredSize(new Dimension(150, 30));
        aboutPanel.add(createRobotButton, gbc);

        createRobotButton.addActionListener(e -> createRobotDialog());

        contentPanel.add(aboutPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Méthode pour afficher le dialogue de création de robot
    public void createRobotDialog() {
        JTextField idField = new JTextField(10);
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JTextField energieField = new JTextField(5);
        JTextField heuresField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("ID du Robot:"));
        panel.add(idField);
        panel.add(new JLabel("Position X:"));
        panel.add(xField);
        panel.add(new JLabel("Position Y:"));
        panel.add(yField);
        panel.add(new JLabel("Énergie:"));
        panel.add(energieField);
        panel.add(new JLabel("Heures de service:"));
        panel.add(heuresField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Créer un nouveau robot", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int energie = Integer.parseInt(energieField.getText());
                int heures = Integer.parseInt(heuresField.getText());

                RobotLivraison robot = new RobotLivraison(id, x, y, energie, heures);
                robot.Demarrer();
                robotMap.put(id, robot);

                JMenuItem robotItem = new JMenuItem(id);
                robotItem.addActionListener(e -> showRobotDetails(robot));
                robotMenu.add(robotItem);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Méthode pour afficher les détails d'un robot
    public void showRobotDetails(RobotLivraison robot) {
        JPanel detailsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        detailsPanel.add(new JLabel("ID: " + robot.getId()));
        detailsPanel.add(new JLabel("Position: (" + robot.getX() + ", " + robot.getY() + ")"));
        detailsPanel.add(new JLabel("Énergie: " + robot.getEnergie()));
        detailsPanel.add(new JLabel("Heures de Service: " + robot.getHeures()));

        JOptionPane.showMessageDialog(frame, detailsPanel, "Détails du Robot", JOptionPane.INFORMATION_MESSAGE);
    }
}
