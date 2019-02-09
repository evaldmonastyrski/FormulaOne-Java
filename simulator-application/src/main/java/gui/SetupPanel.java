package gui;

import gui.setuppanel.SetupComboBoxManager;
import gui.setuppanel.SetupLabelManager;
import model.DreamTeamComponents;
import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SetupPanel extends JPanel {

    private static final int COMPONENTS_START_ROW = 0;
    private static final int ENGINE_ROW_OFFSET = 13;

    @NotNull private final GridBagConstraints constraints = new GridBagConstraints();
    @NotNull private final SetupLabelManager labelManager = new SetupLabelManager(this, constraints);
    @NotNull private final SetupComboBoxManager comboBoxManager = new SetupComboBoxManager(this, constraints);

    @NotNull private final Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    @NotNull private final Dimension pointLabelsDimension = new Dimension(60, 20);
    @NotNull private final JLabel[] driverPointsLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] driverPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final JLabel[] teamPointsLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePointsLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
    @NotNull private final JLabel[] teamPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
    @NotNull private final JLabel[] enginePriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
    @NotNull private final JButton simulateButton = new JButton("Simulate");
    @NotNull private final JButton flushQButton = new JButton("Flush Q");
    @NotNull private final JButton flushRButton = new JButton("Flush R");
    @NotNull private final JButton pointSortButton = new JButton("Point Sort");
    @NotNull private final JButton priceChangeSortButton = new JButton("Price Change Sort");

    SetupPanel() {
        super(new GridBagLayout());
    }

    void init() {
        constraints.weightx = 1;
        labelManager.init(COMPONENTS_START_ROW, ENGINE_ROW_OFFSET);
        comboBoxManager.init();
        initializePointsLabels(constraints, driverPointsLabels, pointLabelsDimension, COMPONENTS_START_ROW, 3);
        initializePriceChangeLabels(constraints, driverPriceChangeLabels, COMPONENTS_START_ROW, 4);
        initializePointsLabels(constraints, teamPointsLabels, pointLabelsDimension, COMPONENTS_START_ROW, 6);
        initializePointsLabels(constraints, enginePointsLabels, pointLabelsDimension, ENGINE_ROW_OFFSET, 6);
        initializePriceChangeLabels(constraints, teamPriceChangeLabels, COMPONENTS_START_ROW, 7);
        initializePriceChangeLabels(constraints, enginePriceChangeLabels, ENGINE_ROW_OFFSET, 7);
        initializeSimulationButtons(constraints);
    }

    void setLabels(@NotNull DreamTeamComponents components) {
        labelManager.setDriverLabels(components.getDrivers());
        labelManager.setTeamLabels(components.getTeams());
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
            this.add(pointsLabels[i], constraints);
            row++;
        }
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
            this.add(priceChangeLabels[i], constraints);
            row++;
        }
    }

    private void initializeSimulationButtons(@NotNull GridBagConstraints constraints) {
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = GuiConstants.NUMBER_OF_DRIVERS + 1;
        constraints.insets = new Insets(15, 0, 0, 0);

        this.add(simulateButton, constraints);
        constraints.gridx = 1;
        this.add(flushQButton, constraints);
        constraints.gridx = 2;
        this.add(flushRButton, constraints);
        constraints.gridx = 3;
        this.add(pointSortButton, constraints);
        constraints.gridx = 4;
        this.add(priceChangeSortButton, constraints);
    }
}
