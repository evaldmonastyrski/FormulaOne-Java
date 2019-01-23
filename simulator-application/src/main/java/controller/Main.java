package controller;

import gui.GuiMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Main() {
        final GuiMain guiMain = new GuiMain();
        final Controller controller = new Controller();
        guiMain.startGui();
    }

    public static void main(String[] args) {
        LOGGER.info("Formula 1 Simulator is starting up...");
        new Main();
    }
}
