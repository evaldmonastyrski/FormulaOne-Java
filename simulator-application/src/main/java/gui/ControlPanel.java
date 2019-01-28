package gui;

import controller.GuiController;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;

class ControlPanel extends JPanel {

    @NotNull private final GuiController guiController;
    @NotNull private final JButton reloadButton;
    @NotNull private final JLabel budgetLabel;
    @NotNull private final JSpinner budgetSpinner;
    @NotNull private final JLabel grandPrixLabel;
    @NotNull private final JComboBox<String> grandPrixComboBox;
    @NotNull private final JLabel pointsThresholdSpinnerLabel;
    @NotNull private final JSpinner pointsThresholdSpinner;
    @NotNull private final JCheckBox pointsThresholdCheckBox;
    @NotNull private final JCheckBox raceSetupCheckBox;

    ControlPanel(@NotNull GuiController guiController) {
        super(new FlowLayout());

        this.guiController = guiController;

        SpinnerNumberModel budgetSpinnerModel = new SpinnerNumberModel(30.0, 0.0, 100.0, 0.1);
        SpinnerNumberModel pointsThresholdSpinnerModel = new SpinnerNumberModel(80, 0, 100, 1);

        reloadButton = new JButton("Reload");
        budgetLabel = new JLabel("Budget");
        budgetSpinner = new JSpinner(budgetSpinnerModel);
        grandPrixLabel = new JLabel("Grand Prix");
        grandPrixComboBox = new JComboBox<>();
        pointsThresholdSpinnerLabel = new JLabel("Points Threshold");
        pointsThresholdSpinner = new JSpinner(pointsThresholdSpinnerModel);
        pointsThresholdCheckBox = new JCheckBox("Points Threshold", true);
        raceSetupCheckBox = new JCheckBox("Race Setup");
    }

    void init(@NotNull String[] gpStages) {
        pointsThresholdSpinner.setValue(80);

        grandPrixComboBox.setMaximumRowCount(gpStages.length);
        setGrandPrixComboBox(gpStages);

        reloadButton.addActionListener(e -> guiController.onReloadButtonClicked());

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

    private void setGrandPrixComboBox(String[] gpStages) {
        for (String gpStage : gpStages) {
            grandPrixComboBox.addItem(gpStage);
        }
    }
}
