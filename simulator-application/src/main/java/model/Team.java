package model;

import controller.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Team implements Comparable<Team> {

    @NotNull private final String name;
    @NotNull private final List<Driver> drivers;

    private final double price;

    private double points;
    private double priceChange;
    private double priceOffset;

    public Team(@NotNull String name, @NotNull List<Driver> drivers) {
        this.name = name;
        this.drivers = drivers;
        this.price = derivePrice();
        updateTeamFields();
    }

    @NotNull public String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }

    public double getPoints() {
        return points;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public double getPriceOffset() {
        return priceOffset;
    }

    public void updateTeam() {
        updateTeamFields();
    }

    public void setPriceOffset() {
        double priceOffset = 0;
        for (Driver driver : drivers) {
            priceOffset += Constants.TEAM_COEFFICIENT * driver.getPriceOffset();
        }
        this.priceOffset = priceOffset;
    }

    private void updateTeamFields() {
        int tempPoints = 0;
        double tempPriceChange = 0;
        for (Driver driver : drivers) {
            tempPoints += driver.getPoints();
            tempPriceChange += driver.getPriceChange();
        }
        points = Constants.TEAM_COEFFICIENT * tempPoints;
        priceChange = Constants.TEAM_COEFFICIENT * tempPriceChange;
    }

    private double derivePrice() {
        double priceCache = 0;
        for (Driver driver : drivers) {
            priceCache += Constants.TEAM_COEFFICIENT * driver.getPrice();
        }
        return priceCache;
    }

    @Override
    public int compareTo(@NotNull Team o) {
        int compareByPrice = -Double.compare(price, o.price);
        int compareByName = name.compareTo(o.name);
        return compareByPrice != 0 ? compareByPrice : compareByName;
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + name.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Team)) {
            return false;
        }
        Team team = (Team) obj;
        return (team.name.equals(name) &&
                (team.price == price));
    }

    @NotNull
    @Override
    public String toString() {
        return this.name;
    }
}
