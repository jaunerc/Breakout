/*
 * The MIT License
 *
 * Copyright 2016 jaunerc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ch.windmill.breakout.control;

import ch.windmill.breakout.model.Ball;
import ch.windmill.breakout.model.Level;
import ch.windmill.breakout.model.Paddle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 * This class represents a manager for all breakout levels. The LevelManager has methods to load
 * levels from resource files.
 * @author Cyrill Jauner
 */
public class LevelManager {
    
    /**
     * The number of bricks in a row.
     */
    private static int NUM_BRICKS_X_AXIS = 10;
    
    /**
     * The initial paddle x-position. This is a percentage value of the 
     * level width.
     */
    private static float INITIAL_PADDLE_POS_X = 0.5f;
    
    /**
     * The initial paddle width.
     */
    private static int INITIAL_PADDLE_WIDTH = 60;
    
    /**
     * The initial paddle height.
     */
    private static int INITIAL_PADDLE_HEIGHT = 10;
    
    /**
     * The initial paddle x-position. This is a percentage value of the 
     * level height.
     */
    private static float INITIAL_PADDLE_POS_Y = 0.9f;
    
    /**
     * The initial radius for a ball.
     */
    private static float INITIAL_BALL_RADIUS = 12f;
    
    /**
     * The initial damage value for a ball.
     */
    private static int INITIAL_BALL_DAMAGE = 100;
    
    /**
     * The initial magnitude of the velocity vector.
     */
    private static int INITIAL_BALL_SPEED = 250;
    
    /**
     * The initial angle of the velocity vector.
     */
    private static int INITIAL_BALL_ANGLE = 1;
    
    private String levelPath;
    private HashMap<String, Level> levels;
    
    /**
     * Creates a new LevelManager object.
     * @param levelPath The path to the level directory.
     */
    public LevelManager(String levelPath) {
        this.levelPath = levelPath;
        levels = new HashMap<>();
    }
    
    /**
     * Creates a new ball object with initial values.
     * @return The reference to a ball with initial values.
     */
    public static Ball createInitBall() {
        Ball ball = new Ball(INITIAL_BALL_RADIUS, INITIAL_BALL_DAMAGE, 150, 150);
        ball.velocity.set((float)Math.cos(INITIAL_BALL_ANGLE) * INITIAL_BALL_SPEED, (float)Math.sin(INITIAL_BALL_ANGLE) * INITIAL_BALL_SPEED);
        return ball;
    }
    
    /**
     * Creates a new paddle object with initial values.
     * @param levelWidth The width of the paddle.
     * @param levelHeight The height of the paddle.
     * @return The reference to a paddle with initial values.
     */
    public static Paddle createInitPaddle(int levelWidth, int levelHeight) {
        float x = (levelWidth * INITIAL_PADDLE_POS_X) - (INITIAL_PADDLE_WIDTH/2);
        float y = (levelHeight * INITIAL_PADDLE_POS_Y);
        Paddle paddle = new Paddle(x, y, INITIAL_PADDLE_WIDTH, INITIAL_PADDLE_HEIGHT);
        return paddle;
    }
    
    public HashMap<String, Level> getLevels() {
        return levels;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }
    
    /**
     * Returns an array that contains all keys of the level map. The returned
     * array sorted.
     * @return Array with key values.
     */
    public String[] getLevelKeys() {
        Set<String> keySet = levels.keySet();
        String[] lvlKeys = new String[keySet.size()];
        Iterator<String> it = keySet.iterator();
        
        for(int i = 0; i < lvlKeys.length; i++) {
            lvlKeys[i] = it.next();
        }
        Arrays.sort(lvlKeys);
        
        return lvlKeys;
    }
    
    /**
     * Loads all levels from the levelPath directory. This method creates a new level object
     * for each level file in the levelPath directory and adds it to the level List.
     * @param levelWidth The width of the level in pixels.
     * @param levelHeight The height of the level in pixels.
     */
    public void loadLevels(int levelWidth, int levelHeight) {
        try {
            List<BufferedImage> lvlList = getLevelImgs();
            for(int i = 0; i < lvlList.size(); i++) {
                Level level = Level.loadFromImage(lvlList.get(i), levelWidth, levelHeight, NUM_BRICKS_X_AXIS);
                level.setPaddle(createInitPaddle(levelWidth, levelHeight));
                level.addBall(createInitBall());
                levels.put("level "+(i+1), level);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Gets a list with images of all level files in the levelPath directory. This method
     * creates a BufferedImage of every file in the levelPath directory.
     * @return A list with level images.
     * @throws If a image could not be loaded.
     */
    private List<BufferedImage> getLevelImgs() throws IOException {
        List<BufferedImage> imgStack = new ArrayList<>();
        
        Files.walk(Paths.get(levelPath)).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    BufferedImage img = ImageIO.read(filePath.toFile());
                    imgStack.add(img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        return imgStack;
    }
}
