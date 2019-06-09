package gui.handlers;

import model.DriverUpdate;
import model.SimulationParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ControllerHandler {

    void onReloadButtonClicked();

    void onGPIndexChanged(int gpIndex);

    void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate);

    void onMinPointsChanged(int index, double points);

    void onSamplingChanged(@Nullable Integer samples);

    void onSimulateButtonClicked(@NotNull SimulationParameters simulationParameters);
}
