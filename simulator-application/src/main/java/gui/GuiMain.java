package gui;

import com.apple.eawt.Application;
import controller.GuiViewController;
import gui.handlers.GuiMainHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiMain implements GuiMainHandler {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(GuiMain.class);
    private static final int WINDOW_WIDTH = 1220;
    private static final int WINDOW_HEIGHT = 740;

    @NotNull private final GuiViewController guiViewController;
    @NotNull private final JFrame mainFrame = new JFrame("Formula 1");
    @NotNull private final JTabbedPane tabbedPane = new JTabbedPane();
    @NotNull private final SimulationTab simulationTab;
    @NotNull private final HelpTab helpTab;
    @NotNull private final ImageIcon icon = new ImageIcon("Resources/Graphics/logo.png");

    public GuiMain(@NotNull GuiViewController guiViewController) {
        simulationTab = new SimulationTab(guiViewController);
        helpTab = new HelpTab();
        this.guiViewController = guiViewController;
    }

    public void runGui(@NotNull String[] gpStages) {
        addIcon();
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.add(tabbedPane);
        tabbedPane.addTab("Simulation", simulationTab);
        tabbedPane.addTab("Help", helpTab);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        simulationTab.init(gpStages);
        helpTab.init();
        LOGGER.info("GUI has started, {} is received", guiViewController);
        mainFrame.setVisible(true);
    }

    @NotNull
    public SimulationTab getSimulationTab() {
        return simulationTab;
    }

    @Override
    public void closeGui() {
        mainFrame.setVisible(false);
    }

    private void addIcon() {
        if (GuiMainUtil.isApple()) {
            Application.getApplication().setDockIconImage(icon.getImage());
        }

        if (GuiMainUtil.isWindows() || GuiMainUtil.isLinux()) {
            mainFrame.setIconImage(icon.getImage());
        }
    }
}
