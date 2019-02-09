package gui.setuppanel;

import gui.GuiConstants;
import gui.SetupPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import static gui.setuppanel.ComboBoxManagerUtil.newNumberSelected;
import static gui.setuppanel.ComboBoxManagerUtil.numberReplaced;
import static gui.setuppanel.ComboBoxManagerUtil.numberRemoved;

@SuppressWarnings({"NullPointerException", "All"})
public class SetupComboBoxManager {

    @NotNull private final List<JComboBox<Integer>> driverQualificationPositions = new ArrayList<>();
    @NotNull private final List<JComboBox<Integer>> driverRacePositions = new ArrayList<>();
    @NotNull private final Integer @Nullable [] qualificationCache = new Integer[GuiConstants.NUMBER_OF_DRIVERS];
    @NotNull private final Integer @Nullable [] raceCache = new Integer[GuiConstants.NUMBER_OF_DRIVERS];

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;

    public SetupComboBoxManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;

        for (int i = 0; i < GuiConstants.NUMBER_OF_DRIVERS; i++) {
            driverQualificationPositions.add(new JComboBox<>(new SortedComboBoxModel()));
            driverRacePositions.add(new JComboBox<>(new SortedComboBoxModel()));
        }
    }

    public void init() {
        initializeComboBoxes(constraints, driverQualificationPositions, CompetitionType.QUALIFICATION, 1);
        initializeComboBoxes(constraints, driverRacePositions,CompetitionType.RACE, 2);
    }

    private void initializeComboBoxes(@NotNull GridBagConstraints constraints,
                                      List<JComboBox<Integer>> driverPositions,
                                      CompetitionType type,
                                      int columnNo) {
        int rowNo = 0;
        for (JComboBox<Integer> driverPosition : driverPositions) {
            final int cacheIndex = rowNo;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = rowNo;
            constraints.insets = new Insets(1, 0, 1, 0);
            driverPosition.setMaximumRowCount(GuiConstants.NUMBER_OF_DRIVERS);
            driverPosition.addItem(null);
            int awardedPlaces = type == CompetitionType.QUALIFICATION
                    ? GuiConstants.QUALIFICATION_AWARDED_PLACES
                    : GuiConstants.RACE_AWARDED_PLACES;
            for (Integer j = 1; j <= awardedPlaces; j++) {
                driverPosition.addItem(j);
            }
            driverPosition.addActionListener(e ->
                    updateComboBox(cacheIndex, driverPosition, type));
            setupPanel.add(driverPosition, constraints);
            rowNo++;
        }
    }

    private void updateComboBox(int cacheIndex, @NotNull JComboBox<Integer> receivedCB, CompetitionType type) {
        @Nullable Integer position = (Integer) receivedCB.getSelectedItem();

        if (type == CompetitionType.QUALIFICATION) {
            newNumberSelected(cacheIndex, receivedCB, position, qualificationCache, driverQualificationPositions);
            numberReplaced(cacheIndex, receivedCB, position, qualificationCache, driverQualificationPositions);
            numberRemoved(cacheIndex, receivedCB, position, qualificationCache, driverQualificationPositions);
        } else {
            newNumberSelected(cacheIndex, receivedCB, position, raceCache, driverRacePositions);
            numberReplaced(cacheIndex, receivedCB, position, raceCache, driverRacePositions);
            numberRemoved(cacheIndex, receivedCB, position, raceCache, driverRacePositions);
        }
    }
}
