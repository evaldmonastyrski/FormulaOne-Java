package model;

import controller.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Engine implements Comparable<Engine> {

    @NotNull private final String name;
    @NotNull private final List<Driver> drivers;

    private final double price;

    private double points;
    private double priceChange;

    public Engine (@NotNull String name, @NotNull List<Driver> drivers) {
        this.name = name;
        this.drivers = drivers;
        this.price = derivePrice();
        updateEngineFields();
    }

    @NotNull public String getName() { return name; }

    public double getPoints() { return points; }

    public double getPriceChange() { return priceChange; }

    public void updateEngine() { updateEngineFields(); }

    private void updateEngineFields() {
       int tempPoints = 0;
       double tempPriceChange = 0;
       for (Driver driver : drivers) {
           tempPoints += driver.getPoints();
           tempPriceChange += driver.getPriceChange();
       }
       points = Constants.ENGINE_COEFFICIENT * tempPoints;
       priceChange = Constants.ENGINE_COEFFICIENT * tempPriceChange;
    }

    private double derivePrice(){
        double priceCache = 0;
        for (Driver driver : drivers){
            priceCache += Constants.ENGINE_COEFFICIENT * driver.getPrice();
        }
        return priceCache;

    }

    @Override
    public int compareTo(@NotNull Engine o) {
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
        if (!(obj instanceof Engine)) {
            return false;
        }
        Engine engine = (Engine) obj;
        return (engine.name.equals(name) &&
                (engine.price == price));
    }

    @NotNull
    @Override
    public String toString() {
        return this.name;
    }
}

