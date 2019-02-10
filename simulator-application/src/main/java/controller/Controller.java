package controller;

import controller.deserializer.DeserializedDataContainer;
import gui.setuppanel.CompetitionType;
import model.Driver;
import model.ImmutableDreamTeamComponents;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final DeserializedDataContainer componentsCreator;

    @NotNull private final Set<Driver> driverSet = new TreeSet<>();
    @NotNull private final Set<Team> teamSet = new TreeSet<>();

    @NotNull private List<Driver> drivers;
    @NotNull private List<Team> teams;

    Controller() {
        guiController = new GuiController(this);
        componentsCreator = new DeserializedDataContainer(driverSet, teamSet);
        initializeGUI();
        LOGGER.info("Number of driverSet: {}", driverSet.size());
        initializeLabels();
        drivers = new ArrayList<>(driverSet);
        teams = new ArrayList<>(teamSet);
    }

    void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        new Controller();
    }

    void onGPIndexChanged(int gpIndex) {
        LOGGER.info("GP Index: {}", gpIndex);
        componentsCreator.createDreamTeamComponents(gpIndex);
        initializeLabels();
        drivers = new ArrayList<>(driverSet);
        teams = new ArrayList<>(teamSet);
    }

    void onComboBoxPositionChanged(int cacheIndex, int position, @NotNull CompetitionType type) {
        Driver myDriver = drivers.get(cacheIndex);

        LOGGER.info("{}'s {} position was changed to {}", myDriver.toString(), type, position);
        if (type == CompetitionType.QUALIFICATION) {
            myDriver.setQPosition(position);
        } else {
            myDriver.setRPosition(position);
        }
        guiController.updateGUILabels();
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
