package model;

public abstract class DreamTeamComponent {

    private String name;
    private double price;
    private double points;
    private double priceChange;

    public DreamTeamComponent (String name, Double price){
        this.name = name;
        this.price = price;
    }
    public DreamTeamComponent (String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    protected void setPrice(double price) {
        this.price = price;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

   /* public int CompareTo (Object obj)
    {
        DreamTeamComponent driver = (DreamTeamComponent)obj;

    }*/
}