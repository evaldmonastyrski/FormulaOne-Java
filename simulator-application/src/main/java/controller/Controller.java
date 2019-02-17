package controller;

import controller.deserializer.DeserializedDataContainer;
import gui.setuppanel.CompetitionType;
import model.Driver;
import model.DriverUpdate;
import model.ImmutableDreamTeamComponents;
import model.ImmutableDriverUpdate;
import model.Team;
import model.Engine;
import model.ComponentsUpdate;
import model.ImmutableComponentsUpdate;
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
        initializeLabels();
        initializePointsAndPrices();
    }

    void onComboBoxPositionChanged(@NotNull DriverUpdate driverUpdate) {
        Driver myDriver = drivers.get(driverUpdate.getIndex());

        if (!driverUpdate.getIsRaceSetup()) {
            setPosition(driverUpdate.getPosition(), driverUpdate.getCompetitionType(), myDriver);
        } else {
            myDriver.setQPosition(driverUpdate.getPosition());
            myDriver.setRPosition(driverUpdate.getPosition());
        }

        Team teamToUpdate = componentsCreator.getTeamMap().get(myDriver.getTeam());
        teamToUpdate.updateTeam();
        Engine engineToUpdate = componentsCreator.getEngineMap().get(myDriver.getEngine());
        engineToUpdate.updateEngine();

        ComponentsUpdate componentsUpdate = ImmutableComponentsUpdate.builder()
                .driverIndex(driverUpdate.getIndex())
                .driverPoints(myDriver.getPoints())
                .driverPriceChange(myDriver.getPriceChange())
                .teamIndex(teams.indexOf(teamToUpdate))
                .teamPoints(teamToUpdate.getPoints())
                .teamPriceChange(teamToUpdate.getPriceChange())
                .engineIndex(engines.indexOf(engineToUpdate))
                .enginePoints(engineToUpdate.getPoints())
                .enginePriceChange(engineToUpdate.getPriceChange())
                .build();

        guiController.updateGUILabels(componentsUpdate);
    }

    private void initializePointsAndPrices() {
        for (int i = 0; i < Constants.NUMBER_OF_DRIVERS; i ++) {
            DriverUpdate driverUpdate = ImmutableDriverUpdate.builder()
                    .index(i)
                    .position(0)
                    .competitionType(CompetitionType.QUALIFICATION)
                    .isRaceSetup(false)
                    .build();

            SwingUtilities.invokeLater(() -> onComboBoxPositionChanged(driverUpdate));
        }
    }

    private void setPosition(int position, @NotNull CompetitionType type, @NotNull Driver myDriver) {
        if (type == CompetitionType.QUALIFICATION) {
            myDriver.setQPosition(position);
        } else {
            myDriver.setRPosition(position);
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
