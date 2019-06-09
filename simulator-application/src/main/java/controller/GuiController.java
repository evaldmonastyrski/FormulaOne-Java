package controller;

import gui.RegularCombinationsDialog;
import gui.GuiMain;
import gui.RiskCombinationsDialog;
import org.jetbrains.annotations.NotNull;

public class GuiController {

    @NotNull private final Controller controller;
    @NotNull private final GuiMain guiMain;

    GuiController(@NotNull Controller controller) {
        this.controller = controller;
        this.guiMain = new GuiMain(this);
    }

    @NotNull
    public Controller getController() {
        return controller;
    }

    public void onReloadButtonClicked() {
        controller.onReloadButtonClicked();
        guiMain.closeGui();
    }

    public void onGPIndexChanged(int gpIndex) {
        controller.onGPIndexChanged(gpIndex);
        guiMain.getSimulationTab().getSetupPanel().flushComboBoxes();
        guiMain.getSimulationTab().getSetupPanel().activateSimulationResults(false);
    }

    public void onSimulateButtonClicked() {
        controller.onSimulateButtonClicked(guiMain.getSimulationTab().getControlPanel().getSimulationParameters());
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

    public void onOverallSortClicked() {
        new RegularCombinationsDialog(controller.getSortedByOverall());
    }

    @NotNull
    GuiMain getGuiMain() {
        return guiMain;
    }

    void startGui(@NotNull String[] gpStages) {
        guiMain.runGui(gpStages);
    }
}
