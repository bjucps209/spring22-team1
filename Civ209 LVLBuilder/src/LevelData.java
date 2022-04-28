//--------------------------------------------------------------------------------
//File:   LevelData.java
//Desc:   File contains logic for writing all of the level information to the file.
//--------------------------------------------------------------------------------

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

    /**
     * Writes to the file and saves all the information
     * 
     * @throws IOException
     */
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
            wr.writeChar('E');
            wr.writeInt(playerCities.size());
            wr.writeDouble(1);
            wr.writeInt(entityList.size());
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }

    /**
     * Loads the file by reading the file contents and returning it
     * 
     * @return
     * @throws IOException
     */
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
                    String cityidentifier = rd.readUTF();
                    double x = rd.readDouble();
                    double y = rd.readDouble();
                    Coordinate location = new Coordinate(x, y);
                    rd.readInt();
                    rd.readInt();
                    IntegerProperty popProperty = new SimpleIntegerProperty();
                    char nation = rd.readChar();
                    Nationality getnationality = nation == 'P' ? Nationality.Player
                            : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
                    rd.readBoolean();
                    char cityT = rd.readChar();
                    CityType cityType = cityT == 's' ? CityType.Standard
                            : cityT == 'F' ? CityType.Fast : CityType.Strong;
                    City city = new City(location, 0, popProperty, getnationality, false,
                            cityType, null);
                    city.setId(i + 1);
                    level.add(city);
                }
            }
        }
        return this.level;
    }
}
