package model;

import controller.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Team implements Comparable<Team> {

    @NotNull private final String name;
    @NotNull private List<Driver> drivers;

    private final double price;

    public Team(@NotNull String name, @NotNull List<Driver> drivers) {
        this.name = name;
        this.drivers = drivers;
        this.price = derivePrice();
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

    @NotNull
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
