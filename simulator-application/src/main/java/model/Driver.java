package model;

import java.util.Comparator;

public class Driver extends DreamTeamComponent
{
    public String surname;

    public String getSurname(){
        return surname;
    }

    public Driver(String name, String surname, double price) {
        super(name, price);
        this.surname = surname;
    }

    @Override
    public int compareTo(DreamTeamComponent o) {
        return 0;
    }
}

