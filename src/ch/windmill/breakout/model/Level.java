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
package ch.windmill.breakout.model;

import ch.windmill.breakout.control.LevelManager;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a level in the breakout app. A Level object holds a list of Sprites.
 * A Level object can be loaded from a image resource file.
 * @author Cyrill Jauner
 */
public class Level {
    
    private final float brickWidth, brickHeight;
    private final int levelWidth, levelHeight;
    
    private List<Brick> bricks;
    private List<Ball> balls;
    private Paddle paddle;
    
    /**
     * Creates a new Level object.
     * @param brickWidth The width of each brick.
     * @param brickHeight The height of each brick.
     * @param levelWidth
     * @param levelHeight
     */
    public Level(float brickWidth, float brickHeight, int levelWidth, int levelHeight) {
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        paddle = null;
    }
    
    /**
     * Creates a level object with the given image file.
     * @param img The level resource file.
     * @param levelWidth The width of the level.
     * @param levelHeight The height of the level.
     * @param numBricksXAxis The number of bricks in a row.
     * @return A new level object.
     */
    public static Level loadFromImage(BufferedImage img, int levelWidth, int levelHeight, int numBricksXAxis) {
        int rgbValues[] = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        float brickWidth = (float)levelWidth / numBricksXAxis;
        float brickHeight = brickWidth * 0.5f;;
        Level level = new Level(brickWidth, brickHeight, levelWidth, levelHeight);
        level.generateBricks(rgbValues, numBricksXAxis);
        return level;
    }
    
    public List<Brick> getBricks() {
        return bricks;
    }
    
    public List<Ball> getBalls() {
        return balls;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Paddle getPaddle() {
        return paddle;
    }
    
    /**
     * Adds the given ball to the list.
     * @param b The ball reference to add to the list.
     */
    public void addBall(Ball b) {
        balls.add(b);
    }
    
    /**
     * Generate Brick objects with the given rgb array.
     * @param rgb Array with rgb values.
     * @param numBricksXAxis The max number of bricks in a row.
     */
    public void generateBricks(int[] rgb, int numBricksXAxis) {
        float x = 0;
        float y = 0;
        int j = 0;
        
        for(int i = 0; i < rgb.length; i++, j++) {
            x = j * brickWidth;
            Color c = new Color(rgb[i]);
            
            if(!c.equals(Color.white)) {
                Brick brick = new Brick(c, x, y, brickWidth, brickHeight);
                bricks.add(brick);
            }
            
            if(j == (numBricksXAxis -1)) {
                j = -1;
                y += brickHeight;
            }
        }
    }
    
    /**
     * Resets all sprites to their initial values.
     */
    public void reset() {
        for(Brick b : bricks) {
            b.reset();
        }
        balls = new ArrayList<>();
        balls.add(LevelManager.createInitBall());
        paddle = LevelManager.createInitPaddle(levelWidth, levelHeight);
    }
}
