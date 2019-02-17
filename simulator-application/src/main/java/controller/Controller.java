package controller;

import controller.deserializer.DeserializedDataContainer;
import gui.setuppanel.CompetitionType;
import model.Driver;
import model.ImmutableDreamTeamComponents;
import model.Team;
import model.Engine;
import model.ComponentsUpdate;
import model.ImmutableComponentsUpdate;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final DeserializedDataContainer componentsCreator;

    @NotNull private final Map<String, Team> teamMap = new HashMap<>();
    @NotNull private final Map<String, Engine> engineMap = new HashMap<>();
    @NotNull private final List<Driver> drivers = new ArrayList<>();
    @NotNull private final List<Team> teams = new ArrayList<>();
    @NotNull private final List<Engine> engines = new ArrayList<>();

    Controller() {
        guiController = new GuiController(this);
        componentsCreator = new DeserializedDataContainer(drivers, teams, teamMap, engines, engineMap);
        initializeGUI();
        LOGGER.info("Number of driverSet: {}", drivers.size());
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

    private void initializePointsAndPrices() {
        for (int i = 0; i < Constants.NUMBER_OF_DRIVERS; i ++) {
            final int index = i;
            SwingUtilities.invokeLater(() -> onComboBoxPositionChanged(index, 0, CompetitionType.QUALIFICATION, false));
        }
    }

    void onComboBoxPositionChanged(int cacheIndex, int position, @NotNull CompetitionType type, boolean isRaceSetup) {
        Driver myDriver = drivers.get(cacheIndex);

        if (!isRaceSetup) {
            if (type == CompetitionType.QUALIFICATION) {
                myDriver.setQPosition(position);
            } else {
                myDriver.setRPosition(position);
            }
        } else {
            myDriver.setQPosition(position);
            myDriver.setRPosition(position);
        }

        Team teamToUpdate = teamMap.get(myDriver.getTeam());
        teamToUpdate.updateTeam();
        Engine engineToUpdate = engineMap.get(myDriver.getEngine());
        engineToUpdate.updateEngine();

        ComponentsUpdate componentsUpdate = ImmutableComponentsUpdate.builder()
                .driverIndex(cacheIndex)
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
