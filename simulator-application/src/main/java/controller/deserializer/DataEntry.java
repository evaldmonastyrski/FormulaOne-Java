package controller.deserializer;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
public interface DataEntry {

    @NotNull String getName();

    @NotNull String getSurname();

    @NotNull String getTeam();

    @NotNull String getEngine();

    double getMinPoints();

    @NotNull double[] getPrices();
}
