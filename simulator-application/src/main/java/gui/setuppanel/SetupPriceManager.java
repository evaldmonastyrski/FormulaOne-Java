package gui.setuppanel;

import gui.GuiConstants;
import gui.SetupPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.GridBagConstraints;

public class SetupPriceManager {

    @NotNull private final JLabel[] driverPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
    @NotNull private final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupPriceManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
    }

    public void init(int startRow, int engineRowOffset) {
        initializePriceChangeLabels(constraints, driverPriceChangeLabels, startRow, 4);
        initializePriceChangeLabels(constraints, teamPriceChangeLabels, startRow, 7);
        initializePriceChangeLabels(constraints, enginePriceChangeLabels, engineRowOffset, 7);
    }

    private void initializePriceChangeLabels(@NotNull GridBagConstraints constraints,
                                             JLabel[] priceChangeLabels,
                                             int rowNo,
                                             int columnNo) {
        int row = rowNo;
        for (int i = 0; i < priceChangeLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            priceChangeLabels[i] = new JLabel("5,000,000");
            priceChangeLabels[i].setBorder(border);
            setupPanel.add(priceChangeLabels[i], constraints);
            row++;
        }
    }
}
