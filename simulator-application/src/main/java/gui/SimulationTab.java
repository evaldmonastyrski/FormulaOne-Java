package gui;

import controller.GuiController;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SimulationTab extends JPanel {

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final ControlPanel controlPanel;

    SimulationTab(@NotNull GuiController guiController) {
        super(new BorderLayout());
        setupPanel = new SetupPanel(guiController, guiController.getController());
        controlPanel = new ControlPanel(guiController,
                setupPanel.getComboBoxManager(),
                guiController.getController());
    }

    void init(@NotNull String[] gpStages) {
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(setupPanel, BorderLayout.CENTER);
        setupPanel.init(controlPanel);
        controlPanel.init(gpStages, setupPanel);
    }

    @NotNull
    public SetupPanel getSetupPanel() {
        return setupPanel;
    }

    @NotNull
    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}
