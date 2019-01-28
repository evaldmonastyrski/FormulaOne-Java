package gui;

import controller.GuiController;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class SimulationTab extends JPanel {

    @NotNull private final ControlPanel controlPanel;
    @NotNull private final SetupPanel setupPanel;

    SimulationTab(@NotNull GuiController guiController) {
        super(new BorderLayout());
        controlPanel = new ControlPanel(guiController);
        setupPanel = new SetupPanel();
    }

    void init(@NotNull String[] gpStages) {
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(setupPanel, BorderLayout.CENTER);
        controlPanel.init(gpStages);
        setupPanel.init();
    }
}
