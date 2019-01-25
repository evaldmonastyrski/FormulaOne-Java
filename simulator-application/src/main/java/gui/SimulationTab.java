package gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;

class SimulationTab extends JPanel {

    SimulationTab() {
        ControlPanel controlPanel = new ControlPanel();
        this.add(controlPanel, BorderLayout.NORTH);
    }
}
