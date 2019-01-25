package gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;

class ControlPanel extends JPanel {

    ControlPanel() {
        super(new FlowLayout());

        SpinnerNumberModel budgetSpinnerModel =new SpinnerNumberModel(30.0, 0.0, 100.0, 0.1);
        SpinnerNumberModel pointsThresholdSpinnerModel =new SpinnerNumberModel(80, 0, 100, 1);

        JButton reloadButton = new JButton("Reload");
        JLabel budgetLabel = new JLabel("Budget");
        JSpinner budgetSpinner = new JSpinner(budgetSpinnerModel);
        JLabel grandPrixLabel = new JLabel("Grand Prix");
        JComboBox<String> grandPrixComboBox = new JComboBox<>();
        JLabel pointsThresholdSpinnerLabel = new JLabel("Points Threshold");
        JSpinner pointsThresholdSpinner = new JSpinner(pointsThresholdSpinnerModel);
        JCheckBox pointsThresholdCheckBox = new JCheckBox("Points Threshold", true);
        JCheckBox raceSetupCheckBox = new JCheckBox("Race Setup");

        grandPrixComboBox.addItem("Australia");
        pointsThresholdSpinner.setValue(80);

        this.add(reloadButton);
        this.add(budgetLabel);
        this.add(budgetSpinner);
        this.add(grandPrixLabel);
        this.add(grandPrixComboBox);
        this.add(pointsThresholdSpinnerLabel);
        this.add(pointsThresholdSpinner);
        this.add(pointsThresholdCheckBox);
        this.add(raceSetupCheckBox);
    }
}
