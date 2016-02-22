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
package ch.windmill.breakout.control;

import ch.windmill.breakout.model.Ball;
import ch.windmill.breakout.model.Paddle;
import ch.windmill.breakout.model.Rect;
import ch.windmill.engine.core.Vector2F;
import java.awt.geom.Rectangle2D;

/**
 * This class represents a collision between a rect and a ball.
 * @author Cyrill Jauner
 */
public class Collision {
    
    public Rect r;
    public Ball b;
    public Vector2F n;
    
    /**
     * Creates a new Collision object.
     * @param b 
     */
    public Collision(Ball b) {
        this.b = b;
        r = null;
        n = null;
    }
    
    /**
     * Handles collision between a ball and a brick.
     */
    public void resolveBrickCollision() {
        if (n.x == 0.0) {
            b.velocity.mulY(-1);
        } else {
            b.velocity.mulX(-1);
        }
    }
    
    /**
     * Handles collision between a ball and a paddle.
     */
    public void resolvePaddleCollision() {
        Vector2F section = ((Paddle) r).getSection(b.position.x);
        float speed = b.velocity.length();
        b.velocity = Vector2F.multiply(section, speed);
    }
    
    /**
     * Handles a collision with the given boundary borders.
     * @param boundary A rectangle that represents the level range.
     * @return True if the ball hits the ground.
     */
    public boolean resolveBoundaryCollision(Rectangle2D.Float boundary) {
        float bl = boundary.x;
	float br = boundary.x + boundary.width - 2*b.radius;
	float bt = boundary.y;
	float bb = boundary.y + boundary.height - 2*b.radius;
        
        if(b.position.x < bl) {
            b.velocity.x *= -1;
            b.position.x = bl;
        }
        if(b.position.x > br) {
            b.velocity.x *= -1;
            b.position.x = br;
        }
        if(b.position.y < bt) {
            b.velocity.y *= -1;
            b.position.y = bt;
        }
        if(b.position.y > bb) {
            b.velocity.mul(0);
            b.position.y = bb;
            return true;
        }
        return false;
    }
}
