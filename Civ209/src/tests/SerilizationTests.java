//-----------------------------------------------------------
//File:   SerializationTests.java
//Desc:   Program tests serialization methods.
//-----------------------------------------------------------

package tests;

import model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

import javafx.beans.property.SimpleIntegerProperty;

public class SerilizationTests {

        /**
         * Serialization tests saving all types and then loading them in.
         * Runs with TESTsavedGame.dat
         * 
         * @throws IOException
         */
        @Test
        public void test_save_then_load() throws IOException {
                Game game = new Game();
                ArrayList<Entity> entityList = new ArrayList<Entity>();
                entityList.add(new City(new Coordinate(39, 42), 0, new SimpleIntegerProperty(3), Nationality.Enemy,
                                false,
                                CityType.Standard, game));
                entityList.add(new Troop(new Coordinate(), 0, 2, 180, new Coordinate(), 1, Nationality.Player, false,
                                DestinationType.Coordinate, CityType.Standard, game));
                // Weather destinations are always null as per design
                entityList.add(new Weather(new Coordinate(390, 123), 7, 8, 9, null, WeatherType.Blizzard));
                entityList.add(new Projectile(new Coordinate(), 0, 100, 270, new Coordinate(100, 100), 9));
                game.setEntityList(entityList);

                assertEquals(4, entityList.size());
                game.save("TESTsavedGame.dat");

                Game loadedGame = new Game();
                loadedGame.load("TESTsavedGame.dat");

                assertEquals(4, entityList.size());

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

                Projectile beam = (Projectile) loadedGame.getEntityList().get(3);
                assertTrue(beam.getLocation().isEqual(new Coordinate()));
                assertEquals(0, beam.getTurnCount());
                assertEquals(100, beam.getSpeed(), 0);
                assertEquals(9, beam.getDamage());
                assertEquals(270, beam.getHeading(), 0);
        }

        /**
         * Tests the save of multiple cities to ensure that Nationality and CityType is
         * loaded in correctly.
         * Runs with TEST2savedGame.dat
         * 
         * @throws IOException
         */
        @Test
        public void test_multipleCity() throws IOException {

                Game game = new Game();
                ArrayList<Entity> entityList = new ArrayList<Entity>();

                entityList.add(new City(new Coordinate(39, 42), 0, new SimpleIntegerProperty(3), Nationality.Enemy,
                                false,
                                CityType.Standard, game));
                entityList.add(new City(new Coordinate(290, 270), 0, new SimpleIntegerProperty(9), Nationality.Player,
                                true,
                                CityType.Fast, game));
                entityList.add(new City(new Coordinate(23, 32), 0, new SimpleIntegerProperty(31), Nationality.Neutral,
                                false,
                                CityType.Strong, game));
                game.setEntityList(entityList);

                assertEquals(3, entityList.size());
                game.save("TEST2savedGame.dat");

                Game loadedGame = new Game();
                loadedGame.load("TEST2savedGame.dat");

                City greenville = (City) loadedGame.getEntityList().get(0);
                assertEquals(39, greenville.getLocation().getX(), 0);
                assertEquals(42, greenville.getLocation().getY(), 0);
                assertEquals(3, greenville.getPopulation());
                assertEquals(Nationality.Enemy, greenville.getNationality());
                assertFalse(greenville.isSelected());
                assertEquals(CityType.Standard, greenville.getType());

                City newYork = (City) loadedGame.getEntityList().get(1);
                assertEquals(290, newYork.getLocation().getX(), 0);
                assertEquals(270, newYork.getLocation().getY(), 0);
                assertEquals(9, newYork.getPopulation());
                assertEquals(Nationality.Player, newYork.getNationality());
                assertTrue(newYork.isSelected());
                assertEquals(CityType.Fast, newYork.getType());

                City dallas = (City) loadedGame.getEntityList().get(2);
                assertEquals(23, dallas.getLocation().getX(), 0);
                assertEquals(32, dallas.getLocation().getY(), 0);
                assertEquals(31, dallas.getPopulation());
                assertEquals(Nationality.Neutral, dallas.getNationality());
                assertFalse(dallas.isSelected());
                assertEquals(CityType.Strong, dallas.getType());

        }

}
