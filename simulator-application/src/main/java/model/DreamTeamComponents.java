package model;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Value.Immutable
public interface DreamTeamComponents {

    @NotNull List<Driver> getDrivers();

    @NotNull List<Team> getTeams();

    @NotNull List<Engine> getEngines();
}
