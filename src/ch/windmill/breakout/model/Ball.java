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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * This class represents a ball in the game.
 * @author Cyrill Jauner
 */
public class Ball extends Sprite {
    
    private Ellipse2D.Float ellipse;
    private Color color;
    private int damage;
    public float radius;
    
    /**
     * Creates a new Ball object. The damage is set to 0.
     * @param radius The radius of the ball.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Ball(float radius, float x, float y) {
        this(radius, 0, x, y);
    }
    
    /**
     * Creates a new Ball object.
     * @param radius The radius of the ball.
     * @param damage The damage of the ball.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Ball(float radius, int damage, float x, float y) {
        super(x, y);
        this.radius = radius;
        this.damage = damage;
        ellipse = new Ellipse2D.Float(x, y, 2*radius, 2*radius);
        color = Color.blue;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public Vector2F center() {
        return Vector2F.add(position, radius);
    }

    @Override
    public void update(GameState state) {
        lastPosition = Vector2F.copyOf(position);
        position.add(Vector2F.multiply(velocity, state.seconds));
    }

    @Override
    public void draw(GameState state, Graphics2D g2) {
        ellipse.x = (position.x - lastPosition.x) * state.interpolate + lastPosition.x;
        ellipse.y = (position.y - lastPosition.y) * state.interpolate + lastPosition.y;
        //ellipse.x = position.x + state.backward * velocity.x -radius;
        //ellipse.y = position.y + state.backward * velocity.y -radius;
        //ellipse.x = position.x + state.forward * velocity.x -radius;
        //ellipse.y = position.y + state.forward * velocity.y -radius;
        g2.setColor(color);
        g2.fill(ellipse);
    }
}
