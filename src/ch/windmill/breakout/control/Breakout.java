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
import ch.windmill.breakout.model.Paddle;
import ch.windmill.engine.Game;
import ch.windmill.engine.GameState;
import ch.windmill.engine.input.GameInput;
import java.awt.Graphics2D;

/**
 *
 * @author Cyrill Jauner
 */
public class Breakout implements Game {
    
    private boolean running;
    private float accu;
    private BreakoutScene scene;
    
    public Breakout(int panelWidth, int panelHeight) {
        running = false;
        accu = 0;
        scene = new BreakoutScene(1.0f / 100, panelWidth, panelHeight);
    }
    
    public void setLevel(Level level) {
        scene.setLevel(level);
    }
    
    @Override
    public void start() {
        running = true;
    }

    @Override
    public void update(GameState state) {
        scene.step(state);
        
        Level level = scene.getLevel();
        boolean allBricksDestroyed = true;
        for(Brick b : level.getBricks()) {
            if(!b.isDestroyed()) {
                allBricksDestroyed = false;
                break;
            }
        }
        if(allBricksDestroyed || scene.isLastBallOut()) {
            terminate();
            scene.reset();
        }
    }
    
    @Override
    public void input(GameInput input) {
        if(input.mouseMoving){
            Level level = scene.getLevel();
            Paddle paddle = level.getPaddle();
            
            if((input.mouseX + paddle.width) <= scene.getBoundary().width) {
                paddle.lastPosition.x = input.mouseX;
                paddle.position.x = input.mouseX;
            }
        }
    }

    @Override
    public void draw(GameState state, Graphics2D g2) {
        Level level = scene.getLevel();
        for(Brick brick : level.getBricks()) {
            if(!brick.isDestroyed()) {
                brick.draw(state, g2);
            }
        }
        for(Ball ball : level.getBalls()) {
            ball.draw(state, g2);
        }
        level.getPaddle().draw(state, g2);
    }

    @Override
    public void terminate() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void resetBreakout() {
        if(running) {
            running = false;
        }
        scene.reset();
    }
}
