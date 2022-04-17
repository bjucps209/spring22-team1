# spring22-team1
CPS 209 Spring Team 1 Project

## Alpha

### Instructions 
  When the game begins, you will see three types of cities. The enemy cities (red) and will try to attack you, the neutral cities (grey) will not attack you, and using your cities (blue), you will be able to send troops to attack the other cities. The population of all of the cities will rise over time until they hit the max population limit. 
  
 To play, you click on one of your cities, drag the slider to choose how many troops you want to send from that city's population, and click on the city you want to sent your troops to. The troops continue to be produced over time at a one per second rate until the city hits the maximum population limit. Your troops will then march out of your city and to the other city. If the destination city is an enemy city or a neutral city, the troops will kill some of the population of the city, but if the destination city is one of your own cities, your troops will add more population to your cities. When you defeat a neutral or enemy city, the city will become one of your own.

You win when you control all of the cities on the map. You lose if all of your cities are taken over, or if your score reaches zero.  

### Work Completed 
* Game Functionality
* Serialization: 
  The save function creates a save file at the given filename. It saves the current score, state of the game, and all of the cities and troops on the map, along with their destinations, speeds, headings, population counts, and type, as relevant. When the load function is called, it loads all of the game in the exact state that it was saved in. Cities are at their populations, and troops are not only where they were when the game was saved, but heading in the same direction at the same speed, and are actively moving when the game is loaded. Code passes all unit tests.
* Level Builder: 
  The program allows the user to place the enemy, neutral, and player cities wherever wanted on the map, and decide on the playing season. The user can save the level, close the window, and load the level later, or they can save the level and continue to change it, decide they want to go back to what they saved, and load the level from the same window. 
* Auxilary Screens

### Known Issues

* Model-view violations

### [Recording](https://youtu.be/fqHC75AZ9QE)

### Expenses
| Name | Username | Ownership Area | Hours Invested | Hours Remaining | Link to Journal | 
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- |
| Isabelle Overton | iover106 | High Scores Design | 11 hours 50 minutes | 37 hours 10 minutes | [Isabelle's Journal](https://github.com/bjucps209/spring22-team1/wiki/OvertonJournal) |
| Rhys Fuller | RFuller25 | Serialization | 17 hours 33 minutes | 32 hours 27 minutes | [Rhys's Journal](https://github.com/bjucps209/spring22-team1/wiki/Fuller-Journal) |
| Emily Bronkema | embronk | Level Builder | 15 hours 52 minutes | 34 hours 08 minutes | [Emily's Journal](https://github.com/bjucps209/spring22-team1/wiki/Bronkema-Journal) | 


## Assets used:

### Castle icon
https://commons.wikimedia.org/wiki/File:BSicon_Castle.svg

### Seasonal backgrounds
 * Summer background: https://www.goodfon.com/wallpaper/green-grass-summer-gazon-fon-trava-zelenaia.html
 * Autumn background: https://phandroid.com/wp-content/uploads/2014/10/Leaves.jpg
 * Winter background: https://besthqwallpapers.com/textures/snow-background-snow-texture-background-with-snow-winter-background-snowflakes-118035
 * Spring background: https://airwallpaper.com/wp-content/uploads/2016/08/Spring-Flowers-Backgrounds.jpg


### Background Music - Vivaldi's Four Seasons
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/1.+Spring+1(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/1.+Spring+3+(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/2.+Summer+1+(presto).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/2.+Summer+2+(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/3.+Autumn+1+(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/3.+Autumn+3+(allegro).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/4.+Winter+1+(allegro+non+molto).mp3
 * https://archive.org/details/ETitleA.Vivaldi-TheFourSeasons/4.+Winter+3+(allegro).mp3

### Sound Effects
 * Snow Storm: https://assets.mixkit.co/sfx/download/mixkit-winter-wind-loop-1175.wav
 * Flood: https://assets.mixkit.co/sfx/download/mixkit-forest-and-waterfall-1229.wav
 * Storm: https://assets.mixkit.co/sfx/download/mixkit-cinematic-thunder-hit-1280.wav
 * Castle Taken: https://www.fesliyanstudios.com/play-mp3/6202

### Extra images
 * Snow storm icon: https://commons.wikimedia.org/wiki/File:Snowstorm.svg
 * Flood icon: https://commons.wikimedia.org/wiki/File:Light_Rain_Cloud_by_Sara.png
 * Lightning icon: https://commons.wikimedia.org/wiki/File:Icons8_flat_flash_on.svg
