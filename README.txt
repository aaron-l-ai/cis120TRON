CIS 120 Final Project: TRON

Two players duke it out, with one player controlling the blue light cycle and the other 
the red. Players can collect golden "coins" that spawn and re-spawn every so often in order to 
increase the amount of points they earn. Players are also rewarded for their longevity, as 
the longer the trails their lightcycle makes, the more points are added to their total. Beware, 
however, for crashing into your own light trail, or into the light trail of another player will 
spell doom! Once both players are out of commission, the player with the highest amount of points 
wins the round! 


IMPLEMENTATION:

Classes:

GameObj - Core of all in game objects. One other game object classes is built off of it. Includes
location, height width, and max x and y parameters (stripped down version of that in Mushroom).

Bonus - Built off of GameObj, stationary yellow object

Segment - Built off of GameObj, adds a color variable to it.  

Cycle - Main "player" class. Includes similar parameters to those found in GameObj, but includes
movement, collisions, and clipping methods. Makes use of Segment in an ArrayList to represent
the body. ArrayList used as it is faster at adding new elements (although it takes up more mem).

Red/Blue - Extend Cycle, one for each player as starting positions are different for both. 

Game - Effectively same as that found in Mushroom with additional JLabels and JButtons

GameCourt - Main game logic found here. Updates input JLabels from Game to show status (e.g. 
"Player 2 Won!", etc) and also implements IO for reading in/writing out high scores. 

Direction - Retained from Mushroom. Useful as the cycles only need to go in one direction at 
a time.

snakecycleTest - JUnit tests that use Robot().

Hopefully this all executes correctly. 





