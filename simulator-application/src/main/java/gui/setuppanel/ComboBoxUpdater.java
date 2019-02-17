package gui.setuppanel;

import gui.SetupPanel;
import model.DriverUpdate;
import model.ImmutableDriverUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComboBox;
import java.util.List;

@SuppressWarnings("All")
class ComboBoxUpdater {

    @NotNull private final SetupPanel setupPanel;

    ComboBoxUpdater(@NotNull SetupPanel setupPanel) {
        this.setupPanel = setupPanel;
    }

    void newNumberSelected(int cacheIndex,
                           @NotNull JComboBox<Integer> receivedCB,
                           @Nullable Integer position,
                           @NotNull Integer @Nullable[] cache,
                           @NotNull List<JComboBox<Integer>> positions,
                           @NotNull CompetitionType type,
                           boolean isRaceSetup) {
        if (cache[cacheIndex] == null && position != null) {
            cache[cacheIndex] = position;
            sendUpdate(cacheIndex, position, type, isRaceSetup);
            for (JComboBox<Integer> cb : positions) {
                if (!cb.equals(receivedCB)) {
                    cb.removeItem(position);
                }
            }
        }
    }

    void numberReplaced(int cacheIndex,
                        @NotNull JComboBox<Integer> receivedCB,
                        @Nullable Integer position,
                        @NotNull Integer @Nullable[] cache,
                        @NotNull List<JComboBox<Integer>> positions,
                        @NotNull CompetitionType type,
                        boolean isRaceSetup) {
        if (cache[cacheIndex] != null && position != null) {
            if (!cache[cacheIndex].equals(position)) {
                Integer temp = cache[cacheIndex];
                cache[cacheIndex] = position;
                sendUpdate(cacheIndex, position, type, isRaceSetup);
                for (JComboBox<Integer> cb : positions) {
                    if (!cb.equals(receivedCB)) {
                        cb.addItem(temp);
                        cb.removeItem(position);
                    }
                }
            }
        }
    }

    void numberRemoved(int cacheIndex,
                       @NotNull JComboBox<Integer> receivedCB,
                       @Nullable Integer position,
                       @NotNull Integer @Nullable[] cache,
                       @NotNull List<JComboBox<Integer>> positions,
                       @NotNull CompetitionType type,
                       boolean isRaceSetup) {
        if (cache[cacheIndex] != null && position == null) {
            Integer temp = cache[cacheIndex];
            cache[cacheIndex] = position;
            sendUpdate(cacheIndex, position, type, isRaceSetup);
            for (JComboBox<Integer> cb : positions) {
                if (!cb.equals(receivedCB)) {
                    cb.addItem(temp);
                }
            }
        }
    }

    private void sendUpdate(int cacheIndex,
                            @Nullable Integer position,
                            @NotNull CompetitionType type,
                            boolean isRaceSetup) {
        int intPosition = position != null ? position : 0;

        DriverUpdate driverUpdate = ImmutableDriverUpdate.builder()
                .index(cacheIndex)
                .position(intPosition)
                .competitionType(type)
                .isRaceSetup(isRaceSetup)
                .build();

        setupPanel.updateDriver(driverUpdate);
    }
}
