package controller;

import controller.combinator.Combinator;
import controller.combinator.Sorter;
import controller.deserializer.DeserializedDataContainer;
import gui.GuiMain;
import gui.RegularCombinationsDialog;
import gui.RiskCombinationsDialog;
import gui.setuppanel.CompetitionType;
import model.ComponentsUpdate;
import model.Driver;
import model.ImmutableComponentsUpdate;
import model.ImmutableOffsetUpdate;
import model.ImmutableDreamTeamComponents;
import model.OffsetUpdate;
import model.SimulationParameters;
import model.Team;
import model.Engine;
import model.DriverUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class GuiViewController {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiViewController.class);

    @NotNull private final DeserializedDataContainer componentsCreator;
    @NotNull private final Combinator combinator = new Combinator();
    @NotNull private final GuiMain guiMain;

    @NotNull private final List<Driver> drivers = new ArrayList<>();
    @NotNull private final List<Team> teams = new ArrayList<>();
    @NotNull private final List<Engine> engines = new ArrayList<>();

    GuiViewController() {
        this.componentsCreator = new DeserializedDataContainer(drivers, teams, engines);
        this.guiMain = new GuiMain(this);
        initializeGUI();
        initializeLabels();
    }

    public void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        new GuiViewController();
        guiMain.closeGui();
    }

    public void onGPIndexChanged(int gpIndex) {
        componentsCreator.createDreamTeamComponents(gpIndex);
        combinator.combine(drivers, teams, engines);
        initializeLabels();
        initializePointsAndPrices();
        guiMain.getSimulationTab().getSetupPanel().flushComboBoxes();
        guiMain.getSimulationTab().getSetupPanel().activateSimulationResults(false);
    }

    public void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        Driver driver = drivers.get(driverUpdate.getIndex());
        setPosition(driverUpdate, driver);

        Team teamToUpdate = componentsCreator.getTeamMap().get(driver.getTeam());
        teamToUpdate.updateTeam();
        Engine engineToUpdate = componentsCreator.getEngineMap().get(driver.getEngine());
        engineToUpdate.updateEngine();

        ComponentsUpdate componentsUpdate = ImmutableComponentsUpdate.builder()
                .driverIndex(driverUpdate.getIndex())
                .driverPoints(driver.getPoints())
                .driverPriceChange(driver.getPriceChange())
                .teamIndex(teams.indexOf(teamToUpdate))
                .teamPoints(teamToUpdate.getPoints())
                .teamPriceChange(teamToUpdate.getPriceChange())
                .engineIndex(engines.indexOf(engineToUpdate))
                .enginePoints(engineToUpdate.getPoints())
                .enginePriceChange(engineToUpdate.getPriceChange())
                .build();

        getGuiMain().getSimulationTab().getSetupPanel().updateGUILabels(componentsUpdate);
    }

    public void onMinPointsChanged(int index, double points) {
        Driver driver = drivers.get(index);
        driver.setMinPoints(points);

        Team teamToUpdate = componentsCreator.getTeamMap().get(driver.getTeam());
        teamToUpdate.setMinPoints();

        Engine engineToUpdate = componentsCreator.getEngineMap().get(driver.getEngine());
        engineToUpdate.setMinPoints();
    }

    public void onSamplingChanged(@Nullable Integer samples) {
        int sampleNumber = 0;
        if (samples != null) {
            sampleNumber = samples;
        }

        componentsCreator.updateDriversPriceOffset(sampleNumber);

        OffsetUpdate offsetUpdate = ImmutableOffsetUpdate.builder()
                .addAllDrivers(drivers)
                .addAllTeams(teams)
                .addAllEngines(engines)
                .build();

        getGuiMain().getSimulationTab().getSetupPanel().getOffsetManager().updateOffsets(offsetUpdate);
    }

    public void onSimulateButtonClicked() {
        SimulationParameters simulationParameters = guiMain.getSimulationTab().getControlPanel().getSimulationParameters();
        combinator.updateDreamTeams(simulationParameters.getBudget());
        combinator.setAvailableDreamTeams(simulationParameters);
        combinator.setLowRiskDreamTeams(simulationParameters);
        LOGGER.info("Number of available teams: {}", combinator.getAvailableDreamTeams().size());
        LOGGER.info("Number of low risk teams: {}", combinator.getAvailableDreamTeams().size());
    }

    public void onPointsSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPoints(combinator.getAvailableDreamTeams()));
    }

    public void onPriceChangeSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPriceChange(combinator.getAvailableDreamTeams()));
    }

    public void onPriceOffsetSortClicked() {
        new RegularCombinationsDialog(Sorter.sortByPriceOffset(combinator.getAvailableDreamTeams()));
    }

    public void onRiskSortClicked() {
        new RiskCombinationsDialog(Sorter.sortByMaxPriceChange(combinator.getLowRiskDreamTeams()));
    }

    public void onOverallSortClicked() {
        new RegularCombinationsDialog(combinator.getAvailableDreamTeams());
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
        startGui(componentsCreator.getGPStages());
    }

    private void initializeLabels() {
        getGuiMain().getSimulationTab().getSetupPanel().setLabels(ImmutableDreamTeamComponents.builder()
                .drivers(drivers)
                .teams(teams)
                .engines(engines)
                .build());
    }
}
