/*
 * The MIT License
 *
 * Copyright 2016 Cyrill Jauner.
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

import ch.windmill.engine.GameState;
import ch.windmill.engine.core.Vector2F;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * A Rect is a general abstraction for a rectangle sprite.
 * @author Cyrill Jauner
 */
public abstract class Rect extends Sprite {
    
    public Rectangle2D.Float rectangle;
    public float width, height;
    
    /**
     * Creates a new Rect object.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle. 
     */
    public Rect(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
        rectangle = new Rectangle2D.Float(x, y, width, height);
    }
    
    /**
     * Gets a position vector to the lowest corner of the rectangle.
     * @return position vector.
     */
    public Vector2F lowCorner() {
        return Vector2F.copyOf(position);
    }
    
    /**
     * Gets a position vector to the highest corner of the rectangle.
     * @return position vector.
     */
    public Vector2F highCorner() {
        return new Vector2F(position.x + width, position.y + height);
    }

    @Override
    public Vector2F center() {
        return new Vector2F(position.x + width/2, position.y + height/2);
    }

    @Override
    public void update(GameState state) {
        lastPosition = Vector2F.copyOf(position);
        position.add(Vector2F.multiply(velocity, state.seconds));
    }

    @Override
    public void draw(GameState state, Graphics2D g2) {
        rectangle.x = (position.x - lastPosition.x) * state.interpolate + lastPosition.x;
        rectangle.y = (position.y - lastPosition.y) * state.interpolate + lastPosition.y;
        drawRectangle(g2);
    }
    
    /**
     * Draws a specific rectangle. This method is invoked by the rect.draw method.
     * @param g2 The graphics context.
     */
    public abstract void drawRectangle(Graphics2D g2);
}
