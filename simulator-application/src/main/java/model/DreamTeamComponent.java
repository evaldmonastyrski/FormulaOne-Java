package model;

public abstract class DreamTeamComponent {

    private String name;
    private Float price;

    public String getName() {
        return name;
    }
    public Float getPrice(){
        return price;
    }
    protected Float setPrice(){
        return price;
    }
    public DreamTeamComponent (String name, Float price){
        this.name = name;
        this.price = price;
    }
    public DreamTeamComponent (String name){
        this.name = name;
    }
}