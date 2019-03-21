package controller;

import controller.combinator.Combinator;
import controller.combinator.Sorter;
import controller.deserializer.DeserializedDataContainer;
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
import model.DreamTeam;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final DeserializedDataContainer componentsCreator;
    @NotNull private final Combinator combinator = new Combinator();

    @NotNull private final List<Driver> drivers = new ArrayList<>();
    @NotNull private final List<Team> teams = new ArrayList<>();
    @NotNull private final List<Engine> engines = new ArrayList<>();

    Controller() {
        guiController = new GuiController(this);
        componentsCreator = new DeserializedDataContainer(drivers, teams, engines);
        initializeGUI();
        initializeLabels();
    }

    void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        new Controller();
    }

    void onGPIndexChanged(int gpIndex) {
        LOGGER.info("GP Index: {}", gpIndex);
        componentsCreator.createDreamTeamComponents(gpIndex);
        combinator.combine(drivers, teams, engines);
        initializeLabels();
        initializePointsAndPrices();
    }

    void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
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

        guiController.updateGUILabels(componentsUpdate);
    }

    void onSamplingChanged(int sampleNumber) {
        componentsCreator.updateDriversPriceOffset(sampleNumber);

        OffsetUpdate offsetUpdate = ImmutableOffsetUpdate.builder()
                .addAllDrivers(drivers)
                .addAllTeams(teams)
                .addAllEngines(engines)
                .build();

        guiController.updateOffsets(offsetUpdate);
    }

    void onSimulateButtonClicked(@NotNull SimulationParameters simulationParameters) {
        combinator.updateDreamTeams(simulationParameters.getBudget());
        combinator.setAvailableDreamTeams(simulationParameters);
        combinator.setLowRiskDreamTeams(simulationParameters);
        LOGGER.info("Number of available teams: {}", combinator.getAvailableDreamTeams().size());
        LOGGER.info("Number of low risk teams: {}", combinator.getAvailableDreamTeams().size());
    }

    @NotNull
    List<DreamTeam> getSortedByPointsList() {
        return Sorter.sortByPoints(combinator.getAvailableDreamTeams());
    }

    @NotNull
    List<DreamTeam> getSortedByPriceChangeList() {
        return Sorter.sortByPriceChange(combinator.getAvailableDreamTeams());
    }

    @NotNull
    List<DreamTeam> getSortedByPriceOffset() {
        return Sorter.sortByPriceOffset(combinator.getAvailableDreamTeams());
    }

    @NotNull
    List<DreamTeam> getSortedByRisk() {
        return Sorter.sortByMaxPriceChange(combinator.getLowRiskDreamTeams());
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
        guiController.startGui(componentsCreator.getGPStages());
    }

    private void initializeLabels() {
        guiController.initializeLabels(ImmutableDreamTeamComponents.builder()
                .drivers(drivers)
                .teams(teams)
                .engines(engines)
                .build());
    }
}
