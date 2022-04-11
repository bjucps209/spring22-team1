package model;

import java.util.Random;

public class City {
    private int id;
    private Nationality nationality;
    private int x;
    private int y;
    private CityObserver observer;
    //private Level level = new Level(); 

    private static int nextId;

    public City(Nationality nationality) {
        var rand = new Random();
        this.nationality = nationality;
        this.id = ++nextId;
        this.x = rand.nextInt(750);
        this.y = rand.nextInt(450);
    }

    public void updatePosition() {
        if (observer != null) {
            observer.cityMoved(x, y); 
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    } 

    public Object[] getInformation() {
        Object[] items = {id, x, y, nationality}; 
        return items; 
    }
}
