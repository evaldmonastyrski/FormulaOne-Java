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
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class SetupPriceManager {

    @NotNull private final JLabel[] driverPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
    @NotNull private final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    @NotNull private final Dimension priceLabelsDimension = new Dimension(60, 20);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupPriceManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow, int engineRowOffset) {
        initializePriceChangeLabels(driverPriceChangeLabels, startRow, 4);
        initializePriceChangeLabels(teamPriceChangeLabels, startRow, 7);
        initializePriceChangeLabels(enginePriceChangeLabels, engineRowOffset, 7);
    }

    public void updatePriceChange(@NotNull ComponentsUpdate update) {
        driverPriceChangeLabels[update.getDriverIndex()]
                .setText(String.valueOf(String.format("%.1f", update.getDriverPriceChange())));
        teamPriceChangeLabels[update.getTeamIndex()]
                .setText(String.valueOf(String.format("%.2f", update.getTeamPriceChange())));
    }

    private void initializePriceChangeLabels(JLabel[] priceChangeLabels, int rowNo, int columnNo) {
        int row = rowNo;
        for (int i = 0; i < priceChangeLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            priceChangeLabels[i] = new JLabel("", SwingConstants.CENTER);
            priceChangeLabels[i].setBorder(border);
            priceChangeLabels[i].setPreferredSize(priceLabelsDimension);
            setupPanel.add(priceChangeLabels[i], constraints);
            row++;
        }
    }
}
