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
import ch.windmill.breakout.model.Brick;
import ch.windmill.breakout.model.Level;
import ch.windmill.breakout.model.Rect;
import ch.windmill.engine.GameState;
import ch.windmill.engine.core.Vector2F;
import java.awt.geom.Rectangle2D;

/**
 * This class represents a scene to play breakout.
 * @author Cyrill Jauner
 */
public class BreakoutScene {
    
    private float dt;
    private boolean lastBallOut;
    private Rectangle2D.Float boundary;
    private Level level;
    
    /**
     * Creates a new BreakoutScene object.
     * @param dt The fixed delta time to calculate physics updates.
     * @param boundaryW The width of the scene.
     * @param boundaryH The height of the scene.
     */
    public BreakoutScene(float dt, int boundaryW, int boundaryH) {
        this.dt = dt;
        level = null;
        lastBallOut = false;
        boundary = new Rectangle2D.Float(0, 0, boundaryW, boundaryH);
    }
    
    /**
     * Limiting a value to an area.
     * @param d The value to proof.
     * @param min The min value of the area.
     * @param max The max value of the area.
     * @return The clamped value.
     */
    public static float clamp(float d, float min, float max) {
        return (d < min ? min : (d > max ? max : d));
    }

    public Level getLevel() {
        return level;
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }

    public Rectangle2D.Float getBoundary() {
        return boundary;
    }

    public boolean isLastBallOut() {
        return lastBallOut;
    }
    
    public void reset() {
        lastBallOut = false;
        level.reset();
    }
    
    /**
     * Invokes a physics update for the scene.
     * @param state The current game state.
     */
    public void step(GameState state) {
        for(Ball b : level.getBalls()) {
            b.update(state);
            
            Collision c = new Collision(b);
            if(!c.resolveBoundaryCollision(boundary)) {
                if (checkCollision(c)) {
                    if (c.r instanceof Brick) {
                        c.resolveBrickCollision();
                        ((Brick) c.r).damage(b.getDamage());
                    } else {
                        c.resolvePaddleCollision();
                    }
                }
            } else {
                if(level.getBalls().size() == 1) {
                    lastBallOut = true;
                }
            }
        }
    }
    
    /**
     * Checks if the given ball is collided.
     * @param b The ball to proof if it is collided.
     * @param c The collision object.
     * @return Wheter the ball is collided or not.
     */
    private boolean checkCollision(Collision c) {
        for(Brick br : level.getBricks()) {
            if(!br.isDestroyed()) {
                if(ballVsRect(br, c)) {
                    return true;
                }
            }
        }
        if(ballVsRect(level.getPaddle(), c)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if the collisions ball and the rect are collided. This method
     * prepares the given collision object to resolve the collision.
     * @param r 
     * @param c 
     * @return Wheter the sprites are collided or not.
     */
    private boolean ballVsRect(Rect r, Collision c) {
        boolean res = false;
        Vector2F difference = Vector2F.sub(r.center(), c.b.center());
        Vector2F closest = new Vector2F();
        
        float x_extent = (r.highCorner().x - r.lowCorner().x) / 2;
        float y_extent = (r.highCorner().y - r.lowCorner().y) / 2;
        
        closest.setX(clamp(difference.x, -x_extent, x_extent));
        closest.setY(clamp(difference.y, -y_extent, y_extent));
        
        if (difference.equals(closest)) {
            if (Math.abs(difference.x) > Math.abs(difference.y)) {
                if (closest.x > 0) {
                    closest.setX(x_extent);
                } else {
                    closest.setX(-x_extent);
                }
            } else {
                if (closest.y > 0) {
                    closest.setY(y_extent);
                } else {
                    closest.setY(-y_extent);
                }
            }
        }
        Vector2F n = Vector2F.sub(difference, closest);
        
        if (n.lengthSquared() <= c.b.radius * c.b.radius) {
            res = true;
            c.r = r;
            c.n = n;
        }
        return res;
    }
}
