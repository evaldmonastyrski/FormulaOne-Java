package controller;

import gui.GuiMain;
import org.jetbrains.annotations.NotNull;

public class GuiController {

    @NotNull private final Controller controller;
    @NotNull private final GuiMain guiMain;

    GuiController(@NotNull Controller controller) {
        this.controller = controller;
        this.guiMain = new GuiMain(this);
    }

    public void onReloadButtonClicked() {
        controller.onReloadButtonClicked();
        guiMain.closeGui();
    }

    void startGui() {
        guiMain.runGui();
    }
}
