package gui;

import controller.DreamTeamComponents;
import controller.GuiController;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class SimulationTab extends JPanel {

    @NotNull private final ControlPanel controlPanel;
    @NotNull private final SetupPanel setupPanel;

    SimulationTab(@NotNull GuiController guiController) {
        super(new BorderLayout());
        setupPanel = new SetupPanel();
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
}
