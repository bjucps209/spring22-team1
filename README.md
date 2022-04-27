# spring22-team1
CPS 209 Spring Team 1 Project

## Beta

### Instructions 
  When the game begins, you will see three types of cities. The enemy cities (red) and will try to attack you, the neutral cities (grey) will not attack you, and using your cities (blue), you will be able to send troops to attack the other cities. The population of all of the cities will rise over time until they hit the max population limit. 
  
 To play, you click on one of your cities, drag the slider to choose how many troops you want to send from that city's population, and click on the city you want to sent your troops to. The troops continue to be produced over time at a one per second rate until the city hits the maximum population limit. Your troops will then march out of your city and to the other city. If the destination city is an enemy city or a neutral city, the troops will kill some of the population of the city, but if the destination city is one of your own cities, your troops will add more population to your cities. When you defeat a neutral or enemy city, the city will become one of your own.

You win when you control all of the cities on the map. You lose if all of your cities are taken over, or if your score reaches zero.  

### Work Completed 
* Game Functionality:
  The game initializes the list of entities supplied by the load function and starts the game loop. The game handles clicks by the user on cities, for selecting troops, and moving troops. There is a slider that allows the user to specify the percent of population to move out of a city at a given time. The game handles a drag box which allows the user to select troops and group them together for group movement or attacks. Cities send troops in a staggered timeline, and when sent to coordinates on the screen troops spread out in a circle formation so they don't all clump together. When troop are sent to enemy cities they decrement population on arrival. When enemy cities hit -1 troops they switch over to player troops. There are three different types of cities corresponding to three different types of troops, being standard, strong, and fast. The standard troops have standard health and speed, the strong troops have standard speed but two health, and the fast troops have double speed and standard health. When units colide with one another they both die, except in the case of strong troops, which take two hits to kill. Every 50 ticks a random kind of weather will spawn traveling in a set path across the screen. The weather will kill any troops in it's path, and will despawn upon reaching the other side. The enemy cities fight back, in easy, medium, and hard mode difficulties, which are dynamic and able to be switched mid-game.
* Serialization: 
  The save function creates a save file at the given filename. It saves the current score, state of the game, and all of the cities and troops on the map, along with their destinations, speeds, headings, population counts, and type, as relevant. When the load function is called, it loads all of the game in the exact state that it was saved in. Cities are at their populations, and troops are not only where they were when the game was saved, but heading in the same direction at the same speed, and are actively moving when the game is loaded. Code passes all unit tests.
  Save Function now saves Weather Entities.
  Save Function now saves Projectile Entities.
* Level Builder: 
  The program allows the user to place the enemy, neutral, and player cities wherever wanted on the map, and decide on the playing season. The user can also choose a type of city (strong and fast) to use. The user can save the level, close the window, and load the level later, or they can save the level and continue to change it, decide they want to go back to what they saved, and load the level from the same window. 
  As of Beta, levels built in Level Builder are automatically sent to the main game files, where they can be launched in the levels menu via the "Play Built Game" button.
* Auxilary Screens:
  The program begins with the main menu screen. Clicking the high scores button displays the list of stored player names and high scores, sorted from highest to lowest. The basic main menu setup allows the user to click to select a level which takes them to the levels window with options to play a campaign level, load a saved game, or pick a season level. When the game ends, a dialog box pops up for the user to enter their name. It is then written to the high score file.


### [Recording](https://youtu.be/7MihSWZzuec)

### Expenses
| Name | Username | Ownership Area | Hours Invested | Hours Remaining | Link to Journal | 
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- |
| Isabelle Overton | iover106 | High Scores Design | 28 hours 50 minutes | 21 hours 10 minutes | [Isabelle's Journal](https://github.com/bjucps209/spring22-team1/wiki/OvertonJournal) |
| Rhys Fuller | RFuller25 | Serialization | 27 hours 44 minutes | 22 hours 16 minutes | [Rhys's Journal](https://github.com/bjucps209/spring22-team1/wiki/Fuller-Journal) |
| Emily Bronkema | embronk | Level Builder | 24 hours 52 minutes | 25 hours 8 minutes | [Emily's Journal](https://github.com/bjucps209/spring22-team1/wiki/Bronkema-Journal) | 
| Ryan Moffitt | Sintfoap | Basic Game Play | 43 hours | 7 hours | [Ryan's Journal](https://github.com/bjucps209/spring22-team1/wiki/MoffittJournal) | 


## Assets used:

### Castle icons
  * Standard: https://commons.wikimedia.org/wiki/File:BSicon_Castle.svg
  * Fast: https://www.pinclipart.com/downpngs/iomhRJ_silhouette-clipart-castle-black-and-white-castle-clipart/ 
  * Strong: https://www.pngfind.com/download/xJJxwo_castle-vector-castle-vector-png-transparent-png/ 

### Seasonal backgrounds
 * Summer background: https://unsplash.com/photos/loAfOVk1eNc
 * Autumn background: https://www.needpix.com/photo/download/1495531/abstract-autumn-backdrop-background-blur-blurred-bokeh-bright-color
 * Winter background: https://besthqwallpapers.com/textures/snow-background-snow-texture-background-with-snow-winter-background-snowflakes-118035
 * Spring background: https://wallpaperaccess.com/pastel-spring-computer#google_vignette

### Background Music - Vivaldi's Four Seasons
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/1.+Spring+1(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/2.+Summer+1+(presto).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/3.+Autumn+1+(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/4.+Winter+1+(allegro+non+molto).mp3

### Extra images
 * Blizzard icon: https://upload.wikimedia.org/wikipedia/commons/d/db/Weather-Snow.png
 * Flood icon: https://upload.wikimedia.org/wikipedia/commons/8/82/Light_Rain_Cloud_by_Sara.png
 * Lightning icon: https://upload.wikimedia.org/wikipedia/commons/f/f1/Bitcoin_lightning_logo.png
 * Brick background for menu screen: https://www.pexels.com/photo/wall-bricks-220182/
