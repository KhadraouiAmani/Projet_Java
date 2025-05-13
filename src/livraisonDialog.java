import javax.swing.*;

public class livraisonDialog {

    public static Object[] showDialog(JFrame parent) {
        String dest;
        int x,y;
        
        JTextField destField = new JTextField(20);
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Destination:"));
        panel.add(destField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Position x:"));
        panel.add(xField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Position y:"));
        panel.add(yField);
        panel.add(Box.createVerticalStrut(10));


        int result = JOptionPane.showConfirmDialog(parent, panel, 
                "Faire la livraison d'une commande",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        

        //si on a clique sur ok ou pas        
        if(result==JOptionPane.OK_OPTION) {
            dest = destField.getText().trim();
            String xText = xField.getText().trim();
            String yText = yField.getText().trim();
           
            try {
                x = Integer.parseInt(xText);
                y = Integer.parseInt(yText);
                
                return new Object[] {dest, x, y};
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Vous devez entrer des nombres!");
                return null;
            }

        } else {
            return null;
        }
        
    }

}
