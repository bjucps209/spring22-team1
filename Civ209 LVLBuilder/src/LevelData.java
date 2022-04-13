import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    LevelData() {
        level = new Level(); 
    }

    LevelData(Level level) {
        this.level = level; 
    }

    
    public void save() throws IOException{
        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream("createdLevel.dat"))) {
            wr.writeUTF("Level");
            wr.writeInt(level.getCities().size());
            List<Entity> entityList = level.getCities(); 
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }

    public void load() throws IOException{
        try (DataInputStream rd = new DataInputStream(new FileInputStream("createdLevel.dat"))) {
            if (rd.readUTF().equals("Civilization209")) {
                
                char s = rd.readChar();
                SeasonType season = level.getSeason(); 

                season = s == 'W' ? SeasonType.Winter
                        : s == 'F' ? SeasonType.Fall : s == 'S' ? SeasonType.Summer : SeasonType.Spring;

                int size = rd.readInt();
                for (int i = 0; i < size; i++) {

                    Entity entity;
                    Coordinate location = new Coordinate(rd.readDouble(), rd.readDouble());
                    int turnCount = rd.readInt();

                    double incrementRate = rd.readDouble();
                    char nation = rd.readChar();
                    Nationality nationality = nation == 'P' ? Nationality.Player
                            : nation == 'E' ? Nationality.Enemy : Nationality.Neutral;
                    boolean selected = rd.readBoolean();
                    double fireRate = rd.readDouble();
                    char cityT = rd.readChar();
                    CityType cityType = cityT == 'S' ? CityType.Standard
                            : cityT == 'F' ? CityType.Fast : CityType.Strong;
                    IntegerProperty intprop = new SimpleIntegerProperty(10); 
                    entity = new City(location, turnCount, intprop , incrementRate, nationality, selected,
                            fireRate, cityType);
                    level.add((City) entity);
                }
            }
        }
    }
}
