package gui;

import controller.Constants;
import controller.GuiViewController;
import gui.handlers.ControlPanelHandler;
import gui.handlers.SetupPanelHandler;
import gui.setuppanel.SetupComboBoxManager;
import gui.setuppanel.SetupLabelManager;
import gui.setuppanel.SetupOffsetManager;
import gui.setuppanel.SetupPointManager;
import gui.setuppanel.SetupPriceManager;
import gui.setuppanel.SetupRiskManager;
import model.ComponentsUpdate;
import model.DreamTeamComponents;
import model.DriverUpdate;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SetupPanel extends JPanel implements SetupPanelHandler {

    private static final int COMPONENTS_START_ROW = 0;
    private static final int ENGINE_ROW_OFFSET = engineOffset();

    @NotNull private final GuiViewController guiViewController;

    @NotNull private final GridBagConstraints constraints = new GridBagConstraints();
    @NotNull private final SetupLabelManager labelManager = new SetupLabelManager(this, constraints);
    @NotNull private final SetupComboBoxManager comboBoxManager;
    @NotNull private final SetupPointManager pointManager = new SetupPointManager(this, constraints);
    @NotNull private final SetupPriceManager priceManager = new SetupPriceManager(this, constraints);
    @NotNull private final SetupRiskManager riskManager = new SetupRiskManager(this, constraints);
    @NotNull private final SetupOffsetManager offsetManager = new SetupOffsetManager(this, constraints);

    @NotNull private final JButton simulateButton = new JButton("     Simulate     ");
    @NotNull private final JButton flushQButton = new JButton("Flush Q");
    @NotNull private final JButton flushRButton = new JButton("Flush R");
    @NotNull private final JButton pointSortButton = new JButton(" Points ");
    @NotNull private final JButton priceChangeSortButton = new JButton(" Prices ");
    @NotNull private final JButton riskSortButton = new JButton("  Risk  ");
    @NotNull private final JButton priceOffsetSortButton = new JButton(" Offset ");
    @NotNull private final JButton overallSortButton = new JButton("Overall");

    SetupPanel(@NotNull GuiViewController guiViewController) {
        super(new GridBagLayout());
        this.guiViewController = guiViewController;
        comboBoxManager = new SetupComboBoxManager(this, constraints);
    }

    @NotNull
    SetupComboBoxManager getComboBoxManager() {
        return comboBoxManager;
    }

    void init(@NotNull ControlPanelHandler controlPanelHandler) {
        constraints.weightx = 1;
        labelManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        comboBoxManager.init(controlPanelHandler);
        pointManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        priceManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        riskManager.init(COMPONENTS_START_ROW);
        offsetManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        initializeSimulationButtons(constraints);
        activateSimulationResults(false);
    }

    public void updateDriver(@NotNull DriverUpdate driverUpdate) {
        guiViewController.onComboBoxPositionChanged(driverUpdate);
        activateSimulationResults(false);
    }

    public void updateDriverMinPoints(int index, double points) {
        guiViewController.onMinPointsChanged(index, points);
        activateSimulationResults(false);
    }

    public void setLabels(@NotNull DreamTeamComponents components) {
        labelManager.setDriverLabels(components.getDrivers());
        labelManager.setTeamLabels(components.getTeams());
        labelManager.setEngineLabels(components.getEngines());
        riskManager.setMinimumPoints(components.getDrivers());
    }

    public void flushComboBoxes() {
        comboBoxManager.flushQualificationComboBoxes();
        comboBoxManager.flushRaceComboBoxes();
    }

    @Override
    public void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate) {
        pointManager.updatePoints(componentsUpdate);
        priceManager.updatePriceChange(componentsUpdate);
    }

    @NotNull
    public SetupOffsetManager getOffsetManager() {
        return offsetManager;
    }

    @Override
    public void activateSimulationResults(boolean enable) {
        pointSortButton.setEnabled(enable);
        priceChangeSortButton.setEnabled(enable);
        priceOffsetSortButton.setEnabled(enable);
        riskSortButton.setEnabled(enable);
        overallSortButton.setEnabled(enable);
    }

    private void initializeSimulationButtons(@NotNull GridBagConstraints constraints) {
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = Constants.NUMBER_OF_DRIVERS + 1;
        constraints.insets = new Insets(15, 0, 0, 0);

        simulateButton.addActionListener(e -> {
            guiViewController.onSimulateButtonClicked();
            activateSimulationResults(true);
        });
        flushQButton.addActionListener(e -> comboBoxManager.flushQualificationComboBoxes());
        flushRButton.addActionListener(e -> comboBoxManager.flushRaceComboBoxes());
        pointSortButton.addActionListener(e -> guiViewController.onPointsSortClicked());
        priceChangeSortButton.addActionListener(e -> guiViewController.onPriceChangeSortClicked());
        priceOffsetSortButton.addActionListener(e -> guiViewController.onPriceOffsetSortClicked());
        riskSortButton.addActionListener(e -> guiViewController.onRiskSortClicked());
        overallSortButton.addActionListener(e -> guiViewController.onOverallSortClicked());

        this.add(simulateButton, constraints);
        constraints.gridx = 1;
        this.add(flushQButton, constraints);
        constraints.gridx = 2;
        this.add(flushRButton, constraints);
        constraints.gridx = 3;
        this.add(pointSortButton, constraints);
        constraints.gridx = 4;
        this.add(priceChangeSortButton, constraints);
        constraints.gridx = 5;
        this.add(riskSortButton, constraints);
        constraints.gridx = 6;
        this.add(priceOffsetSortButton, constraints);
        constraints.gridx = 10;
        this.add(overallSortButton, constraints);
    }

    private static int engineOffset() {
        int freeSpaces = Constants.NUMBER_OF_DRIVERS - Constants.NUMBER_OF_TEAMS;
        int offset = freeSpaces / 2 - Constants.NUMBER_OF_ENGINES / 2;
        return Constants.NUMBER_OF_TEAMS + offset;
    }
}
