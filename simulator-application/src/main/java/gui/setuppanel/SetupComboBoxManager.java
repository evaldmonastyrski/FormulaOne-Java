package gui.setuppanel;

import controller.Constants;
import gui.SetupPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class SetupComboBoxManager {

    @NotNull private final List<JComboBox<Integer>> driverQualificationPositions = new ArrayList<>();
    @NotNull private final List<JComboBox<Integer>> driverRacePositions = new ArrayList<>();
    @NotNull private final Integer @Nullable [] qualificationCache = new Integer[Constants.NUMBER_OF_DRIVERS];
    @NotNull private final Integer @Nullable [] raceCache = new Integer[Constants.NUMBER_OF_DRIVERS];
    @NotNull private final Dimension comboBoxDimension = new Dimension(70, 27);

    @NotNull private final SetupPanel setupPanel;
    @NotNull private final GridBagConstraints constraints;
    @NotNull private final ComboBoxUpdater comboBoxUpdater;

    public SetupComboBoxManager(@NotNull SetupPanel setupPanel, @NotNull GridBagConstraints constraints) {
        this.setupPanel = setupPanel;
        this.constraints = constraints;
        this.comboBoxUpdater = new ComboBoxUpdater(setupPanel);


        for (int i = 0; i < Constants.NUMBER_OF_DRIVERS; i++) {
            driverQualificationPositions.add(new JComboBox<>(new SortedComboBoxModel()));
            driverRacePositions.add(new JComboBox<>(new SortedComboBoxModel()));
        }
    }

    public void init() {
        initializeComboBoxes(constraints, driverQualificationPositions, CompetitionType.QUALIFICATION, 1);
        initializeComboBoxes(constraints, driverRacePositions,CompetitionType.RACE, 2);
    }

    public void flushQualificationComboBoxes() {
        for (JComboBox<Integer> cb : driverQualificationPositions) {
            if (cb.getSelectedItem() != null) {
                cb.setSelectedItem(null);
            }
        }
    }

    public void flushRaceComboBoxes() {
        for (JComboBox<Integer> cb : driverRacePositions) {
            if (cb.getSelectedItem() != null) {
                cb.setSelectedItem(null);
            }
        }
    }

    private void initializeComboBoxes(@NotNull GridBagConstraints constraints,
                                      List<JComboBox<Integer>> driverPositions,
                                      @NotNull CompetitionType type,
                                      int columnNo) {
        int rowNo = 0;
        for (JComboBox<Integer> driverPosition : driverPositions) {
            final int cacheIndex = rowNo;
            constraints.fill = GridBagConstraints.CENTER;
            constraints.gridx = columnNo;
            constraints.gridy = rowNo;
            constraints.insets = new Insets(1, 0, 1, 0);
            driverPosition.setMaximumRowCount(Constants.NUMBER_OF_DRIVERS);
            driverPosition.setPreferredSize(comboBoxDimension);
            driverPosition.addItem(null);
            int awardedPlaces = type == CompetitionType.QUALIFICATION
                    ? Constants.QUALIFICATION_AWARDED_PLACES
                    : Constants.RACE_AWARDED_PLACES;
            for (Integer j = 1; j <= awardedPlaces; j++) {
                driverPosition.addItem(j);
            }
            driverPosition.addActionListener(e ->
                    updateComboBox(cacheIndex, driverPosition, type));
            setupPanel.add(driverPosition, constraints);
            rowNo++;
        }
    }

    private void updateComboBox(int cacheIndex, @NotNull JComboBox<Integer> receivedCB, @NotNull CompetitionType type) {
        @Nullable Integer position = (Integer) receivedCB.getSelectedItem();

        if (type == CompetitionType.QUALIFICATION) {
            comboBoxUpdater.newNumberSelected(cacheIndex, receivedCB, position, qualificationCache,
                    driverQualificationPositions, CompetitionType.QUALIFICATION);
            comboBoxUpdater.numberReplaced(cacheIndex, receivedCB, position, qualificationCache,
                    driverQualificationPositions, CompetitionType.QUALIFICATION);
            comboBoxUpdater.numberRemoved(cacheIndex, receivedCB, position, qualificationCache,
                    driverQualificationPositions, CompetitionType.QUALIFICATION);
        } else {
            comboBoxUpdater.newNumberSelected(cacheIndex, receivedCB, position, raceCache,
                    driverRacePositions, CompetitionType.RACE);
            comboBoxUpdater.numberReplaced(cacheIndex, receivedCB, position, raceCache,
                    driverRacePositions, CompetitionType.RACE);
            comboBoxUpdater.numberRemoved(cacheIndex, receivedCB, position, raceCache,
                    driverRacePositions, CompetitionType.RACE);
        }
    }
}
