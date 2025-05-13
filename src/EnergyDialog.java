import javax.swing.*;

public class EnergyDialog {

    public static Integer showEnergyDialog(JFrame parent, int currentEnergy) {
        SpinnerNumberModel model = new SpinnerNumberModel(currentEnergy + 1, 0, 100, 1);
        JSpinner spinner = new JSpinner(model);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Votre énergie actuelle: " + currentEnergy + ". Entrer la nouvelle énergie souhaitée:"));
        panel.add(spinner);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Mettre à jour l'énergie",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (Integer) spinner.getValue();
        } else {
            return null;  
        }
    }
}
