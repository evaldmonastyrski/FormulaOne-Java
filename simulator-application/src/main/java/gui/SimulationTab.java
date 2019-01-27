package gui;

import controller.GuiController;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class SimulationTab extends JPanel {

    @NotNull private final ControlPanel controlPanel;

    SimulationTab(@NotNull GuiController guiController) {
        controlPanel = new ControlPanel(guiController);
    }

    void init(@NotNull String[] gpStages) {
        this.add(controlPanel, BorderLayout.NORTH);
        controlPanel.init(gpStages);
    }
}
