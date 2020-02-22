package controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Main {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(@NotNull String[] args) {
        LOGGER.info("Formula 1 Simulator is starting up...");
        new GuiViewController();
    }
}
