package gui;

import controller.Controller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiMain {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiMain.class);
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 400;

    @NotNull private final Controller controller;

    public GuiMain(@NotNull Controller controller) {
        this.controller = controller;
    }

    public void startGui() {
        final JFrame mainFrame;
        final JTabbedPane tabbedPane;
        final SimulationTab simulationTab;

        mainFrame = new JFrame("Formula 1");
        tabbedPane = new JTabbedPane();
        simulationTab = new SimulationTab();

        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.add(tabbedPane);
        tabbedPane.addTab("Simulation", simulationTab);

        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        LOGGER.info("GUI has started, {} is received", controller);
    }
}
