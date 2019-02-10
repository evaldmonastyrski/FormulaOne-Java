package model;

import controller.deserializer.DataEntry;
import org.jetbrains.annotations.NotNull;

import static model.PositionToPointsMap.mapQPosition;
import static model.PositionToPointsMap.mapRPosition;

public class Driver implements Comparable<Driver> {

    @NotNull private final String name;
    @NotNull private final String surname;
    @NotNull private final String team;

    private final double price;

    private int qPosition = 0;
    private int rPosition = 0;
    private double points;
    private double priceChange;

    public Driver(@NotNull DataEntry data, int gpStage) {
        this.name = data.getName();
        this.surname = data.getSurname();
        this.team = data.getTeam();
        price = data.getPrices()[gpStage];
        updateDriverFields();
    }

    @NotNull public String getTeam() {
        return team;
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

    public void setQPosition(int qPosition) {
        this.qPosition = qPosition;
        updateDriverFields();
    }

    public void setRPosition(int rPosition) {
        this.rPosition = rPosition;
        updateDriverFields();
    }

    private void updateDriverFields() {
        this.points = mapQPosition(this.qPosition) + mapRPosition(this.rPosition);
        double newPrice = Constants.PRICING_PRICE_COEFFICIENT * price + Constants.PRICING_POINTS_COEFFICIENT * points;
        newPrice = (newPrice >= 0.5d) ? newPrice : 0.5d;
        priceChange = Math.round((newPrice - price) * 10.0) / 10.0;
    }

    @Override
    public int compareTo(@NotNull Driver o) {
        int compareByPrice = -Double.compare(price, o.price);
        int compareBySurname = name.compareTo(o.surname);
        return compareByPrice != 0 ? compareByPrice : compareBySurname;
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + name.hashCode();
        h += (h << 5) + surname.hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Driver)) {
            return false;
        }
        Driver driver = (Driver) obj;
        return (driver.name.equals(name) &&
                driver.surname.equals(surname) &&
                (driver.price == price));
    }

    @NotNull
    @Override
    public String toString() {
        return (this.name + " " + this.surname);
    }
}
