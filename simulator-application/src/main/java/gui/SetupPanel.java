package gui;

import controller.Constants;
import controller.GuiController;
import gui.setuppanel.SetupComboBoxManager;
import gui.setuppanel.SetupLabelManager;
import gui.setuppanel.SetupOffsetManager;
import gui.setuppanel.SetupPointManager;
import gui.setuppanel.SetupPriceManager;
import model.ComponentsUpdate;
import model.DreamTeamComponents;
import model.DriverUpdate;
import model.OffsetUpdate;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SetupPanel extends JPanel {

    private static final int COMPONENTS_START_ROW = 0;
    private static final int ENGINE_ROW_OFFSET = engineOffset();

    @NotNull private final GuiController guiController;

    @NotNull private final GridBagConstraints constraints = new GridBagConstraints();
    @NotNull private final SetupLabelManager labelManager = new SetupLabelManager(this, constraints);
    @NotNull private final SetupComboBoxManager comboBoxManager = new SetupComboBoxManager(this, constraints);
    @NotNull private final SetupPointManager pointManager = new SetupPointManager(this, constraints);
    @NotNull private final SetupPriceManager priceManager = new SetupPriceManager(this, constraints);
    @NotNull private final SetupOffsetManager offsetManager = new SetupOffsetManager(this, constraints);

    @NotNull private final JButton simulateButton = new JButton("Simulate");
    @NotNull private final JButton flushQButton = new JButton("Flush Q");
    @NotNull private final JButton flushRButton = new JButton("Flush R");
    @NotNull private final JButton pointSortButton = new JButton("Point Sort");
    @NotNull private final JButton priceChangeSortButton = new JButton("Price Sort");
    @NotNull private final JButton priceOffsetSortButton = new JButton("Offset Sort");
    @NotNull private final JButton riskSortButton = new JButton("Risk Sort");

    SetupPanel(@NotNull GuiController guiController) {
        super(new GridBagLayout());
        this.guiController = guiController;
    }

    void init() {
        constraints.weightx = 1;
        labelManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        comboBoxManager.init();
        pointManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        priceManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        offsetManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        initializeSimulationButtons(constraints);
        activateSimulationResults(false);
    }

    public void updateDriver(@NotNull DriverUpdate driverUpdate) {
        guiController.onComboBoxPositionChanged(driverUpdate);
        activateSimulationResults(false);
    }

    void setLabels(@NotNull DreamTeamComponents components) {
        labelManager.setDriverLabels(components.getDrivers());
        labelManager.setTeamLabels(components.getTeams());
        labelManager.setEngineLabels(components.getEngines());
    }

    void flushComboBoxes() {
        comboBoxManager.flushQualificationComboBoxes();
        comboBoxManager.flushRaceComboBoxes();
    }

    void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate) {
        pointManager.updatePoints(componentsUpdate);
        priceManager.updatePriceChange(componentsUpdate);
    }

    void updateOffsets(@NotNull OffsetUpdate offsetUpdate) {
        offsetManager.updateOffsets(offsetUpdate);
    }

    void raceSetup(boolean isSelected) {
        comboBoxManager.raceSetup(isSelected);
    }

    void activateSimulationResults(boolean enable) {
        pointSortButton.setEnabled(enable);
        priceChangeSortButton.setEnabled(enable);
        priceOffsetSortButton.setEnabled(enable);
        riskSortButton.setEnabled(enable);
    }

    public boolean isRaceSetup() {
        return guiController.isRaceSetup();
    }

    private void initializeSimulationButtons(@NotNull GridBagConstraints constraints) {
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = Constants.NUMBER_OF_DRIVERS + 1;
        constraints.insets = new Insets(15, 0, 0, 0);

        simulateButton.addActionListener(e -> {
            guiController.onSimulateButtonClicked();
            activateSimulationResults(true);
        });
        flushQButton.addActionListener(e -> comboBoxManager.flushQualificationComboBoxes());
        flushRButton.addActionListener(e -> comboBoxManager.flushRaceComboBoxes());
        pointSortButton.addActionListener(e -> guiController.onPointsSortClicked());
        priceChangeSortButton.addActionListener(e -> guiController.onPriceChangeSortClicked());
        priceOffsetSortButton.addActionListener(e -> guiController.onPriceOffsetSortClicked());
        riskSortButton.addActionListener(e -> guiController.onRiskSortClicked());

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
        this.add(priceOffsetSortButton, constraints);
        constraints.gridx = 6;
        this.add(riskSortButton, constraints);
    }

    private static int engineOffset() {
        int freeSpaces = Constants.NUMBER_OF_DRIVERS - Constants.NUMBER_OF_TEAMS;
        int offset = freeSpaces / 2 - Constants.NUMBER_OF_ENGINES / 2;
        return Constants.NUMBER_OF_TEAMS + offset;
    }
}
