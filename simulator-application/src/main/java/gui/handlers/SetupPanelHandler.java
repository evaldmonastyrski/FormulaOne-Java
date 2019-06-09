package gui.handlers;

import model.ComponentsUpdate;
import org.jetbrains.annotations.NotNull;

public interface SetupPanelHandler {

    void activateSimulationResults(boolean enable);

    void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate);
}
