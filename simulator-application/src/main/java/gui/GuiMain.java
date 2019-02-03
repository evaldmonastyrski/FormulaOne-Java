package gui;

import com.apple.eawt.Application;
import controller.DreamTeamComponents;
import controller.GuiController;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiMain {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiMain.class);
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 740;

    @NotNull private final GuiController guiController;
    @NotNull private final JFrame mainFrame;
    @NotNull private final JTabbedPane tabbedPane;
    @NotNull private final SimulationTab simulationTab;
    @NotNull private final ImageIcon icon;

    public GuiMain(@NotNull GuiController guiController) {
        this.guiController = guiController;
        icon = new ImageIcon("logo.png");
        mainFrame = new JFrame("Formula 1");
        tabbedPane = new JTabbedPane();
        simulationTab = new SimulationTab(guiController);
    }

    public void runGui(@NotNull String[] gpStages) {
        addIcon();
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.add(tabbedPane);
        tabbedPane.addTab("Simulation", simulationTab);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        simulationTab.init(gpStages);
        LOGGER.info("GUI has started, {} is received", guiController);
        mainFrame.setVisible(true);
    }

    public void initializeLabels(@NotNull DreamTeamComponents components) {
        simulationTab.setLabels(components);
    }

    public void closeGui() {
        mainFrame.setVisible(false);
    }

    private void addIcon() {
        if (GuiMainUtil.isApple()) {
            Application.getApplication().setDockIconImage(icon.getImage());
        }

        if (GuiMainUtil.isWindows()) {
            mainFrame.setIconImage(icon.getImage());
        }
    }
}
