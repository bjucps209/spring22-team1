package tests;

import model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SerilizationTests {

    @Test
    public void test_save_then_load() throws IOException {
        Game game = new Game();
        game.initialize(Difficulty.Easy, "lvl1.dat");
        ArrayList<Entity> entityList = new ArrayList<Entity>();
        entityList.add(new City(new Coordinate(39, 42), 0, new SimpleIntegerProperty(3), 3.4, Nationality.Enemy, false, 1.0, CityType.Standard));
        entityList.add(new Troop(new Coordinate(), 0, 2, 180, new Coordinate(), 1, Nationality.Player, false));
        entityList.add(new Projectile(new Coordinate(100, 100), 0, 100, 180, new Coordinate(), 2));
        entityList.add(new Weather(new Coordinate(40, 40), 7, 3, 180, new Coordinate(), WeatherType.Blizzard));
        game.setEntityList(entityList);
        game.save();
        
        Game loadedGame = new Game();
        game.initialize(Difficulty.Easy, "savedGame.dat");

        City chicago = (City) loadedGame.getEntityList().get(0);
        assertEquals(39, chicago.getLocation().getX());
        assertEquals(42, chicago.getLocation().getY());
        assertFalse(chicago.isSelected());
        assertEquals(Nationality.Enemy, chicago.getNationality());

        Troop rambo = (Troop) loadedGame.getEntityList().get(1);
        assertEquals(0, rambo.getTurnCount());
        assertEquals(2, rambo.getSpeed());
        assertEquals(1, rambo.getHealth());
        assertEquals(Nationality.Player, rambo.getNationality());

        Projectile arrow = (Projectile) loadedGame.getEntityList().get(2);
        assertTrue(arrow.getLocation().isEqual(new Coordinate(100, 100)));
        assertEquals(2, arrow.getDamage());
        assertEquals(180, arrow.getHeading());
        assertEquals(100, arrow.getSpeed());

        Weather snowy = (Weather) loadedGame.getEntityList().get(3);
        assertEquals(WeatherType.Blizzard, snowy.getType());
        assertTrue(snowy.getLocation().isEqual(new Coordinate(40, 40)));
        assertEquals(7, snowy.getTurnCount());
    }
    
}
