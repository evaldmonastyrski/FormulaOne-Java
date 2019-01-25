package gui;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiMain.class);

    private final Controller controller;

    public GuiMain(Controller controller) {
        this.controller = controller;
    }

    public void startGui() {
        final JFrame mainFrame;
        final JTabbedPane tabbedPane;
        final SimulationTab simulationTab;

        mainFrame = new JFrame("Formula 1");
        tabbedPane = new JTabbedPane();
        simulationTab = new SimulationTab();

        mainFrame.setSize(900, 400);
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
