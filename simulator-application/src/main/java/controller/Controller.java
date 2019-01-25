package controller;

import controller.deserializer.Deserializer;
import gui.GuiMain;
import org.jetbrains.annotations.NotNull;

public class Controller {

    @NotNull private final GuiMain guiMain;
    @NotNull private final Deserializer deserializer;

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
