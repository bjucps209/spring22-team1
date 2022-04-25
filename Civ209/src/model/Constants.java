package model;

import javafx.scene.image.Image;

public class Constants {
    public static int troopRadius = 4;
    public final static int cityRadius = 35;
    public final static int standardTroopSpeed = 5;
    public final static int fastTroopSpeed = 9;
    public final static int blizzardTroopspeed = 2;
    public final static int standardTroopHealth = 1;
    public final static int windowHeight = 525;
    public final static int windowWidth = 1025;
    public final static int strongTroopHealth = 2;
    public final static int cityPopulationLimit = 30;
    public final static int troopRingRadius = 10;
    public final static int tickSpeed = 200;
    public final static int projectileSpeed = 10;
    public static Image blizzardImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/d/db/Weather-Snow.png");
    public static Image lightningImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/f/f1/Bitcoin_lightning_logo.png");
    public static Image floodImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/8/82/Light_Rain_Cloud_by_Sara.png");
    public static Image cityImage = new Image(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/BSicon_Castle.svg/32px-BSicon_Castle.svg.png");
    public static final Image playButton = new Image("play-button(pressed).png");
    public static final Image playButtonPressed = new Image("play-button.png");
    public static final Image pauseButton = new Image("pause(pressed).png");
    public static final Image pauseButtonPressed = new Image("pause.png");
    public static Image playerImage = new Image("playerTroop.png");
    public static Image enemyImage = new Image("enemyTroop.png");
    public static Image enemyStrong = new Image("enemyStrong.png");
    public static Image playerStrong = new Image("playerStrong.png");
    public static Image enemyFast = new Image("enemyFast.png");
    public static Image playerFast = new Image("playerFast.png");
    public static Image fastCity = new Image("/images/fastcastle.png");
    public static Image strongCity = new Image("/images/strongcastle.png");
    public final static int weatherRadius = 40;
    public final static double weatherSpeed = 4;

    public static void switchImages() {
        blizzardImage = new Image("/Images/Blizzard.png");
        lightningImage = new Image("/Images/Lightning.png");
        floodImage = new Image("/Images/Flood.png");
        cityImage = new Image("/Images/NormalCity.png");
        fastCity = new Image("/Images/FastCity.png");
        strongCity = new Image("/Images/StrongCity.png");
        playerImage = new Image("/Images/PlayerNormal.png");
        playerFast = new Image("/Images/PlayerFast.png");
        playerStrong = new Image("/Images/PlayerStrong.png");
        enemyFast = new Image("/Images/EnemyFast.png");
        enemyStrong = new Image("/Images/EnemyStrong.png");
        enemyImage = new Image("/Images/EnemyNormal.png");
        troopRadius = 8;
    }

}
