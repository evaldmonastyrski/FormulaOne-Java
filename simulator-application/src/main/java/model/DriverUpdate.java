package model;

import gui.setuppanel.CompetitionType;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
public interface DriverUpdate {

    int getIndex();

    int getPosition();

    @NotNull CompetitionType getCompetitionType();

    boolean getIsRaceSetup();
}
