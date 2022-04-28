![Main Screen](rhysfuller.com/MainScreen.PNG)
# spring22-team1
CPS 209 Spring Team 1 Project

## Beta

### Instructions 
  When the game begins, you will see three types of cities. The enemy cities (red) and will try to attack you but the neutral cities (grey) will not attack you. Using your cities (blue), you will send troops to attack and subsequently capture the other cities. The population of all of the cities will rise over time until they hit the max population limit of 30.
  
 To play, click on one of your own cities, drag the slider at the bottom of the screen to choose how many troops you want to send from that city's population, and then click on the city you want to send your troops to. The troops continue to be produced over time at a one-per-second rate until the city hits the maximum population limit. Your troops will then march out of your city and to the other city. If the destination city is an enemy city or a neutral city, the troops will decrease some of the population of the city, but if the destination city is one of your own cities, your troops will add more population to that city. When you defeat a neutral or enemy city by reducing their population to -1, that city will become one of your own.

You win when you control all of the cities on the map. You lose if all of your cities are taken over by the enemy, or if your score reaches zero.  

### Work Completed 
* Game Functionality:
  The game initializes the list of entities supplied by the load function and starts the game loop. The game handles clicks by the user on cities, for selecting troops and moving troops. The slider allows the user to specify the percent of population to move out of a city at a given time. The game handles a drag box which allows the user to select many troops and group them together for group movement or attacks. Cities send troops in a staggered timeline, and when sent to coordinates on the screen, troops spread out in a circle formation so they don't all clump together. When troop are sent to enemy cities, they decrement population on arrival. When enemy cities hit -1 troops the city changes to a player city. There are three different types of cities corresponding to three different types of troops: standard, strong, and fast. 
  - The standard troops have standard health and speed, the strong troops have standard speed with two health, and the fast troops have double speed and standard health. When units collide with one another, they both die, unless they are strong troops, which take two hits to kill. 
  - Every 50 ticks a random type of weather will spawn on the screen, traveling in a set path across the screen. The rainstorm will kill any troops in its path, the blizzard will slow troops it comes across, and the lightning has a chance of killing random troops it floats over. All will despawn upon reaching the other side of the screen. 
  - The enemy cities fight back with easy, medium, and hard mode difficulties, which are dynamic and can be switched mid-game. Cities will shoot projectiles at oncoming enemies, destroying them. The special Easter Egg level is activated upon clicking the game's title. The cheat mode allows the user to instantly win, lose, increment population, and spawn weather.
* Serialization: 
  The save function creates a save file at the given filename. It saves the current score, state of the game, and all of the cities and troops on the map, along with their destinations, speeds, headings, population counts, and type, as relevant. When the load function is called, it loads all of the game in the exact state that it was saved in. Cities are at their current populations, and troops are not only where they were when the game was saved, but heading in the same direction at the same speed, and are actively moving when the game is loaded. The code passes all unit tests.
  Save Function now saves Weather Entities and Projectile Entities.
* Level Builder: 
  The program allows the user to place the enemy, neutral, and player cities wherever wanted on the map, and decide on the playing season. The user can also choose a type of city (strong and fast) to use. The user can save the level, close the window, and load the level later, or they can save the level and continue to change it, decide they want to go back to what they saved, and load the level from the same window. 
  As of Beta, levels built in Level Builder are automatically sent to the main game files, where they can be launched in the levels menu via the "Play Built Game" button.
* Auxilary Screens:
  The program begins with the main menu screen. Clicking the high scores button displays the list of stored player names and high scores, sorted from highest to lowest. The basic main menu setup has a levels button which takes them to the levels window with options to play a campaign level, load a saved game, or pick a season level. There is also an option for determining the game difficulty. When the game ends, a dialog box pops up for the user to enter their name. It is then written to the high score file along with the score.


### [Recording](https://www.youtube.com/watch?v=qFtaXuC6fbY)

### Expenses
| Name | Username | Ownership Area | Hours Invested | Hours Remaining | Link to Journal | 
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- |
| Isabelle Overton | iover106 | Auxiliary Screens | 38 hours 55 minutes | 11 hours 5 minutes | [Isabelle's Journal](https://github.com/bjucps209/spring22-team1/wiki/OvertonJournal) |
| Rhys Fuller | RFuller25 | Serialization | 38 hours 35 minutes | 11 hours 25 minutes | [Rhys's Journal](https://github.com/bjucps209/spring22-team1/wiki/Fuller-Journal) |
| Emily Bronkema | embronk | Level Builder | 34 hours 40 minutes | 15 hours 20 minutes | [Emily's Journal](https://github.com/bjucps209/spring22-team1/wiki/Bronkema-Journal) | 
| Ryan Moffitt | Sintfoap | Basic Game Play | 50 hours | 0 hours | [Ryan's Journal](https://github.com/bjucps209/spring22-team1/wiki/MoffittJournal) | 


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
 * Spring: https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/1.+Spring+1(allegro).mp3
 * Summer: https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/2.+Summer+1+(presto).mp3
 * Autumn: https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/3.+Autumn+1+(allegro).mp3
 * Winter: https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/4.+Winter+1+(allegro+non+molto).mp3

### Extra images
 * Blizzard icon: https://upload.wikimedia.org/wikipedia/commons/d/db/Weather-Snow.png
 * Flood icon: https://upload.wikimedia.org/wikipedia/commons/8/82/Light_Rain_Cloud_by_Sara.png
 * Lightning icon: https://upload.wikimedia.org/wikipedia/commons/f/f1/Bitcoin_lightning_logo.png
 * Brick background (menu screen): https://www.pexels.com/photo/wall-bricks-220182/
