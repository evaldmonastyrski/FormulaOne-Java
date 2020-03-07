package controller;

import controller.combinator.Sorter;
import gui.GuiMain;
import gui.RegularCombinationsDialog;
import gui.RiskCombinationsDialog;
import gui.setuppanel.CompetitionType;
import model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;

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
        initializeLabels(dataProvider.getDreamTeamComponents());
    }

    public void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        guiMain.closeGui();
        reloadHandler.reload();
    }

    public void onGPIndexChanged(int gpIndex) {
        dataProvider.combine();
        initializeLabels(dataProvider.getDreamTeamComponents(gpIndex));
        initializePointsAndPrices();
        guiMain.getSimulationTab().getSetupPanel().flushComboBoxes();
        guiMain.getSimulationTab().getSetupPanel().activateSimulationResults(false);
    }

    public void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        Driver driver = dataProvider.getDrivers().get(driverUpdate.getIndex());
        setPosition(driverUpdate, driver);

        Team teamToUpdate = dataProvider.getTeamMap().get(driver.getTeam());
        teamToUpdate.updateTeam();
        Engine engineToUpdate = dataProvider.getEngineMap().get(driver.getEngine());
        engineToUpdate.updateEngine();

        ComponentsUpdate componentsUpdate = ImmutableComponentsUpdate.builder()
                .driverIndex(driverUpdate.getIndex())
                .driverPoints(driver.getPoints())
                .driverPriceChange(driver.getPriceChange())
                .teamIndex(dataProvider.getTeams().indexOf(teamToUpdate))
                .teamPoints(teamToUpdate.getPoints())
                .teamPriceChange(teamToUpdate.getPriceChange())
                .engineIndex(dataProvider.getEngines().indexOf(engineToUpdate))
                .enginePoints(engineToUpdate.getPoints())
                .enginePriceChange(engineToUpdate.getPriceChange())
                .build();

        getGuiMain().getSimulationTab().getSetupPanel().updateGUILabels(componentsUpdate);
    }

    public void onMinPointsChanged(int index, double points) {
        Driver driver = dataProvider.getDrivers().get(index);
        driver.setMinPoints(points);

        Team teamToUpdate = dataProvider.getTeamMap().get(driver.getTeam());
        teamToUpdate.setMinPoints();

        Engine engineToUpdate = dataProvider.getEngineMap().get(driver.getEngine());
        engineToUpdate.setMinPoints();
    }

    public void onSamplingChanged(@Nullable Integer samples) {
        int sampleNumber = 0;
        if (samples != null) {
            sampleNumber = samples;
        }

        dataProvider.updateDriversPriceOffset(sampleNumber);

        OffsetUpdate offsetUpdate = ImmutableOffsetUpdate.builder()
                .addAllDrivers(dataProvider.getDrivers())
                .addAllTeams(dataProvider.getTeams())
                .addAllEngines(dataProvider.getEngines())
                .build();

        getGuiMain().getSimulationTab().getSetupPanel().getOffsetManager().updateOffsets(offsetUpdate);
    }

    public void onSimulateButtonClicked() {
        SimulationParameters simulationParameters = guiMain.getSimulationTab().getControlPanel().getSimulationParameters();
        dataProvider.updateDreamTeams(simulationParameters.getBudget());
        dataProvider.setAvailableDreamTeams(simulationParameters);
        dataProvider.setLowRiskDreamTeams(simulationParameters);
        LOGGER.info("Number of available teams: {}", dataProvider.getAvailableDreamTeams().size());
        LOGGER.info("Number of low risk teams: {}", dataProvider.getAvailableDreamTeams().size());
    }

    public void onPointsSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPoints(dataProvider.getAvailableDreamTeams()));
    }

    public void onPriceChangeSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPriceChange(dataProvider.getAvailableDreamTeams()));
    }

    public void onPriceOffsetSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPriceOffset(dataProvider.getAvailableDreamTeams()));
    }

    public void onRiskSortClicked() {
        new RiskCombinationsDialog(Sorter.sortByMaxPriceChange(dataProvider.getLowRiskDreamTeams()));
    }

    public void onOverallSortClicked() {
        new RegularCombinationsDialog(dataProvider.getAvailableDreamTeams());
    }

    @NotNull
    private GuiMain getGuiMain() {
        return guiMain;
    }

    private void startGui(@NotNull String[] gpStages) {
        guiMain.runGui(gpStages);
    }

    private void initializePointsAndPrices() {
        for (int i = 0; i < Constants.NUMBER_OF_DRIVERS; i ++) {
            DriverUpdate driverUpdate = DriverUpdate.initialDriverUpdate(i);
            SwingUtilities.invokeLater(() -> onComboBoxPositionChanged(driverUpdate));
            SwingUtilities.invokeLater(() -> onSamplingChanged(Constants.OFFSET_STAGES));
        }
    }

    private void setPosition(@NotNull DriverUpdate driverUpdate, @NotNull Driver driver) {
        if (!driverUpdate.getIsRaceSetup()) {
            setPosition(driverUpdate.getPosition(), driverUpdate.getCompetitionType(), driver);
        } else {
            driver.setQPosition(driverUpdate.getPosition());
            driver.setRPosition(driverUpdate.getPosition());
        }
    }

    private void setPosition(int position, @NotNull CompetitionType type, @NotNull Driver driver) {
        if (type == CompetitionType.QUALIFICATION) {
            driver.setQPosition(position);
        } else {
            driver.setRPosition(position);
        }
    }

    private void initializeGUI() {
        startGui(dataProvider.getGPStages());
    }

    private void initializeLabels(@NotNull DreamTeamComponents dreamTeamComponents) {
        getGuiMain().getSimulationTab().getSetupPanel().setLabels(ImmutableDreamTeamComponents.builder()
                .drivers(dreamTeamComponents.getDrivers())
                .teams(dreamTeamComponents.getTeams())
                .engines(dreamTeamComponents.getEngines())
                .build());
    }
}
