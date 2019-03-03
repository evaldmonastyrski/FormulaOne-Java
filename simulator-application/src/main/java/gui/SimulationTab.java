package gui;

import model.ComponentsUpdate;
import model.DreamTeamComponents;
import controller.GuiController;
import model.OffsetUpdate;
import model.SimulationParameters;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class SimulationTab extends JPanel {

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final ControlPanel controlPanel;

    SimulationTab(@NotNull GuiController guiController) {
        super(new BorderLayout());
        setupPanel = new SetupPanel(guiController);
        controlPanel = new ControlPanel(guiController);
    }

    void init(@NotNull String[] gpStages) {
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(setupPanel, BorderLayout.CENTER);
        setupPanel.init();
        controlPanel.init(gpStages);
    }

    void setLabels(@NotNull DreamTeamComponents components) {
        setupPanel.setLabels(components);
    }

    void flushComboBoxes() {
        setupPanel.flushComboBoxes();
    }

    void raceSetup(boolean isSelected) {
        setupPanel.raceSetup(isSelected);
    }

    boolean isRaceSetup() {
        return controlPanel.isRaceSetup();
    }

    void disableSimulationResults() {
        setupPanel.activateSimulationResults(false);
    }

    @NotNull SimulationParameters getSimulationParameters() {
        return controlPanel.getSimulationParameters();
    }

    void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate) {
        setupPanel.updateGUILabels(componentsUpdate);
    }

    void updateOffsets(@NotNull OffsetUpdate offsetUpdate) {
        setupPanel.updateOffsets(offsetUpdate);
    }
}
