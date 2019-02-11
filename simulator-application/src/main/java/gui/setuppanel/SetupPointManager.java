package gui.setuppanel;

import gui.GuiConstants;
import gui.SetupPanel;
import model.ComponentsUpdate;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class SetupPointManager {

    @NotNull private final JLabel[] driverPointsLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamPointsLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePointsLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
    @NotNull private final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    @NotNull private final Dimension pointLabelsDimension = new Dimension(80, 24);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupPointManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow, int engineRowOffset) {
        initializePointsLabels(driverPointsLabels, startRow, 3);
        initializePointsLabels(teamPointsLabels, startRow, 6);
        initializePointsLabels(enginePointsLabels, engineRowOffset, 6);
    }

    public void updatePoints(@NotNull ComponentsUpdate update) {
        driverPointsLabels[update.getDriverIndex()].setText(String.valueOf(Math.round(update.getDriverPoints())));
        teamPointsLabels[update.getTeamIndex()].setText(String.valueOf(String.format("%.1f", update.getTeamPoints())));
    }

    private void initializePointsLabels(@NotNull JLabel[] pointsLabels, int rowNo, int columnNo) {
        int row = rowNo;
        for (int i = 0; i < pointsLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            pointsLabels[i] = new JLabel("", SwingConstants.CENTER);
            pointsLabels[i].setBorder(border);
            pointsLabels[i].setOpaque(true);
            pointsLabels[i].setBackground(Color.WHITE);
            pointsLabels[i].setFont(pointsLabels[i].getFont().deriveFont(13f));
            pointsLabels[i].setPreferredSize(pointLabelsDimension);
            setupPanel.add(pointsLabels[i], constraints);
            row++;
        }
    }
}
