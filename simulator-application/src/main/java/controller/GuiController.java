package controller;

import gui.RegularCombinationsDialog;
import gui.GuiMain;
import gui.RiskCombinationsDialog;
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
        new RegularCombinationsDialog(controller.getSortedByPointsList());
    }

    public void onPriceChangeSortClicked() {
        new RegularCombinationsDialog(controller.getSortedByPriceChangeList());
    }

    public void onPriceOffsetSortClicked() {
        new RegularCombinationsDialog(controller.getSortedByPriceOffset());
    }

    public void onRiskSortClicked() {
        new RiskCombinationsDialog(controller.getSortedByRisk());
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
