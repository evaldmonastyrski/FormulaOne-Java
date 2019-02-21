package model;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
public interface DreamTeam {

    @NotNull Driver getDriver1();

    @NotNull Driver getDriver2();

    @NotNull Team getTeam();

    @NotNull Engine getEngine();
}
