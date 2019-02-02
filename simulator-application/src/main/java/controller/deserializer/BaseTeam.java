package controller.deserializer;

import controller.Constants;
import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Value.Immutable
public interface BaseTeam {

    @NotNull String getName();

    @NotNull List<BaseDriver> getDrivers();

    @Value.Derived
    @NotNull default double[] getPrices() {
        List<BaseDriver> drivers = getDrivers();

        double[] derivedPrices = new double[drivers.get(0).getPrices().length];
        for (int i = 0; i < derivedPrices.length; i++) {
            for (BaseDriver driver : drivers) {
                derivedPrices[i] += Constants.TEAM_COEFFICIENT * driver.getPrices()[i];
            }
        }
        return derivedPrices;
    }
}
