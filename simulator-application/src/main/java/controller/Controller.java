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
import java.util.Set;
import java.util.TreeSet;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final DeserializedDataContainer componentsCreator;

    @NotNull private final Set<Driver> driverSet = new TreeSet<>();
    @NotNull private final Set<Team> teamSet = new TreeSet<>();
    @NotNull private final Map<String, Team> teamMap = new HashMap<>();
    @NotNull private final Set<Engine> engineSet = new TreeSet<>();
    @NotNull private final Map<String, Engine> engineMap = new HashMap<>();

    @NotNull private List<Driver> drivers;
    @NotNull private List<Team> teams;
    @NotNull private List<Engine> engines;

    Controller() {
        guiController = new GuiController(this);
        componentsCreator = new DeserializedDataContainer(driverSet, teamSet, teamMap, engineSet, engineMap);
        initializeGUI();
        LOGGER.info("Number of driverSet: {}", driverSet.size());
        initializeLabels();
        drivers = new ArrayList<>(driverSet);
        teams = new ArrayList<>(teamSet);
        engines = new ArrayList<>(engineSet);
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
        drivers = new ArrayList<>(driverSet);
        teams = new ArrayList<>(teamSet);
        engines = new ArrayList<>(engineSet);
    }

    private void initializePointsAndPrices() {
        for (int i = 0; i < Constants.NUMBER_OF_DRIVERS; i ++) {
            final int index = i;
            SwingUtilities.invokeLater(() -> onComboBoxPositionChanged(index, 0, CompetitionType.QUALIFICATION));
        }
    }

    void onComboBoxPositionChanged(int cacheIndex, int position, @NotNull CompetitionType type) {
        Driver myDriver = drivers.get(cacheIndex);

        if (type == CompetitionType.QUALIFICATION) {
            myDriver.setQPosition(position);
        } else {
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
                .drivers(driverSet)
                .teams(teamSet)
                .build());
    }
}
