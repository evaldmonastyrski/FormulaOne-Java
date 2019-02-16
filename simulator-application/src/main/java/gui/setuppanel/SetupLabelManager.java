package gui.setuppanel;

import controller.Constants;
import gui.SetupPanel;
import model.Driver;
import model.Engine;
import model.Team;
import org.jetbrains.annotations.NotNull;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Set;

public class SetupLabelManager {

    @NotNull private final JLabel[] driverLabels = new JLabel[Constants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamLabels = new JLabel[Constants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] engineLabels = new JLabel[Constants.NUMBER_OF_ENGINES];
    @NotNull private final Dimension driverLabelsDimension = new Dimension(120, 20);
    @NotNull private final Dimension corporateLabelsDimension = new Dimension(80, 20);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupLabelManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow, int engineRowOffset) {
        initializeNameLabels(constraints, driverLabels, startRow, 0);
        initializeNameLabels(constraints, teamLabels, startRow, 5);
        initializeNameLabels(constraints, engineLabels, engineRowOffset, 5);
    }

    public void setDriverLabels(@NotNull Set<Driver> drivers) {
        int i = 0;
        for (Driver driver : drivers) {
            driverLabels[i].setText(driver.toString());
            driverLabels[i].setPreferredSize(driverLabelsDimension);
            i++;
        }
    }

    public void setTeamLabels(@NotNull Set<Team> teams) {
        int i = 0;
        for (Team team : teams) {
            teamLabels[i].setText(team.getName());
            teamLabels[i].setPreferredSize(corporateLabelsDimension);
            i++;
        }
    }

    public void setEngineLabels(@NotNull Set<Engine> engines) {
        int i = 0;
        for (Engine engine : engines) {
            engineLabels[i].setText(engine.getName());
            engineLabels[i].setPreferredSize(corporateLabelsDimension);
            i++;
        }
    }

    private void initializeNameLabels(@NotNull GridBagConstraints constraints,
                                      JLabel[] nameLabels,
                                      int rowNo,
                                      int columnNo) {
        int row = rowNo;
        for (int i = 0; i < nameLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            nameLabels[i] = new JLabel("Engine " + (i + 1), SwingConstants.LEFT);
            setupPanel.add(nameLabels[i], constraints);
            row++;
        }
    }
}
