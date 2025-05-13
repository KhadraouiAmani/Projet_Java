import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

//one thing we have to keep in mind, is that we're manipulating objects here
//donc ki nhebou nasn3ou robot mthln, lezemna dima naa3tiw object kemel bch baaed najmou nmanipuliweh 
//soit history soit fl reseau wala ay hkeya
public class Window {

    // Data structures for robots and menus
    static Map<String, RobotLivraison> robotMap = new HashMap<>();
    static Map<String, JMenu> robotMenus = new HashMap<>();
    static robotMenuBuilder menuBuilder;

    // UI components
    static JPanel contentPanel;
    JMenuBar menuBar;
    static JMenu robotMenu;
    static JMenu aboutMenu;
    JMenuItem aboutPageItem;

    public static void main(String[] args) {
        new Window(); // Let the constructor do the job
    }

    public Window() {
        menuBar = new JMenuBar();
        robotMenu = new JMenu("Robots");
        menuBar.add(robotMenu);

        JFrame frame = new JFrame("Robot Manager");
        menuBuilder = new robotMenuBuilder(frame, menuBar, robotMap, robotMenus);

        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(menuBar);

        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //this is where we control the content of each page
        contentPanel = new JPanel(new BorderLayout());
        frame.add(contentPanel, BorderLayout.CENTER);

        showAboutPage(frame);
    }

    public static void showAboutPage(JFrame frame) {
        contentPanel.removeAll(); //nfass5ou eli ken mawjoud
        JPanel aboutPanel = new JPanel();

        //bch naamlou box layout mel fou9 lel louta lel about panel
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("About Page");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createRobotButton = new JButton("Create Robot");
        createRobotButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        aboutPanel.add(label);
        
        //hateh yzidelna espace te3 20 pixels binet l components te3 l about panel
        aboutPanel.add(Box.createVerticalStrut(20));
        aboutPanel.add(createRobotButton);

        //action to create robot
        //show input dialog: tala3lek popup feha text field + ok and cancel buttons,
        //wl frame parameter meloul, bch l popup tji centered fl frame
        createRobotButton.addActionListener(e-> {
            //on va appeler le JDialog qu'on a créé
            Object[] robotInfo = MultiInputDialog.showDialog(frame);

            if (robotInfo != null) {
                String id = (String) robotInfo[0];
                int x = (int) robotInfo[1];
                int y = (int) robotInfo[2];
                int energie = (int) robotInfo[3];
                int heures = (int) robotInfo[4];
                RobotLivraison rob = new RobotLivraison(id, x, y, energie, heures);

                try {
                     //le demarrer
                    rob.Demarrer();
                    menuBuilder.addRobot(rob, frame);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show the about panel
        contentPanel.add(aboutPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
