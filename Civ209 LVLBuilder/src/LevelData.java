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
                    : level.getSeason() == SeasonType.Fall ? 'F' : level.getSeason() == SeasonType.Summer ? 'S' : level.getSeason() == SeasonType.Spring ? 's': 'N');
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
        //Following code writes with LevelBuilder Save Pattern:
        
        // try (DataOutputStream wr = new DataOutputStream(new
        // FileOutputStream("createdLevel.dat"))) {
        // wr.writeUTF("Level");
        // String season = null; 
        // if (level.getSeason() == SeasonType.Fall) {
        //     season = "Fall"; 
        // }
        // if (level.getSeason() == SeasonType.Spring) {
        //     season = "Spring"; 
        // }
        // if (level.getSeason() == SeasonType.Winter) {
        //     season = "Winter"; 
        // }
        // if (level.getSeason() == SeasonType.Summer) {
        //     season = "Summer"; 
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

    }

    public Level load() throws IOException {
        this.level = new Level();
        try (DataInputStream rd = new DataInputStream(new FileInputStream("createdLevel.dat"))) {
            if (rd.readUTF().equals("Level")) {

                String s = rd.readUTF();

                if (s.equals("Winter")) {
                    level.setSeason(SeasonType.Winter);
                }
                if (s.equals("Summer")) {
                    level.setSeason(SeasonType.Summer);
                }
                if (s.equals("Spring")) {
                    level.setSeason(SeasonType.Spring);
                }
                if (s.equals("Fall")) {
                    level.setSeason(SeasonType.Fall);
                }

                int size = Integer.parseInt(rd.readUTF());
                for (int i = 0; i < size; i++) {

                    Entity entity;
                    // Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
                    int id = Integer.parseInt(rd.readUTF());
                    int x = Integer.parseInt(rd.readUTF());
                    int y = Integer.parseInt(rd.readUTF());
                    String nation = rd.readUTF();
                    Nationality nationality = Nationality.Neutral;
                    if (nation.equals("P")) {
                        nationality = Nationality.Player;
                    }
                    if (nation.equals("E")) {
                        nationality = Nationality.Enemy;
                    }
                    if (nation.equals("N")) {
                        nationality = Nationality.Neutral;
                    }

                    IntegerProperty intprop = new SimpleIntegerProperty(10);
                    Coordinate location = new Coordinate(x, y);
                    entity = new City(location, 0, intprop, 0, nationality, false,
                            0.0, CityType.Standard);
                    City city = (City) entity;
                    city.setX(x);
                    city.setY(y);
                    city.setId(id);
                    level.add(city);
                }
            }
        }
        return this.level;
    }
}
