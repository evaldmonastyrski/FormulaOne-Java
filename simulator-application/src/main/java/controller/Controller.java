package controller;

import controller.deserializer.BaseDriver;
import controller.deserializer.BaseTeam;
import controller.deserializer.Deserializer;
import model.Driver;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final Deserializer deserializer;

    @NotNull private final List<BaseDriver> baseDrivers;
    @NotNull private final List<BaseTeam> baseTeams;

    @NotNull private final Set<Driver> drivers = new TreeSet<>();

    Controller() {
        guiController = new GuiController(this);
        deserializer = new Deserializer();
        baseDrivers = deserializer.getDrivers();
        baseTeams = deserializer.getTeams();
        initialize();
    }

    void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        new Controller();
    }

    void onGPIndexChanged(int gpIndex) {
        LOGGER.info("GP Index: {}", gpIndex);
        initializeDrivers(gpIndex);
    }

    private void initialize() {
        guiController.startGui(deserializer.getGPStages());
    }

    private void initializeDrivers(int gpStage) {
        drivers.clear();
        for (BaseDriver driver : baseDrivers) {
            drivers.add(new Driver(driver, gpStage));
        }
        initializeLabels();
    }

    private void initializeLabels() {
        guiController.initializeLabels(drivers);
    }
}
