package gui;

import controller.GuiViewController;
import gui.handlers.ControlPanelHandler;
import gui.handlers.SetupComboBoxHandler;
import gui.handlers.SetupPanelHandler;
import model.ImmutableSimulationParameters;
import model.SimulationParameters;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

public class ControlPanel extends JPanel implements ControlPanelHandler {

    @NotNull
    private final GuiViewController guiViewController;

    @NotNull
    private final JButton reloadButton = new JButton("Reload");
    @NotNull
    private final JLabel budgetLabel = new JLabel("Budget");
    @NotNull
    private final SpinnerNumberModel budgetSpinnerModel = new SpinnerNumberModel(30.0, 0.0, 100.0, 0.1);
    @NotNull
    private final JSpinner budgetSpinner = new JSpinner(budgetSpinnerModel);
    @NotNull
    private final JLabel grandPrixLabel = new JLabel("Grand Prix");
    @NotNull
    private final JComboBox<String> grandPrixComboBox = new JComboBox<>();
    @NotNull
    private final JLabel pointsThresholdSpinnerLabel = new JLabel("Points Threshold");
    @NotNull
    private final SpinnerNumberModel pointsThresholdSpinnerModel = new SpinnerNumberModel(80, 0, 100, 1);
    @NotNull
    private final JSpinner pointsThresholdSpinner = new JSpinner(pointsThresholdSpinnerModel);
    @NotNull
    private final JCheckBox pointsThresholdCheckBox = new JCheckBox("Points Threshold", true);
    @NotNull
    private final JCheckBox raceSetupCheckBox = new JCheckBox("Race Setup");
    @NotNull
    private final JLabel samplesNumberLabel = new JLabel("Max Samples");
    @NotNull
    private final SpinnerNumberModel samplesSpinnerModel = new SpinnerNumberModel(7, 1, 21, 1);
    @NotNull
    private final JSpinner samplesSpinner = new JSpinner(samplesSpinnerModel);

    @NotNull
    private final SetupComboBoxHandler setupComboBoxHandler;

    ControlPanel(@NotNull GuiViewController guiViewController,
                 @NotNull SetupComboBoxHandler setupComboBoxHandler) {
        super(new FlowLayout());

        this.guiViewController = guiViewController;
        this.setupComboBoxHandler = setupComboBoxHandler;
    }

    void init(@NotNull String[] gpStages, @NotNull SetupPanelHandler setupPanelHandler) {
        pointsThresholdSpinner.setValue(80);

        grandPrixComboBox.setMaximumRowCount(gpStages.length);
        SwingUtilities.invokeLater(() -> {
            setGrandPrixComboBox(gpStages);
            grandPrixComboBox.setSelectedIndex(gpStages.length - 1);
        });

        reloadButton.addActionListener(e -> guiViewController.onReloadButtonClicked());
        grandPrixComboBox.addActionListener(e -> guiViewController.onGPIndexChanged(grandPrixComboBox.getSelectedIndex()));
        raceSetupCheckBox.addActionListener(e ->
                setupComboBoxHandler.onRaceSetupStateChanged(raceSetupCheckBox.isSelected()));
        budgetSpinner.addChangeListener(e -> setupPanelHandler.activateSimulationResults(false));
        samplesSpinner.addChangeListener(e -> {
            guiViewController.onSamplingChanged((Integer) samplesSpinner.getValue());
            setupPanelHandler.activateSimulationResults(false);
        });
        pointsThresholdSpinner.addChangeListener(e -> setupPanelHandler.activateSimulationResults(false));
        pointsThresholdCheckBox.addActionListener(e -> setupPanelHandler.activateSimulationResults(false));
        raceSetupCheckBox.addActionListener(e -> setupPanelHandler.activateSimulationResults(false));

        this.add(reloadButton);
        this.add(budgetLabel);
        this.add(budgetSpinner);
        this.add(grandPrixLabel);
        this.add(grandPrixComboBox);
        this.add(samplesNumberLabel);
        this.add(samplesSpinner);
        this.add(pointsThresholdSpinnerLabel);
        this.add(pointsThresholdSpinner);
        this.add(pointsThresholdCheckBox);
        this.add(raceSetupCheckBox);
    }

    @NotNull
    public SimulationParameters getSimulationParameters() {
        return ImmutableSimulationParameters.builder()
                .budget((Double) budgetSpinner.getValue())
                .pointsThreshold((Integer) pointsThresholdSpinner.getValue())
                .usePointsThreshold(pointsThresholdCheckBox.isSelected())
                .build();
    }

    @Override
    public boolean isRaceSetup() {
        return raceSetupCheckBox.isSelected();
    }

    private void setGrandPrixComboBox(String[] gpStages) {
        for (String gpStage : gpStages) {
            grandPrixComboBox.addItem(gpStage);
        }
    }
}
