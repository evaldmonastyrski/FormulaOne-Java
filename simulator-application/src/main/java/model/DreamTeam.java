package model;

import org.jetbrains.annotations.NotNull;

public class DreamTeam {

    @NotNull private final Driver driver1;
    @NotNull private final Driver driver2;
    @NotNull private final Team team;
    @NotNull private final Engine engine;

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

    public double getPoints() {
        return driver1.getPoints() + driver2.getPoints() + team.getPoints() + engine.getPoints();
    }

    public double getPriceChange() {
        return driver1.getPriceChange() + driver2.getPriceChange() + team.getPriceChange() + engine.getPriceChange();
    }
}
