package model;

import controller.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Engine implements Comparable<Engine> {

    @NotNull private final String name;
    @NotNull private final List<Driver> drivers;

    private final double price;

    private double points;
    private double minPoints;
    private double priceChange;
    private double maxPriceChange;
    private double priceOffset;

    public Engine (@NotNull String name, @NotNull List<Driver> drivers) {
        this.name = name;
        this.drivers = drivers;
        this.price = derivePrice();
        this.minPoints = deriveMinPoints();
        updateEngineFields();
    }

    @NotNull public String getName() { return name; }

    double getPrice() {
        return price;
    }

    public double getPoints() { return points; }

    public double getPriceChange() { return priceChange; }

    public double getPriceOffset() {
        return priceOffset;
    }

    double getMinPoints() {
        return minPoints;
    }

    double getMaxPriceChange() {
        return maxPriceChange;
    }

    public void updateEngine() { updateEngineFields(); }

    public void setPriceOffset() {
        double priceOffset = 0;
        for (Driver driver : drivers) {
            priceOffset += Constants.ENGINE_COEFFICIENT * driver.getPriceOffset();
        }
        this.priceOffset = priceOffset;
    }

    public void setMinPoints() {
        this.minPoints = deriveMinPoints();
    }

    private void updateEngineFields() {
       int tempPoints = 0;
       double tempPriceChange = 0;
       double tempMaxPriceChange = 0;
       for (Driver driver : drivers) {
           tempPoints += driver.getPoints();
           tempPriceChange += driver.getPriceChange();
           tempMaxPriceChange += driver.getMaxPriceChange();
       }
       points = Constants.ENGINE_COEFFICIENT * tempPoints;
       priceChange = Constants.ENGINE_COEFFICIENT * tempPriceChange;
       maxPriceChange = Constants.ENGINE_COEFFICIENT * tempMaxPriceChange;
    }

    private double derivePrice(){
        double priceCache = 0;
        for (Driver driver : drivers){
            priceCache += Constants.ENGINE_COEFFICIENT * driver.getPrice();
        }
        return priceCache;
    }

    private double deriveMinPoints() {
        double pointsCache = 0;
        for (Driver driver : drivers) {
            pointsCache += Constants.ENGINE_COEFFICIENT * driver.getMinPoints();
        }
        return pointsCache;
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

