package model;

import gui.setuppanel.CompetitionType;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
public interface DriverUpdate {

    @NotNull
    static DriverUpdate initialDriverUpdate(int i) {
        return ImmutableDriverUpdate.builder()
                .index(i)
                .position(0)
                .competitionType(CompetitionType.QUALIFICATION)
                .isRaceSetup(false)
                .build();
    }

    int getIndex();

    int getPosition();

    @NotNull CompetitionType getCompetitionType();

    boolean getIsRaceSetup();
}
