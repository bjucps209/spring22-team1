package tests;

import model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

import javafx.beans.property.SimpleIntegerProperty;

public class SerilizationTests {

    @Test
    public void test_save_then_load() throws IOException {
        Game game = new Game();
        ArrayList<Entity> entityList = new ArrayList<Entity>();
        entityList.add(new City(new Coordinate(39, 42), 0, new SimpleIntegerProperty(3), 3.4, Nationality.Enemy, false,
                1.0, CityType.Standard, game));
        entityList.add(new Troop(new Coordinate(), 0, 2, 180, new Coordinate(), 1, Nationality.Player, false,
                DestinationType.Coordinate, CityType.Standard, game));
        // Weather destinations are always null as per design
        entityList.add(new Weather(new Coordinate(390, 123), 7, 8, 9, null, WeatherType.Blizzard));

        game.setEntityList(entityList);
        assertEquals(3, entityList.size());
        game.save("TESTsavedGame.dat");

        Game loadedGame = new Game();
        loadedGame.load("TESTsavedGame.dat");

        assertEquals(3, entityList.size());

        City chicago = (City) loadedGame.getEntityList().get(0);
        assertEquals(39, chicago.getLocation().getX(), 0);
        assertEquals(42, chicago.getLocation().getY(), 0);
        assertFalse(chicago.isSelected());
        assertEquals(Nationality.Enemy, chicago.getNationality());

        Troop rambo = (Troop) loadedGame.getEntityList().get(1);
        assertEquals(0, rambo.getTurnCount());
        assertEquals(2, rambo.getSpeed(), 0);
        assertEquals(1, rambo.getHealth());
        assertEquals(Nationality.Player, rambo.getNationality());

        Weather chilly = (Weather) loadedGame.getEntityList().get(2);
        assertEquals(390, chilly.getLocation().getX(), 0);
        assertEquals(9, chilly.getHeading(), 0);
        assertEquals(8, chilly.getSpeed(), 0);
        assertEquals(null, chilly.getDestination());
        assertEquals(WeatherType.Blizzard, chilly.getType());


        // Didn't add serialization tests for projectiles and Weather yet, as they are
        // buggy and not yet implemented in the model.
    }

}
