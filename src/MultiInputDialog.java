import javax.swing.*;

public class MultiInputDialog {

    public static Object[] showDialog(JFrame parent) {
        String id;
        int x,y;
        int energie;
        int heureUtilisation;

        JTextField idField = new JTextField(20);
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JTextField energieField = new JTextField(5);
        JTextField heuresField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Robot id:"));
        panel.add(idField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Position x:"));
        panel.add(xField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Position y:"));
        panel.add(yField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Energie:"));
        panel.add(energieField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("heures:"));
        panel.add(heuresField);

        int result = JOptionPane.showConfirmDialog(parent, panel, 
                "Create New Robot",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        

        if(result==JOptionPane.OK_OPTION) {
            id = idField.getText().trim();
            String xText = xField.getText().trim();
            String yText = yField.getText().trim();
            String energieText = energieField.getText().trim();
            String heuresText = heuresField.getText().trim();


            try {
                x = Integer.parseInt(xText);
                y = Integer.parseInt(yText);
                energie = Integer.parseInt(energieText);
                heureUtilisation = Integer.parseInt(heuresText);

                return new Object[] {id, x, y, energie, heureUtilisation};
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Vous devez entrer des nombres!");
                return null;
            }

        } else {
            return null;
        }
        
    }

}