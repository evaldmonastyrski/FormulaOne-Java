package model;

import controller.Constants;
import org.jetbrains.annotations.NotNull;

public class DreamTeam implements Comparable<DreamTeam>{

    @NotNull private final Driver driver1;
    @NotNull private final Driver driver2;
    @NotNull private final Team team;
    @NotNull private final Engine engine;
    private double budget;

    public DreamTeam(@NotNull Driver driver1, @NotNull Driver driver2, @NotNull Team team, @NotNull Engine engine) {
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.team = team;
        this.engine = engine;
    }

    @NotNull public Driver getDriver1() {
        return driver1;
    }

    @NotNull public Driver getDriver2() {
        return driver2;
    }

    @NotNull public Team getTeam() {
        return team;
    }

    @NotNull public Engine getEngine() {
        return engine;
    }

    public double getPrice() {
        return driver1.getPrice() + driver2.getPrice() + team.getPrice() + engine.getPrice();
    }

    public double getPriceOffset() {
        return driver1.getPriceOffset() + driver2.getPriceOffset() + team.getPriceOffset() + engine.getPriceOffset();
    }

    public double getPoints() {
        return driver1.getPoints() + driver2.getPoints() + team.getPoints() + engine.getPoints();
    }

    public double getPriceChange() {
        return driver1.getPriceChange() + driver2.getPriceChange() + team.getPriceChange() + engine.getPriceChange()
                + Constants.INTEREST_RATE * (budget - getPrice());
    }

    public double getMinPoints() {
        return driver1.getMinPoints() + driver2.getMinPoints() + team.getMinPoints() + engine.getMinPoints();
    }

    public double getMaxPriceChange() {
        return driver1.getMaxPriceChange() + driver2.getMaxPriceChange() + team.getMaxPriceChange()
                + engine.getMaxPriceChange() + Constants.INTEREST_RATE * (budget - getPrice());
    }

    public double getRisk() {
        double pnl = getMaxPriceChange();
        if (pnl >= 0d) return 0d;

        return Constants.RISK_FACTOR * pnl;
    }

    public double getOverall() {
        return Constants.OVERALL_POINTS_FACTOR * getPoints()
                + Constants.OVERALL_PRICE_CHANGE_FACTOR * getPriceOffset()
                + Constants.OVERALL_PRICE_OFFSET_FACTOR * getPriceOffset()
                + (Constants.OVERALL_RISK_FACTOR * getRisk() + Constants.OVERALL_RISK_OFFSET);
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public int compareTo(@NotNull DreamTeam o) {
        return 0;
    }
}
