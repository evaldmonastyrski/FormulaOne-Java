package controller.deserializer;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

@Value.Immutable
public interface BaseDriver {

    @NotNull String getName();
    @NotNull String getSurname();
    double getPrice();
}
