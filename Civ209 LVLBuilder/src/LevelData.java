import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.City;
import model.CityType;
import model.Coordinate;
import model.Entity;
import model.Level;
import model.Nationality;
import model.SeasonType;

public class LevelData {

    Level level;

    public Level getLevel() {
        return level;
    }

    LevelData() {
        level = new Level();
    }

    LevelData(Level level) {
        this.level = level;
    }

    public void save() throws IOException {

        List<City> entityList = level.getCities();

        ArrayList<City> playerCities = new ArrayList<City>();

        for (City city : entityList) {
            if (city.getNationality() == Nationality.Player) {
                playerCities.add(city);
            }
        }

        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream("../Civ209/Levels/Level1.dat"))) {
            wr.writeUTF("Civilization209");
            wr.writeInt(300 + (entityList.size() * 16) * 1);
            wr.writeChar(level.getSeason() == SeasonType.Winter ? 'W'
                    : level.getSeason() == SeasonType.Fall ? 'F'
                            : level.getSeason() == SeasonType.Summer ? 'S'
                                    : level.getSeason() == SeasonType.Spring ? 's' : 'N');
            // this.difficulty = diff == 'E' ? Difficulty.Easy : diff == 'M' ?
            // Difficulty.Medium : Difficulty.Hard;
            wr.writeChar('E');
            wr.writeInt(playerCities.size());
            wr.writeDouble(1);
            wr.writeInt(entityList.size());
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }
    // Following code writes with LevelBuilder Save Pattern:

    // try (DataOutputStream wr = new DataOutputStream(new
    // FileOutputStream("createdLevel.dat"))) {
    // wr.writeUTF("Level");
    // String season = null;
    // if (level.getSeason() == SeasonType.Fall) {
    // season = "Fall";
    // }
    // if (level.getSeason() == SeasonType.Spring) {
    // season = "Spring";
    // }
    // if (level.getSeason() == SeasonType.Winter) {
    // season = "Winter";
    // }
    // if (level.getSeason() == SeasonType.Summer) {
    // season = "Summer";
    // }
    // wr.writeUTF("" + level.getSeason());
    // wr.writeUTF(Integer.toString(level.getCities().size()));
    // List<City> entityList = level.getCities();
    // for (City entity : entityList) {
    // City city = entity;
    // String id = Integer.toString(city.getId());
    // wr.writeUTF(id);
    // String x = Integer.toString(city.getX());
    // wr.writeUTF(x);
    // String y = Integer.toString(city.getY());
    // wr.writeUTF(y);
    // String nationality = "N";
    // if (city.getNationality() == Nationality.Enemy) {
    // nationality = Character.toString('E');
    // }
    // if (city.getNationality() == Nationality.Player) {
    // nationality = Character.toString('P');
    // }
    // if (city.getNationality() == Nationality.Neutral) {
    // nationality = Character.toString('N');
    // }
    // wr.writeUTF(nationality);
    // }
    // }
    // }

    public Level load() throws IOException {
        this.level = new Level();
        try (DataInputStream rd = new DataInputStream(new FileInputStream("../Civ209/Levels/Level1.dat"))) {
            if (rd.readUTF().equals("Civilization209")) {

                rd.readInt();
                char s = rd.readChar();
                if (s == 'W') {
                    level.setSeason(SeasonType.Winter);
                } else if (s == 'F') {
                    level.setSeason(SeasonType.Fall);
                } else if (s == 'S') {
                    level.setSeason(SeasonType.Summer);
                } else if (s == 's') {
                    level.setSeason(SeasonType.Spring);
                } else {
                    level.setSeason(SeasonType.None);
                }
                rd.readChar();
                rd.readInt();
                rd.readDouble();
                int size = rd.readInt();
                for (int i = 0; i < size; i++) {
                    String citytype = rd.readUTF();
                    double x = rd.readDouble();
                    double y = rd.readDouble();
                    Coordinate location = new Coordinate(x, y);
                    rd.readInt();
                    rd.readInt();
                    IntegerProperty popProperty = new SimpleIntegerProperty();
                    rd.readDouble();
                    char nation = rd.readChar();
                    Nationality getnationality = nation == 'P' ? Nationality.Player
                            : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
                    rd.readBoolean();
                    rd.readDouble();
                    char cityT = rd.readChar();
                    CityType cityType = cityT == 'S' ? CityType.Standard
                            : cityT == 'F' ? CityType.Fast : CityType.Strong;
                    City city = new City(location, 0, popProperty, getnationality, false,
                            cityType, null);
                    city.setX((int) x);
                    city.setY((int) y);
                    city.setId(i + 1);
                    level.add(city);
                }
            }
        }
        // return new City(location, turnCount, popProperty, incrementRate, nationality,
        // selected,
        // fireRate, cityType);
        // if (rd.readUTF().equals("Level")) {

        // String s = rd.readUTF();

        // if (s.equals("Winter")) {
        // level.setSeason(SeasonType.Winter);
        // }
        // if (s.equals("Summer")) {
        // level.setSeason(SeasonType.Summer);
        // }
        // if (s.equals("Spring")) {
        // level.setSeason(SeasonType.Spring);
        // }
        // if (s.equals("Fall")) {
        // level.setSeason(SeasonType.Fall);
        // }

        // int size = Integer.parseInt(rd.readUTF());
        // for (int i = 0; i < size; i++) {

        // Entity entity;
        // // Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
        // int id = Integer.parseInt(rd.readUTF());
        // int x = Integer.parseInt(rd.readUTF());
        // int y = Integer.parseInt(rd.readUTF());
        // String nation = rd.readUTF();
        // Nationality nationality = Nationality.Neutral;
        // if (nation.equals("P")) {
        // nationality = Nationality.Player;
        // }
        // if (nation.equals("E")) {
        // nationality = Nationality.Enemy;
        // }
        // if (nation.equals("N")) {
        // nationality = Nationality.Neutral;
        // }

        // IntegerProperty intprop = new SimpleIntegerProperty(10);
        // Coordinate location = new Coordinate(x, y);
        // entity = new City(location, 0, intprop, 0, nationality, false,
        // 0.0, CityType.Standard);
        // City city = (City) entity;
        // city.setX(x);
        // city.setY(y);
        // city.setId(id);
        // level.add(city);
        // }
        // }
        // }
        return this.level;
    }
}
