package controller;

import gui.CombinationsDialog;
import gui.GuiMain;
import model.ComponentsUpdate;
import model.DreamTeamComponents;
import model.DriverUpdate;
import model.OffsetUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiController {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiController.class);

    @NotNull private final Controller controller;
    @NotNull private final GuiMain guiMain;

    GuiController(@NotNull Controller controller) {
        this.controller = controller;
        this.guiMain = new GuiMain(this);
    }

    public void onReloadButtonClicked() {
        LOGGER.info("Reload button clicked");
        controller.onReloadButtonClicked();
        guiMain.closeGui();
    }

    public void onGPIndexChanged(int gpIndex) {
        LOGGER.info("GP stage changed");
        controller.onGPIndexChanged(gpIndex);
        guiMain.flushComboBoxes();
        guiMain.disableSimulationResults();
    }

    public void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        controller.onComboBoxPositionChanged(driverUpdate);
    }

    public void onSimulateButtonClicked() {
        controller.onSimulateButtonClicked(guiMain.getSimulationParameters());
    }

    public void disableSimulationResults() {
        guiMain.disableSimulationResults();
    }

    public void onRaceSetupStateChanged(boolean isSelected) {
        guiMain.raceSetup(isSelected);
    }

    public boolean isRaceSetup() {
        return guiMain.isRaceSetup();
    }

    public void onPointsSortClicked() {
        new CombinationsDialog(controller.getSortedByPointsList());
    }

    public void onPriceChangeSortClicked() {
        new CombinationsDialog(controller.getSortedByPriceChangeList());
    }

    public void onPriceOffsetSortClicked() {
        new CombinationsDialog(controller.getSortedByPriceOffset());
    }

    public void onSamplesNumChanged(@Nullable Integer samples) {
        int intSamples = 0;
        if (samples != null) {
            intSamples = samples;
        }
        controller.onSamplingChanged(intSamples);
    }

    void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate) {
        guiMain.updateGUILabels(componentsUpdate);
    }

    void updateOffsets(@NotNull OffsetUpdate offsetUpdate) {
        guiMain.updateOffsets(offsetUpdate);
    }

    void startGui(@NotNull String[] gpStages) {
        guiMain.runGui(gpStages);
    }

    void initializeLabels(@NotNull DreamTeamComponents components) {
        guiMain.initializeLabels(components);
    }
}
