package model;

import javafx.scene.image.Image;

public class Constants {
    public final static int troopRadius = 4;
    public final static int cityRadius = 35;
    public final static int standardTroopSpeed = 5;
    public final static int fastTroopSpeed = 8;
    public final static int standardTroopHealth = 1;
    public final static int windowHeight = 900;
    public final static int windowWidth = 1400;
    public final static int strongTroopHealth = 2;
    public final static int cityPopulationLimit = 30;
    public final static int troopRingRadius = 10;
    public final static int tickSpeed = 200;
    public static final Image blizzardImage = new Image("https://commons.wikimedia.org/wiki/File:Snowstorm.svg.png");
    public static final Image cityImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/BSicon_Castle.svg/32px-BSicon_Castle.svg.png");
    public static final Image playerImage = new Image("playerTroop.png");
    public static final Image enemyImage = new Image("enemyTroop.png");
}
