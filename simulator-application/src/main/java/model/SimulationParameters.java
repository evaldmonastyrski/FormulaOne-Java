package model;

import org.immutables.value.Value;

@Value.Immutable
public interface SimulationParameters {

    double getBudget();

    int getPointsThreshold();

    boolean usePointsThreshold();
}
