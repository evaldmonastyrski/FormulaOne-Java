package gui.setuppanel;

import gui.GuiConstants;
import gui.SetupPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class SetupPointManager {

    @NotNull private final JLabel[] driverPointsLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamPointsLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePointsLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
    @NotNull private final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    @NotNull private final Dimension pointLabelsDimension = new Dimension(60, 20);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupPointManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow, int engineRowOffset) {
        initializePointsLabels(constraints, driverPointsLabels, pointLabelsDimension, startRow, 3);
        initializePointsLabels(constraints, teamPointsLabels, pointLabelsDimension, startRow, 6);
        initializePointsLabels(constraints, enginePointsLabels, pointLabelsDimension, engineRowOffset, 6);
    }

    private void initializePointsLabels(@NotNull GridBagConstraints constraints,
                                        @NotNull JLabel[] pointsLabels,
                                        @NotNull Dimension dimension,
                                        int rowNo,
                                        int columnNo) {
        int row = rowNo;
        for (int i = 0; i < pointsLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            pointsLabels[i] = new JLabel("0", SwingConstants.CENTER);
            pointsLabels[i].setBorder(border);
            pointsLabels[i].setPreferredSize(dimension);
            setupPanel.add(pointsLabels[i], constraints);
            row++;
        }
    }
}
