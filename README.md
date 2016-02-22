# Breakout
This breakout application is a self-study project for basic 2D game development.<br>
It implements the basic features of the original breakout game (see <a href="https://en.wikipedia.org/wiki/Breakout_(video_game)">wikipedia.org</a>)<br>
<br>
The classes of the game engine package were coded with a great tutorial from <a href="https://github.com/ClickerMonkey/gameprogblog">ClickerMonkey</a>.
Visit his account to see the full tutorial.
<br><br>
This application let you play breakout. The target of the game is to destroy all bricks on the screen.
The game is finished, if the ball hits the bottom border of the screen.
<br>
The /res directory contains level resource files. Each level loads his bricks by a image file.
The levelmanager reads the rgb-values of each pixel and creates the bricks of each level. The color
influences for the fill-color and the lifepoints of a brick.
