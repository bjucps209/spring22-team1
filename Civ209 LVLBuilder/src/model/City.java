package model;

public class City {
    private int id;
    private Nationality nationality;
    private int x;
    private int y;
    private Level level = new Level(); 

    private static int nextId;

    public City(Nationality nationality) {
        this.nationality = nationality;
        this.id = ++nextId;
    }

    public void updatePosition() {}

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

    public String toString() {
        throw new RuntimeException("Method not implemented");
    }

    public Season getSeason() {
        return level.getSeason(); 
    }
}
