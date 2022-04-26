//-----------------------------------------------------------
//File:   Constants.java
//Desc:   File holds all of the constant variables for the
// program. Includes numbers for logic and image links.
//-----------------------------------------------------------

package model;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Window;

public class Constants {

        // Troop variables
        public static int troopRadius = 4;
        public final static int troopRingRadius = 10;
        public final static int standardTroopSpeed = 3;
        public final static int fastTroopSpeed = 5;
        public final static int blizzardTroopspeed = 1;
        public final static int standardTroopHealth = 1;
        public final static int strongTroopHealth = 2;

        // City variables
        public final static int cityRadius = 35;
        public final static int cityPopulationLimit = 30;
        public final static int cityPopulationUpdateSpeed = 6;

        // Game Pane variables
        public final static int windowHeight = 525;
        public final static int windowWidth = 1025;

        // Weather variables
        public final static int weatherRadius = 40;
        public final static double weatherSpeed = 2;

        // Projectile variable
        public final static int projectileSpeed = 10;

        // Game Speed variable
        public final static int tickSpeed = 100;

        // Game Image Links
        public static Image blizzardImage = new Image(
                        "https://upload.wikimedia.org/wikipedia/commons/d/db/Weather-Snow.png");
        public static Image lightningImage = new Image(
                        "https://upload.wikimedia.org/wikipedia/commons/f/f1/Bitcoin_lightning_logo.png");
        public static Image floodImage = new Image(
                        "https://upload.wikimedia.org/wikipedia/commons/8/82/Light_Rain_Cloud_by_Sara.png");

        public static final Image playButton = new Image("/NormalImages/play-button(pressed).png");
        public static final Image playButtonPressed = new Image("/NormalImages/play-button.png");
        public static final Image pauseButton = new Image("/NormalImages/pause(pressed).png");
        public static final Image pauseButtonPressed = new Image("/NormalImages/pause.png");
        public static Image playerImage = new Image("/NormalImages/playerTroop.png");
        public static Image enemyImage = new Image("/NormalImages/enemyTroop.png");
        public static Image enemyStrong = new Image("/NormalImages/enemyStrong.png");
        public static Image playerStrong = new Image("/NormalImages/playerStrong.png");
        public static Image enemyFast = new Image("/NormalImages/enemyFast.png");
        public static Image playerFast = new Image("/NormalImages/playerFast.png");
        public static Image fastCity = new Image("/images/fastcastle.png");
        public static Image strongCity = new Image("/images/strongcastle.png");
        public static Image cityImage = new Image(
                        "/NormalImages/Castle.png");

        // AudioClip references
        public static String summerMusic = "/Assets/summer1.mp3";
        public static String fallMusic = "/Assets/autumn1.mp3";
        public static String winterMusic = "/Assets/winter1.mp3";
        public static String springMusic = "/Assets/spring1.mp3";

        // Method switches constants to reflect "Easter Egg" method.
        public static void switchImages() {
                // Switch images
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

                // Switch Music
                springMusic = "/Assets/schauborgan.mp3";
                summerMusic = "/Assets/schauborgan.mp3";
                fallMusic = "/Assets/schauborgan.mp3";
                winterMusic = "/Assets/schauborgan.mp3";

                // Update troop radius so you can see everyone's beautiful faces.
                troopRadius = 10;
        }

}