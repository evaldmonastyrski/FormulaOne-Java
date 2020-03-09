package controller;

import controller.combinator.Sorter;
import gui.GuiMain;
import gui.resultpanel.RegularCombinationsDialog;
import gui.resultpanel.RiskCombinationsDialog;
import model.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiViewController {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiViewController.class);

    @NotNull private final DataProvider dataProvider;
    @NotNull private final ReloadHandler reloadHandler;
    @NotNull private final GuiMain guiMain;

    GuiViewController(@NotNull DataProvider dataProvider, @NotNull ReloadHandler reloadHandler) {
        this.dataProvider = dataProvider;
        this.reloadHandler = reloadHandler;
        this.guiMain = new GuiMain(this);
        initializeGUI();
    }

    private void initializeGUI() {
        guiMain.initGui(dataProvider.getGPStages());
        initializeLabels(dataProvider.getDefaultDreamTeamComponents());
    }

    public void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        guiMain.closeGui();
        reloadHandler.reload();
    }

    public void onGPIndexChanged(int gpIndex) {
        initializeLabels(dataProvider.getDreamTeamComponents(gpIndex));
        initializePointsAndPrices();
        guiMain.getSimulationTab().getSetupPanel().flushComboBoxes();
        guiMain.getSimulationTab().getSetupPanel().activateSimulationResults(false);
    }

    private void initializePointsAndPrices() {
        for (int driverIndex = 0; driverIndex < Constants.NUMBER_OF_DRIVERS; driverIndex++) {
            DriverUpdate driverUpdate = DriverUpdate.defaultDriverUpdate(driverIndex);
            onComboBoxPositionChanged(driverUpdate);
        }
        onSamplingChanged(Constants.OFFSET_STAGES);
    }

    public void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        getGuiMain().getSimulationTab().getSetupPanel().updateGUILabels(dataProvider.onComboBoxPositionChanged(driverUpdate));
    }

    public void onMinPointsChanged(int index, double points) {
        dataProvider.onMinPointsChanged(index, points);
    }

    public void onSamplingChanged(int samples) {
        getGuiMain().getSimulationTab().getSetupPanel().getOffsetManager().updateOffsets(dataProvider.onSamplingChanged(samples));
    }

    public void onSimulateButtonClicked() {
        SimulationParameters simulationParameters = guiMain.getSimulationTab().getControlPanel().getSimulationParameters();
        dataProvider.applySimulationParameters(simulationParameters);
        LOGGER.info("Number of available teams: {}", dataProvider.getAvailableDreamTeams().size());
        LOGGER.info("Number of low risk teams: {}", dataProvider.getAvailableDreamTeams().size());
    }

    public void onPointsSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPoints(dataProvider.getAvailableDreamTeams()),
                dataProvider.getMaxPoints());
    }

    public void onPriceChangeSortClicked() {
        new RegularCombinationsDialog(
                Sorter.sortByPriceChange(dataProvider.getAvailableDreamTeams()), dataProvider.getMaxPoints());
    }

    public void onPriceOffsetSortClicked() {
        new RegularCombinationsDialog(
                Sorter.sortByPriceOffset(dataProvider.getAvailableDreamTeams()), dataProvider.getMaxPoints());
    }

    public void onRiskSortClicked() {
        new RiskCombinationsDialog(
                Sorter.sortByMaxPriceChange(dataProvider.getLowRiskDreamTeams()), dataProvider.getMaxPoints());
    }

    public void onOverallSortClicked() {
        new RegularCombinationsDialog(
                Sorter.sortByOverall(dataProvider.getAvailableDreamTeams()), dataProvider.getMaxPoints());
    }

    @NotNull
    private GuiMain getGuiMain() {
        return guiMain;
    }

    private void initializeLabels(@NotNull DreamTeamComponents dreamTeamComponents) {
        getGuiMain().getSimulationTab().getSetupPanel().setLabels(ImmutableDreamTeamComponents.builder()
                .drivers(dreamTeamComponents.getDrivers())
                .teams(dreamTeamComponents.getTeams())
                .engines(dreamTeamComponents.getEngines())
                .build());
    }
}
