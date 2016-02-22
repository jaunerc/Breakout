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

import ch.windmill.breakout.exception.IllegalColorException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents a brick in the game. A brick is specialized rect with fields for
 * lifepoints and a brick-type enum. The brick-type defines the color and the start value
 * of lifepoints.
 * @author Cyrill Jauner
 */
public class Brick extends Rect {
    private int lifePoints;
    private BrickType type;
    
    /**
     * The BrickType defines the initial number of lifepoints that a brick has.
     */
    public enum BrickType {
        STANDARD(Color.red, 100),
        GREEN(Color.green, 200);
        
        private final Color color;
        private final int lifePoints;
        
        BrickType(Color color, int lifePoints) {
            this.color = color;
            this.lifePoints = lifePoints;
        }
        
        public Color getColor() { return color; }
        public int getLifePoints() { return lifePoints; }
    }
    
    /**
     * Creates a new Brick object with red BrickType.
     * @param x The x-coordinate on the panel.
     * @param y The y-coordinate on the panel.
     * @param width The width of the brick.
     * @param height The height of the brick.
     */
    public Brick(float x, float y, float width, float height) {
        this(Color.red, x, y, width, height);
    }
    
    /**
     * Creates a new Brick object. The color objects determines the BrickType of the brick.
     * A BrickType enum with the given Color must be exist, otherwise it sets the default Color red.
     * In this case, this constructor throws a RuntimeException.
     * @param c The color of the brick.
     * @param x The x-coordinate on the panel.
     * @param y The y-coordinate on the panel.
     * @param width The width of the brick.
     * @param height The height of the brick.
     * @throws IllegalColorException The given Color is not supported.
     */
    public Brick(Color c, float x, float y, float width, float height) {
        super(x, y, width, height);
        try {
            initBrick(c);
        } catch(IllegalColorException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Sets the type of this brick by the given color. The color determines the type and
     * the initial life points. If the color is not supported, this method sets the color
     * to red.
     * @param c The color of the brick.
     * @throws IllegalColorException There is no type for the given color.
     */
    private void initBrick(Color c) throws IllegalColorException {
        type = null;
        for(BrickType bType : BrickType.values()) {
            if(bType.getColor().equals(c)) {
                type = bType;
                break;
            }
        }
        if(type != null) {
            lifePoints = type.getLifePoints();
        } else {
            type = BrickType.STANDARD;
            lifePoints = type.getLifePoints();
            throw new IllegalColorException(c);
        }
    }
    
    @Override
    public void drawRectangle(Graphics2D g2) {
        g2.setColor(type.color);
        g2.fill(rectangle);
        g2.setColor(Color.black);
        g2.draw(rectangle);
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
    
    public Color getColor() {
        return type.getColor();
    }
    
    /**
     * Reduces the lifepoints of this brick by the given damage value.
     * @param damage The number of lifepoints to reduce.
     */
    public void damage(int damage) {
        lifePoints -= damage;
    }
    
    /**
     * Wheter this brick is destroyed or not.
     * @return Wheter this brick is destroyed or not.
     */
    public boolean isDestroyed() {
        return (lifePoints <= 0);
    }
    
    /**
     * Resets the lifepoints of this brick.
     */
    public void reset() {
        lifePoints = type.getLifePoints();
    }
}
