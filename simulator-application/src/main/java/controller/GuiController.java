package controller;

import gui.GuiMain;
import model.DreamTeamComponents;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;

public class GuiController {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiController.class);

    @NotNull private final Controller controller;
    @NotNull private final GuiMain guiMain;

    GuiController(@NotNull Controller controller) {
        this.controller = controller;
        this.guiMain = new GuiMain(this);
    }

    public void onReloadButtonClicked() {
        LOGGER.info("Reload button clicked");
        controller.onReloadButtonClicked();
        guiMain.closeGui();
    }

    public void onGPIndexChanged(int gpIndex) {
        LOGGER.info("GP stage changed");
        System.out.println(SwingUtilities.isEventDispatchThread());
        controller.onGPIndexChanged(gpIndex);
    }

    void startGui(@NotNull String[] gpStages) {
        guiMain.runGui(gpStages);
    }

    void initializeLabels(@NotNull DreamTeamComponents components) {
        guiMain.initializeLabels(components);
    }
}
