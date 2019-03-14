package gui;

import com.apple.eawt.Application;
import model.ComponentsUpdate;
import model.DreamTeamComponents;
import controller.GuiController;
import model.OffsetUpdate;
import model.SimulationParameters;
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
    private static final int WINDOW_WIDTH = 1100;
    private static final int WINDOW_HEIGHT = 740;

    @NotNull private final GuiController guiController;
    @NotNull private final JFrame mainFrame = new JFrame("Formula 1");
    @NotNull private final JTabbedPane tabbedPane = new JTabbedPane();
    @NotNull private final SimulationTab simulationTab;
    @NotNull private final HelpTab helpTab;
    @NotNull private final ImageIcon icon = new ImageIcon("Resources/Graphics/logo.png");

    public GuiMain(@NotNull GuiController guiController) {
        simulationTab = new SimulationTab(guiController);
        helpTab = new HelpTab();
        this.guiController = guiController;
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
        LOGGER.info("GUI has started, {} is received", guiController);
        mainFrame.setVisible(true);
    }

    public void initializeLabels(@NotNull DreamTeamComponents components) {
        simulationTab.setLabels(components);
    }

    public void flushComboBoxes() {
        simulationTab.flushComboBoxes();
    }

    public void updateGUILabels(@NotNull ComponentsUpdate componentsUpdate) {
        simulationTab.updateGUILabels(componentsUpdate);
    }

    public void updateOffsets(@NotNull OffsetUpdate offsetUpdate) {
        simulationTab.updateOffsets(offsetUpdate);
    }

    public void raceSetup(boolean isSelected) {
        simulationTab.raceSetup(isSelected);
    }

    public boolean isRaceSetup() {
        return simulationTab.isRaceSetup();
    }

    public void disableSimulationResults() {
        simulationTab.disableSimulationResults();
    }

    @NotNull public SimulationParameters getSimulationParameters() {
        return simulationTab.getSimulationParameters();
    }

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
