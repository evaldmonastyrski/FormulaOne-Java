package controller;

import gui.CombinationsDialog;
import gui.GuiMain;
import model.ComponentsUpdate;
import model.DreamTeam;
import model.DreamTeamComponents;
import model.DriverUpdate;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    }

    public void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        controller.onComboBoxPositionChanged(driverUpdate);
    }

    public void onSimulateButtonClicked() {
        List<DreamTeam> dreamTeams = controller.onSimulateButtonClicked(guiMain.getSimulationParameters());
        new CombinationsDialog(dreamTeams);
    }

    public void onRaceSetupStateChanged(boolean isSelected) {
        guiMain.raceSetup(isSelected);
    }

    public boolean isRaceSetup() {
        return guiMain.isRaceSetup();
    }

    void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate) {
        guiMain.updateGUILabels(componentsUpdate);
    }

    void startGui(@NotNull String[] gpStages) {
        guiMain.runGui(gpStages);
    }

    void initializeLabels(@NotNull DreamTeamComponents components) {
        guiMain.initializeLabels(components);
    }
}
