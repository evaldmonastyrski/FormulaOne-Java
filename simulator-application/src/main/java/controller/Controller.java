package controller;

import controller.deserializer.BaseDriver;
import controller.deserializer.BaseTeam;
import controller.deserializer.Deserializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final Deserializer deserializer;

    @NotNull private final List<BaseDriver> baseDrivers;
    @NotNull private final List<BaseTeam> baseTeams;

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

    private void initialize() {
        guiController.startGui(deserializer.getGPStages());
    }
}
