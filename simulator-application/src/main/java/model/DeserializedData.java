package model;

import controller.deserializer.DataEntry;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Value.Immutable
public interface DeserializedData {
    @NotNull String[] getGPStages();
    @NotNull List<DataEntry> getData();
}
