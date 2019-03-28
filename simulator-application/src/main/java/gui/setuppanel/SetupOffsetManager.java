package gui.setuppanel;

import controller.Constants;
import gui.SetupPanel;
import model.OffsetUpdate;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class SetupOffsetManager {

    @NotNull
    private final JLabel[] driverPriceOffsetLabels = new JLabel[Constants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamPriceOffsetLabels = new JLabel[Constants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePriceOffsetLabels = new JLabel[Constants.NUMBER_OF_ENGINES];
    @NotNull private final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    @NotNull private final Dimension offsetLabelsDimension = new Dimension(80, 24);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupOffsetManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow, int engineRowOffset) {
        initializePriceOffsetLabels(driverPriceOffsetLabels, startRow, 6);
        initializePriceOffsetLabels(teamPriceOffsetLabels, startRow, 10);
        initializePriceOffsetLabels(enginePriceOffsetLabels, engineRowOffset, 10);
    }

    public void updateOffsets(@NotNull OffsetUpdate offsetUpdate) {
        for (int i = 0; i < Constants.NUMBER_OF_DRIVERS; i++) {
            double priceOffset = offsetUpdate.getDrivers().get(i).getPriceOffset();
            driverPriceOffsetLabels[i].setText(String.valueOf(String.format("%.1f", priceOffset)));
            Coloring.colorLabel(driverPriceOffsetLabels[i], priceOffset);
        }
        for (int j = 0; j < Constants.NUMBER_OF_TEAMS; j++) {
            double priceOffset = offsetUpdate.getTeams().get(j).getPriceOffset();
            teamPriceOffsetLabels[j].setText(String.valueOf(String.format("%.2f", priceOffset)));
            Coloring.colorLabel(teamPriceOffsetLabels[j], priceOffset);
        }
        for (int k = 0; k < Constants.NUMBER_OF_ENGINES; k++) {
            double priceOffset = offsetUpdate.getEngines().get(k).getPriceOffset();
            enginePriceOffsetLabels[k].setText(String.valueOf(String.format("%.2f", priceOffset)));
            Coloring.colorLabel(enginePriceOffsetLabels[k], priceOffset);
        }
    }

    private void initializePriceOffsetLabels(@NotNull JLabel[] priceOffsetLabels, int rowNo, int columnNo) {
        int row = rowNo;
        for (int i = 0; i < priceOffsetLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            priceOffsetLabels[i] = new JLabel("", SwingConstants.CENTER);
            priceOffsetLabels[i].setBorder(border);
            priceOffsetLabels[i].setPreferredSize(offsetLabelsDimension);
            priceOffsetLabels[i].setFont(priceOffsetLabels[i].getFont().deriveFont(13f));
            priceOffsetLabels[i].setOpaque(true);
            setupPanel.add(priceOffsetLabels[i], constraints);
            row++;
        }
    }
}
