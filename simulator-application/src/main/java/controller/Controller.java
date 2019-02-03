package controller;

import controller.deserializer.Deserializer;
import model.Driver;
import model.Team;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final Deserializer deserializer;
    @NotNull private final DreamTeamComponentsCreator componentsCreator;

    @NotNull private final Set<Driver> drivers = new TreeSet<>();
    @NotNull private final Set<Team> teams = new TreeSet<>();

    Controller() {
        guiController = new GuiController(this);
        deserializer = new Deserializer();
        componentsCreator = new DreamTeamComponentsCreator(deserializer.getData(), drivers, teams);
        initialize();
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
    }

    private void initialize() {
        guiController.startGui(deserializer.getGPStages());
    }

    private void initializeLabels() {
        guiController.initializeLabels(ImmutableDreamTeamComponents.builder()
                .drivers(drivers)
                .teams(teams)
                .build());
    }
}
