package gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class SimulationTab extends JPanel {

    @NotNull private final ControlPanel controlPanel;

    SimulationTab() {
        controlPanel = new ControlPanel();
    }

    void init() {
        this.add(controlPanel, BorderLayout.NORTH);
        controlPanel.init();
    }
}
