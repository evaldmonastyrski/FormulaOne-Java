package gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

class SetupPanel extends JPanel {

    private static final int COMPONENTS_START_ROW = 0;
    private static final int ENGINE_ROW_OFFSET = 13;

    @NotNull private final Border border;
    @NotNull private final GridBagConstraints constraints;
    @NotNull private final Dimension pointLabelsDimension;
    @NotNull private final JLabel[] driverLabels;
    @NotNull private final List<JComboBox<Integer>> driverQualificationPositions;
    @NotNull private final List<JComboBox<Integer>> driverRacePositions;
    @NotNull private final JLabel[] driverPointsLabels;
    @NotNull private final JLabel[] driverPriceChangeLabels;
    @NotNull private final JLabel[] teamLabels;
    @NotNull private final JLabel[] engineLabels;
    @NotNull private final JLabel[] teamPointsLabels;
    @NotNull private final JLabel[] enginePointsLabels;
    @NotNull private final JLabel[] teamPriceChangeLabels;
    @NotNull private final JLabel[] enginePriceChangeLabels;

    SetupPanel() {
        super(new GridBagLayout());
        border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        pointLabelsDimension = new Dimension(10000, 1);

        driverLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
        driverQualificationPositions = new ArrayList<>();
        driverRacePositions = new ArrayList<>();
        driverPointsLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
        driverPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_DRIVERS];
        teamLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
        engineLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
        teamPointsLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
        enginePointsLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
        teamPriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_TEAMS];
        enginePriceChangeLabels = new JLabel[GuiConstants.NUMBER_OF_ENGINES];
        for (int i = 0; i < GuiConstants.NUMBER_OF_DRIVERS; i++) {
            driverQualificationPositions.add(new JComboBox<>());
            driverRacePositions.add(new JComboBox<>());
        }
    }

    void init() {


        initializeNameLabels(constraints, driverLabels, COMPONENTS_START_ROW, 0, "Name Name ");
        initializeComboBoxes(constraints, driverQualificationPositions, 1);
        initializeComboBoxes(constraints, driverRacePositions, 2);
        initializePointsLabels(constraints, driverPointsLabels, pointLabelsDimension, COMPONENTS_START_ROW, 3);
        initializePriceChangeLabels(constraints, driverPriceChangeLabels, COMPONENTS_START_ROW, 4);
        initializeNameLabels(constraints, teamLabels, COMPONENTS_START_ROW, 5, "Team ");
        initializeNameLabels(constraints, engineLabels, ENGINE_ROW_OFFSET, 5, "Engine ");
        initializePointsLabels(constraints, teamPointsLabels, pointLabelsDimension, COMPONENTS_START_ROW, 6);
        initializePointsLabels(constraints, enginePointsLabels, pointLabelsDimension, ENGINE_ROW_OFFSET, 6);
        initializePriceChangeLabels(constraints, teamPriceChangeLabels, COMPONENTS_START_ROW, 7);
        initializePriceChangeLabels(constraints, enginePriceChangeLabels, ENGINE_ROW_OFFSET, 7);
        initializeSimulationButtons(constraints);
    }

    private void initializeNameLabels(@NotNull GridBagConstraints constraints,
                                      JLabel[] nameLabels,
                                      int rowNo,
                                      int columnNo,
                                      @NotNull String name) {
        int row = rowNo;
        for (int i = 0; i < nameLabels.length; i++) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = row;
            nameLabels[i] = new JLabel(name + (i + 1));
            this.add(nameLabels[i], constraints);
            row++;
        }
    }

    private void initializeComboBoxes(@NotNull GridBagConstraints constraints,
                                      List<JComboBox<Integer>> driverPositions,
                                      int columnNo) {
        int rowNo = 0;
        for (JComboBox<Integer> driverPosition : driverPositions) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = rowNo;
            driverPosition.setMaximumRowCount(GuiConstants.NUMBER_OF_DRIVERS);
            for (int j = 1; j < GuiConstants.NUMBER_OF_DRIVERS + 1; j++) {
                driverPosition.addItem(j);
            }
            this.add(driverPosition, constraints);
            rowNo++;
        }
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
            pointsLabels[i].setPreferredSize(new Dimension(60, 20));
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

        JButton simulateButton = new JButton("Simulate");
        JButton flushQButton = new JButton("Flush Q");
        JButton flushRButton = new JButton("Flush R");
        JButton pointSortButton = new JButton("Point Sort");
        JButton priceChangeSortButton = new JButton("Price Change Sort");

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
