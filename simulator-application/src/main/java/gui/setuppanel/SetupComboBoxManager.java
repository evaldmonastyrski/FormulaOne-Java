package gui.setuppanel;

import gui.GuiConstants;
import gui.SetupPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class SetupComboBoxManager {

    @NotNull private final List<JComboBox<Integer>> driverQualificationPositions = new ArrayList<>();
    @NotNull private final List<JComboBox<Integer>> driverRacePositions = new ArrayList<>();

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupComboBoxManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;

        for (int i = 0; i < GuiConstants.NUMBER_OF_DRIVERS; i++) {
            driverQualificationPositions.add(new JComboBox<>());
            driverRacePositions.add(new JComboBox<>());
        }
    }

    public void init() {
        initializeComboBoxes(constraints, driverQualificationPositions, 1);
        initializeComboBoxes(constraints, driverRacePositions, 2);
    }

    private void initializeComboBoxes(@NotNull GridBagConstraints constraints,
                                      List<JComboBox<Integer>> driverPositions,
                                      int columnNo) {
        int rowNo = 0;
        for (JComboBox<Integer> driverPosition : driverPositions) {
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = rowNo;
            constraints.insets = new Insets(1, 0, 1, 0);
            driverPosition.setMaximumRowCount(GuiConstants.NUMBER_OF_DRIVERS);
            for (int j = 1; j < GuiConstants.NUMBER_OF_DRIVERS + 1; j++) {
                driverPosition.addItem(j);
            }
            setupPanel.add(driverPosition, constraints);
            rowNo++;
        }
    }
}
