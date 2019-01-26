package controller;

import controller.deserializer.Deserializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Controller {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @NotNull private final GuiController guiController;
    @NotNull private final Deserializer deserializer;

    Controller() {
        guiController = new GuiController(this);
        deserializer = new Deserializer();
        initialize();
    }

    void onReloadButtonClicked() {
        LOGGER.info("Simulator is restarting");
        new Controller();
    }

    private void initialize() {
        guiController.startGui();
        deserializer.readTextFile();
    }
}
