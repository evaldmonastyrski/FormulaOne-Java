package model;


import org.immutables.value.Value;

@Value.Immutable
public interface ComponentsUpdate {

    int getDriverIndex();

    double getDriverPoints();

    double getDriverPriceChange();

    int getTeamIndex();

    double getTeamPoints();

    double getTeamPriceChange();

    int getEngineIndex();

    double getEnginePoints();

    double getEnginePriceChange();
}
