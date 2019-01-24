package model;

import java.util.Comparator;

public class Driver extends DreamTeamComponent implements Comparable<Driver>
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
    public int compareTo(Driver o) {
        return 0;
    }
}

