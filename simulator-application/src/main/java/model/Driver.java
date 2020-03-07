package model;

import controller.Constants;
import controller.deserializer.DataEntry;
import gui.setuppanel.CompetitionType;
import org.jetbrains.annotations.NotNull;

import static model.PositionToPointsMap.mapQPosition;
import static model.PositionToPointsMap.mapRPosition;

public class Driver implements Comparable<Driver> {

    @NotNull private final String name;
    @NotNull private final String surname;
    @NotNull private final String team;
    @NotNull private final String engine;

    private final int gpStage;
    private final double price;


    private int qPosition = 0;
    private int rPosition = 0;
    private double points;
    private double minPoints;

    private double priceChange;
    private double maxPriceChange;
    private double priceOffset;

    public Driver(@NotNull DataEntry data, int gpStage) {
        this.name = data.getName();
        this.surname = data.getSurname();
        this.team = data.getTeam();
        this.engine = data.getEngine();
        this.gpStage = gpStage;
        this.price = data.getPrices()[gpStage];
        this.minPoints = data.getMinPoints();
        updateDriverFields();
    }

    @NotNull public String getTeam() {
        return team;
    }

    @NotNull public String getEngine() { return engine; }

    @NotNull public String getSurname() {
        return surname;
    }

    double getPrice() {
        return price;
    }

    public double getPoints() {
        return points;
    }

    public void setMinPoints(double minPoints) {
        this.minPoints = minPoints;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public double getPriceOffset() {
        return priceOffset;
    }

    public double getMinPoints() {
        return minPoints;
    }

    double getMaxPriceChange() {
        return maxPriceChange;
    }

    public void setPosition(@NotNull DriverUpdate driverUpdate) {
        if (!driverUpdate.getIsRaceSetup()) {
            setPosition(driverUpdate.getPosition(), driverUpdate.getCompetitionType());
        } else {
            setQPosition(driverUpdate.getPosition());
            setRPosition(driverUpdate.getPosition());
        }
    }

    private void setPosition(int position, @NotNull CompetitionType type) {
        if (type == CompetitionType.QUALIFICATION) {
            setQPosition(position);
        } else {
            setRPosition(position);
        }
    }

    private void setQPosition(int qPosition) {
        this.qPosition = (qPosition <= Constants.QUALIFICATION_AWARDED_PLACES) ? qPosition : 0;
        updateDriverFields();
    }

    private void setRPosition(int rPosition) {
        this.rPosition = rPosition;
        updateDriverFields();
    }

    private void updateDriverFields() {
        this.points = mapQPosition(this.qPosition) + mapRPosition(this.rPosition);
        double newPrice = Constants.PRICING_PRICE_COEFFICIENT * price + Constants.PRICING_POINTS_COEFFICIENT * points;
        double newMaxPrice = Constants.PRICING_PRICE_COEFFICIENT * price
                + Constants.PRICING_POINTS_COEFFICIENT * minPoints;
        newPrice = (newPrice >= 0.5d) ? newPrice : 0.5d;
        newMaxPrice = (newMaxPrice >= 0.5d) ? newMaxPrice : 0.5d;
        priceChange = Math.round((newPrice - price) * 10.0) / 10.0;
        maxPriceChange = Math.round((newMaxPrice - price) * 10.0) / 10.0;
    }

    public void setPriceOffset(@NotNull DataEntry data, int samplesNumber) {
        double priceSum = 0;
        int startingStage = gpStage <= samplesNumber ? 0 : gpStage - samplesNumber;
        int numberOfStages = gpStage - startingStage;

        for (int i = startingStage; i < gpStage; i++) {
            priceSum += data.getPrices()[i];
        }

        double averagePrice = priceSum / numberOfStages;
        priceOffset = averagePrice - price;
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
