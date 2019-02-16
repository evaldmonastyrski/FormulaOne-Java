package controller;

import gui.GuiMain;
import gui.setuppanel.CompetitionType;
import model.ComponentsUpdate;
import model.DreamTeamComponents;
import org.jetbrains.annotations.NotNull;
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
    }

    public void onComboBoxPositionChanged(int cacheIndex,
                                          int position,
                                          @NotNull CompetitionType type,
                                          boolean isRaceSetup) {
        controller.onComboBoxPositionChanged(cacheIndex, position, type, isRaceSetup);
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
