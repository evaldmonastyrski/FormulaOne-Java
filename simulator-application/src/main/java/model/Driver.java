package model;

import controller.deserializer.DataEntry;
import org.jetbrains.annotations.NotNull;

public class Driver implements Comparable<Driver> {

    @NotNull private final String name;
    @NotNull private final String surname;

    private final double price;

    public Driver(@NotNull DataEntry data, int gpStage) {
        this.name = data.getName();
        this.surname = data.getSurname();
        price = data.getPrices()[gpStage];
    }

    double getPrice() {
        return price;
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
