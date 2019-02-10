package model;

import controller.deserializer.DataEntry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static model.PositionToPointsMap.mapQPosition;
import static model.PositionToPointsMap.mapRPosition;

public class Driver implements Comparable<Driver> {

    @NotNull private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    @NotNull private final String name;
    @NotNull private final String surname;

    private final double price;

    private int qPosition;
    private int rPosition;
    private int points;
    private double priceChange;

    public Driver(@NotNull DataEntry data, int gpStage) {
        this.name = data.getName();
        this.surname = data.getSurname();
        price = data.getPrices()[gpStage];
    }

    double getPrice() {
        return price;
    }

    int getQPosition() {
        return qPosition;
    }

    public void setQPosition(int qPosition) {
        this.qPosition = qPosition;
        updateDriverFields("{} {} earned {} points and {}$");
    }

    int getRPosition() {
        return rPosition;
    }

    public void setRPosition(int rPosition) {
        this.rPosition = rPosition;
        updateDriverFields("{} {} earned {} and {}$");
    }

    private void updateDriverFields(@NotNull String s) {
        this.points = mapQPosition(this.qPosition) + mapRPosition(this.rPosition);
        double newPrice = Constants.PRICING_PRICE_COEFFICIENT * price + Constants.PRICING_POINTS_COEFFICIENT * points;
        newPrice = (newPrice >= 0.5d) ? newPrice : 0.5d;
        priceChange = newPrice - price;
        LOGGER.info(s, name, surname, points, priceChange);
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
