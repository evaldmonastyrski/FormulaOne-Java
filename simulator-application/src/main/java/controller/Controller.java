package controller;

import controller.deserializer.Deserializer;
import gui.GuiMain;

public class Controller {

    private final GuiMain guiMain;
    private final Deserializer deserializer;

    Controller() {
        guiMain = new GuiMain(this);
        deserializer = new Deserializer();
        initialize();
    }

    private void initialize() {
        guiMain.startGui();
        deserializer.readTextFile();
    }
}
