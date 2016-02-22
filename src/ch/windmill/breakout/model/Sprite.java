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

import ch.windmill.engine.GameState;
import ch.windmill.engine.core.Vector2F;
import java.awt.Graphics2D;


/**
 * A Sprite is a general abstraction for a drawable and moveable object.
 * @author Cyrill Jauner
 */
public abstract class Sprite {
    
    public Vector2F position, lastPosition;
    public Vector2F velocity;
    
    public Sprite(float x, float y) {
        position = new Vector2F(x, y);
        lastPosition = new Vector2F(x,y);
        velocity = new Vector2F();
    }
    
    /**
     * Updates the position of this shape based on the current game state.
     * @param state The current game state.
     */
    public abstract void update(GameState state);
    
    /**
     * 
     * @param state
     * @param g2 
     */
    public abstract void draw(GameState state, Graphics2D g2);
    
    /**
     * Gets a vector from the origin to the center of this sprite.
     * @return position vector to the center of the sprite.
     */
    public abstract Vector2F center();
}
