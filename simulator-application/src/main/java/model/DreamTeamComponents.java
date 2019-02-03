package model;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Value.Immutable
public interface DreamTeamComponents {

    @NotNull Set<Driver> getDrivers();

    @NotNull Set<Team> getTeams();
}
