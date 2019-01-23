package gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiMain {

    public void startGui() {
        final JFrame mainFrame;
        final JTabbedPane tabbedPane;
        final SimulationTab simulationTab;

        mainFrame = new JFrame("Formula 1");
        tabbedPane = new JTabbedPane();
        simulationTab = new SimulationTab();

        mainFrame.setSize(400, 400);
        mainFrame.add(tabbedPane);
        tabbedPane.addTab("Simulation", simulationTab);

        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
