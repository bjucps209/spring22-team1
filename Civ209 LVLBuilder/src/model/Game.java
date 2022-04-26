//-----------------------------------------------------------
//File:   Game.java
//Desc:   This program instantiates an image and handles game state.
//-----------------------------------------------------------
package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class Game {
    private Timeline timer;
    private ArrayList<Entity> entityList = new ArrayList<>();
    private String fileName;
    private double gameSpeed;
    private IntegerProperty scoreProperty = new SimpleIntegerProperty();
    private Computer computer = new Computer();
    private int numPlayerCitiesLeft;
    private SeasonType season;
    private ArrayList<Entity> deleteEntityList = new ArrayList<>();
    private Difficulty difficulty;
    private int turncount = 0;
    Random rand = new Random();
    private MakeWeather onMakeWeather;
    private FireProjectiles onFireProjectile;
    private ArrayList<Troop> selectedTroops = new ArrayList<>();
    private City selectedCity;
    private EntityManager entityManager;
    private GameOverObserver gameOver;
    private boolean endGame = false;

    /**
     * instantiates game from lvl with a computer of level difficulty
     * 
     * @param difficulty difficulty of computer
     * @param lvlName    id of level played to load in from binary file
     */
    public void initialize(Difficulty difficulty, String lvlName) {
        computer.setDifficulty(Difficulty.Easy);
        try {
            load(lvlName);
        } catch (IOException e) {
            try {
                load("Civ209/Levels/DemoLevel.dat");
            } catch (IOException xe) {
                System.out.println("fatalError! " + xe);
                System.exit(1);
            }
        }
        startTimer();
    }

    /**
     * selects troops of type nationality inside box constructed by coord1, coord2,
     * so that they can be told to go to a destination
     * 
     * @param coord1      top left corner of box
     * @param coord2      bottom right corner of box
     * @param nationality nationality of troops to select
     */
    public ArrayList<Troop> selectTroops(Coordinate coord1, Coordinate coord2, Nationality nationality) {
        selectedTroops.clear();
        for (Entity entity : getEntityList()) {
            if (entity instanceof Troop) {
                Troop troop = (Troop) entity;
                if (troop.getNationality() == nationality) {
                    Coordinate location = troop.getLocation();
                    if (location.getX() >= coord1.getX() && location.getY() >= coord1.getY()
                            && location.getX() <= coord2.getX() && location.getY() <= coord2.getY()) {
                        troop.setSelected(true);
                        selectedTroops.add(troop);
                    }
                }
            }
        }
        return selectedTroops;
    }

    /**
     * checks numCitiesLeft and score to see if game should be over
     */
    public void gameEnd() {
        /**
         * checks numCitiesLeft and score to see if game should be over
         */
        if (getScore() <= 0) {
            stopTimer();
            endGame = true;

            gameOver.recognizeGameOver("Time limit exceeded", getScore());
        }
        if (getEntityList().stream().filter(e -> e instanceof City && ((City) e).getNationality() == Nationality.Player)
                .count() == 0) {
            stopTimer();

            // Your move, Mr. Moffitt
            gameOver.recognizeGameOver("Enemy conquest", scoreProperty.get());
        } else {
            if (getEntityList().stream()
                    .filter(e -> e instanceof City && ((City) e).getNationality() == Nationality.Enemy).count() == 0) {
                stopTimer();
                endGame = true;
                gameOver.recognizeGameOver("Player conquest", scoreProperty.get());
            }
        }
    }

    /**
     * loads in a game under the id lvlName
     * 
     * @param lvlName id for what to load in
     * @throws IOException in case file not there
     */
    public void load(String lvlName) throws IOException {
        try (DataInputStream rd = new DataInputStream(new FileInputStream(lvlName))) {
            if (rd.readUTF().equals("Civilization209")) {
                entityList.clear();
                setScore(rd.readInt());
                char s = rd.readChar();
                this.season = s == 'W' ? SeasonType.Winter
                        : s == 'F' ? SeasonType.Fall
                                : s == 'S' ? SeasonType.Summer : s == 's' ? SeasonType.Spring : SeasonType.None;
                char diff = rd.readChar();
                this.difficulty = diff == 'E' ? Difficulty.Easy : diff == 'M' ? Difficulty.Medium : Difficulty.Hard;
                this.numPlayerCitiesLeft = rd.readInt();

                this.gameSpeed = rd.readDouble();
                int size = rd.readInt();

                for (int i = 0; i < size; i++) {

                    Entity entity;
                    String entityType = rd.readUTF();

                    if (entityType.equals("City"))
                        entity = City.load(rd);
                    else if (entityType.equals("Troop"))
                        entity = Troop.load(rd);
                    else if (entityType.equals("Projectile"))
                        entity = Projectile.load(rd);
                    else
                        entity = Weather.load(rd, entityType);

                    entityList.add(entity);
                }
            }
        }
    }

    public void update() {
        if (turncount >= 10 && !endGame) {
            gameEnd();
        }
        if (endGame)
            return;
        computer.executeAction(this);
        turncount++;
        if (turncount % 5 == 0)
            setScore(getScore() - 1);
        deleteEntityList.stream().forEach(e -> {
            entityManager.removeEntity(e);
        });

        for (Entity entity : deleteEntityList) {
            entityList.remove(entity);
        }

        deleteEntityList.clear();
        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
        // troops.stream().forEach(e -> e.setGame(this));
        // moveTroopToField(troops, destination);
        // getEntityList().addAll(troops);
        // return troops;
        // TODO work on Projectile
        // Ik it looks bad, but I promise it's the way it is for a reason
        for (Entity entity : entityList) {
            entity.update();
            if (entity instanceof City) {
                City city = (City) entity;
                Projectile proj = city.fireProjectile(this);
                projectiles.add(proj);
                // System.out.println("everything is fine");
            }
        }

        for (Projectile proj : projectiles) {
            if (proj != null) {
                renderProjectile(proj);
            }
        }
        if (turncount % 50 == 0) {
            onMakeWeather.onMakeWeather();
        }
        // Check to see if troops need to be destroyed by weather
        getEntityList().stream().filter(e -> e instanceof Troop)
                .filter(e -> checkInWeather(((Troop) e).getLocation())).forEach(e -> deleteTroopWeather((Troop) e));
        // check if the weather is in bounds of the pane
        getEntityList().stream().filter(e -> e instanceof Weather).forEach(e -> checkInBounds((Weather) e));

    }

    public void deSelect() {
        if (selectedCity != null) {
            selectedCity.setSelected(false);
        }
        selectedCity = null;
        for (Troop troop : selectedTroops) {
            troop.setSelected(false);
        }
        selectedTroops.clear();
    }

    public ArrayList<Troop> sendTroopsFromCity(City selectedCity, Coordinate destination, double percentage) {
        ArrayList<Troop> troops = selectedCity.sendTroops(percentage, destination,
                selectedCity.getType(),
                DestinationType.City);
        troops.stream().forEach(e -> {
            e.setDestination(destination);
            e.setGame(this);
        });
        return troops;
    }

    public ArrayList<Troop> sendTroopsFromGround(ArrayList<Troop> troops, Coordinate destination,
            DestinationType destType) {
        for (Troop troop : troops) {
            troop.setDestination(destination);
            troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                    : Constants.standardTroopSpeed);
            troop.setHeading(troop.figureHeading(destination));
            troop.setDestinationType(destType);
        }
        return troops;
    }

    public ArrayList<Troop> deployTroops(boolean pointInCircle, Coordinate cityCenter, Coordinate destination,
            double percentage) {
        if (getSelectedCity() != null) {
            if (pointInCircle) {
                if (cityCenter == null || cityCenter != getSelectedCity().getLocation()) {
                    ArrayList<Troop> troops = getSelectedCity().sendTroops(percentage, destination,
                            getSelectedCity().getType(),
                            DestinationType.City);
                    troops.stream().forEach(e -> e.setGame(this));
                    getEntityList().addAll(troops);
                    return troops;
                } else {
                    return new ArrayList<Troop>();
                }
            } else {
                ArrayList<Troop> troops = getSelectedCity().sendTroops(percentage, destination,
                        getSelectedCity().getType(),
                        DestinationType.Coordinate);
                troops.stream().forEach(e -> e.setGame(this));
                moveTroopToField(troops, destination);
                getEntityList().addAll(troops);
                return troops;
            }
        } else {
            if (pointInCircle) {
                for (Troop troop : selectedTroops) {
                    troop.setDestination(cityCenter);
                    troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                            : Constants.standardTroopSpeed);
                    troop.setHeading(troop.figureHeading(cityCenter));
                    troop.setDestinationType(DestinationType.City);
                }
                return selectedTroops;
            } else {
                ArrayList<Troop> troops = new ArrayList<>();
                for (Troop troop : selectedTroops) {
                    troops.add(troop);
                    moveTroopToField(troops, destination);
                }
                for (Troop troop : troops) {
                    troop.setSpeed(troop.getTroopType() == CityType.Fast ? Constants.fastTroopSpeed
                            : Constants.standardTroopSpeed);
                }
                return troops;
            }
        }
    }

    /**
     * stops timer and saves all objects to saved game portion of game file
     * 
     * @throws IOException in case file not there
     */
    public void save(String lvlName) throws IOException {
        /**
         * stop timer
         */
        if (lvlName.equals(""))
            lvlName = "savedGame.dat";
        try (DataOutputStream wr = new DataOutputStream(new FileOutputStream(lvlName))) {
            wr.writeUTF("Civilization209");
            wr.writeInt(getScore());
            wr.writeChar(this.season == SeasonType.Winter ? 'W'
                    : this.season == SeasonType.Fall ? 'F' : this.season == SeasonType.Summer ? 'S' : 's');
            // this.difficulty = diff == 'E' ? Difficulty.Easy : diff == 'M' ?
            // Difficulty.Medium : Difficulty.Hard;
            wr.writeChar(this.difficulty == Difficulty.Easy ? 'E' : this.difficulty == Difficulty.Medium ? 'M' : 'H');
            wr.writeInt(numPlayerCitiesLeft);
            wr.writeDouble(gameSpeed);

            wr.writeInt(entityList.size());
            for (Entity entity : entityList) {
                entity.serialize(wr);
            }
        }
    }

    /**
     * Generate weather
     * 
     * @return
     */
    public Weather makeWeather() {
        int screenSide = rand.nextInt(4);
        int heading;
        int coordX = 30;
        int coordY = 30;
        WeatherType type = null;
        int typeNum = rand.nextInt(3); // 0, 3
        // determine the weather type
        if (typeNum == 0) {
            type = WeatherType.Blizzard;
        } else if (typeNum == 1) {
            type = WeatherType.Flood;
        } else if (typeNum == 2) {
            type = WeatherType.LightningStorm;
        }

        // determine which side of the screen the weather starts on
        if (screenSide == 0) { // bottom
            heading = nextInt(225, 315);
            coordY = Constants.windowHeight - 39;

            if (heading >= 270) {
                coordX = nextInt(39, Constants.windowWidth / 2);
            } else {
                coordX = nextInt((Constants.windowWidth) / 2, Constants.windowWidth - 39);
            }

        } else if (screenSide == 1) { // left
            int check = rand.nextInt(2);
            coordX = 39;
            if (check == 0) {
                heading = nextInt(315, 360);
                coordY = nextInt((Constants.windowHeight) / 2, Constants.windowHeight - 39);

            } else {
                heading = nextInt(0, 45);
                coordY = nextInt(39, (Constants.windowHeight) / 2);
            }

        } else if (screenSide == 2) { // top
            heading = nextInt(45, 135);
            coordY = 39;

            if (heading <= 90) {
                coordX = nextInt(39, (Constants.windowWidth - 39) / 2);
            } else {
                coordX = nextInt((Constants.windowWidth) / 2, (Constants.windowWidth - 39));
            }

        } else { // right

            int check = rand.nextInt(2);
            coordX = Constants.windowWidth - 39;
            if (check == 0) {
                heading = nextInt(180, 225);
                coordY = nextInt((Constants.windowHeight) / 2, (Constants.windowHeight - 39));

            } else {
                heading = nextInt(135, 180);
                coordY = nextInt(39, (Constants.windowHeight) / 2);
            }

        }

        Weather weather = new Weather(new Coordinate(coordX, coordY), turncount, Constants.weatherSpeed, heading, null,
                type);
        getEntityList().add(weather);
        return weather;
    }

    /**
     * Checks if the weather is in the bounds of the screen. If it isn't, delete the
     * weather
     */
    public void checkInBounds(Weather w) {
        if (w.getLocation().getX() > Constants.windowWidth - 39 || w.getLocation().getX() < 39
                || w.getLocation().getY() > Constants.windowHeight - 39 ||
                w.getLocation().getY() < 39) {
            deleteEntityList.add(w);
        }

    }

    // returns true if the entity is in the weather, false otherwise
    public boolean checkInWeather(Coordinate e) {
        boolean pointInCircle = false;
        for (Entity entity : getEntityList()) {
            if (entity instanceof Weather) {
                Weather weatherEntity = (Weather) entity;
                if (Math.sqrt(Math.pow(weatherEntity.getLocation().getX() - e.getX(), 2) + Math
                        .pow(weatherEntity.getLocation().getY() - e.getY(), 2)) <= Constants.weatherRadius
                                + Constants.troopRadius) {
                    pointInCircle = true;
                    break;
                }
            }
        }
        return pointInCircle;
    }

    // delete the troop if it is inside the weather
    public void deleteTroopWeather(Troop troop) {
        Coordinate location = troop.getLocation();
        if (checkInWeather(location)) {
            getDeleteEntityList().add(troop);
        }
    }

    public int nextInt(int lowerBound, int upperBound) {
        return (int) ((Math.random() * (upperBound - lowerBound)) + lowerBound);
    }

    public void setUpComputer(ComputerObserver window) {
        computer.setObs(window);
    }

    public City getCityHit(Coordinate coordinate) {
        City city = null;
        for (Entity entity : getEntityList()) {
            if (entity instanceof City) {
                City cityEntity = (City) entity;
                if (Math.pow(coordinate.getX() - cityEntity.getLocation().getX(), 2) + Math
                        .pow(coordinate.getY() - cityEntity.getLocation().getY(), 2) <= Math.pow(Constants.cityRadius,
                                2)) {
                    city = cityEntity;
                    break;
                }
            }
        }
        return city;
    }

    public boolean checkInCity(Coordinate e) {
        boolean pointInCircle = false;
        for (Entity entity : getEntityList()) {
            if (entity instanceof City) {
                City cityEntity = (City) entity;
                if (Math.pow(e.getX() - cityEntity.getLocation().getX(), 2) + Math
                        .pow(e.getY() - cityEntity.getLocation().getY(), 2) <= Math.pow(Constants.cityRadius, 2)) {
                    pointInCircle = true;
                    break;
                }
            }
        }
        return pointInCircle;
    }

    public Coordinate checkInCity(Coordinate e, boolean returnCity) {
        for (Entity entity : getEntityList()) {
            if (entity instanceof City) {
                City cityEntity = (City) entity;
                if (Math.pow(e.getX() - cityEntity.getLocation().getX(), 2) + Math
                        .pow(e.getY() - cityEntity.getLocation().getY(), 2) <= Math.pow(Constants.cityRadius, 2)) {
                    return cityEntity.getLocation();
                }
            }
        }
        return null;
    }

    public void moveTroopToField(ArrayList<Troop> troops, Coordinate destination) {
        int numTroops = troops.size();
        int ring = 0;
        int curTroop = 0;
        while (numTroops != 0) {
            for (int i = 0; i < Math.max(ring * 6, 1); i++) {
                if (numTroops == 0) {
                    break;
                } else {
                    numTroops--;
                    Troop troop = troops.get(curTroop);
                    double changeInHeading = (ring * 6) == 0 ? 0 : Math.toRadians((360.0 / (ring * 6.0)) * i);
                    troop.setDestination(
                            new Coordinate(
                                    Math.max(Math.min(
                                            destination.getX()
                                                    + (ring * Constants.troopRingRadius) * Math.cos(changeInHeading),
                                            Constants.windowWidth), 0),
                                    Math.max(Math.min(
                                            destination.getY()
                                                    + (ring * Constants.troopRingRadius) * Math.sin(changeInHeading),
                                            Constants.windowHeight), 0)));
                    troop.setHeading(troop.figureHeading(troop.getDestination()));
                    // troop.setSpeed(troop.getTroopType() == CityType.Fast ?
                    // Constants.fastTroopSpeed
                    // : Constants.standardTroopSpeed);
                    troop.setDestinationType(DestinationType.Coordinate);
                    curTroop++;
                }
            }
            ring++;
        }
    }

    public void deleteTroop(Troop troop) {
        // Switched from Location to destination. Because I #can
        Coordinate location = troop.getDestination();

        if (checkInCity(location)) {
            City city = getCityHit(location);
            if (city != null)
                city.recieveTroops(troop.getHealth(), troop.getNationality());
        }

        getDeleteEntityList().add(troop);
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timeline(new KeyFrame(Duration.millis(Constants.tickSpeed), e -> update()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        } else {
            timer.play();
        }
    }

    public void instantGameOver(boolean playerWin) {
        for (Entity ent : entityList) {
            if (ent instanceof City) {
                City city = (City) ent;
                if (city.getNationality() == (playerWin ? Nationality.Enemy : Nationality.Player)) {
                    city.setNationality(playerWin ? Nationality.Player : Nationality.Enemy);
                }
            }
        }
    }

    public void instantAddTroops() {
        for (Entity ent : entityList) {
            if (ent instanceof City) {
                City city = (City) ent;
                if (city.getNationality() == Nationality.Player) {
                    city.setPopulation(city.getPopulation() + Constants.cityPopulationLimit);
                }
            }
        }
    }

    public void renderProjectile(Projectile proj) {
        getEntityList().add(proj);
        onFireProjectile.onFireProjectiles(proj);
    }

    public Projectile fireProjectile() {
        for (Entity ent : entityList) {
            if (ent instanceof City) {
                City city = (City) ent;
                return city.fireProjectile(this);
            }
        }
        return null;
    }

    public void instantMakeWeather() {
        onMakeWeather.onMakeWeather();
    }

    public void stopTimer() {
        timer.stop();
    }

    public Timeline getTimer() {
        return timer;
    }

    public void setTimer(Timeline timer) {
        this.timer = timer;
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getScore() {
        return scoreProperty.get();
    }

    public void setScore(int score) {
        this.scoreProperty.set(score);
    }

    public IntegerProperty scoreProperty() {
        return scoreProperty;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public int getNumPlayerCitiesLeft() {
        return numPlayerCitiesLeft;
    }

    public void setNumPlayerCitiesLeft(int numPlayerCitiesLeft) {
        this.numPlayerCitiesLeft = numPlayerCitiesLeft;
    }

    public SeasonType getSeason() {
        return season;
    }

    public void setSeason(SeasonType season) {
        this.season = season;
    }

    public ArrayList<Entity> getDeleteEntityList() {
        return deleteEntityList;
    }

    public void setDeleteEntityList(ArrayList<Entity> deleteEntityList) {
        this.deleteEntityList = deleteEntityList;
    }

    public void setOnMakeWeather(MakeWeather onMakeWeather) {
        this.onMakeWeather = onMakeWeather;
    }

    public void setOnFireProjectiles(FireProjectiles onFireProjectile) {
        this.onFireProjectile = onFireProjectile;
    }

    public ArrayList<Troop> getSelectedTroops() {
        return selectedTroops;
    }

    public void setSelectedTroops(ArrayList<Troop> selectedTroops) {
        this.selectedTroops = selectedTroops;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public void setGameOverObserver(GameOverObserver obs) {
        gameOver = obs;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}