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
package ch.windmill.breakout.view;

import ch.windmill.breakout.control.Breakout;
import ch.windmill.breakout.control.LevelManager;
import ch.windmill.breakout.model.Level;
import ch.windmill.engine.GameState;
import ch.windmill.engine.InterpolatedLoop;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 * This class provides a window to show breakout.
 * @author Cyrill Jauner
 */
public class BreakoutFrame extends JFrame {
    
    public final static String STATE_RUNNING = "running";
    public final static String STATE_GAMEOVER = "game over";
    private final static String TITLE = "Breakout";
    
    private BreakoutControlPanel controlPanel;
    private BreakoutScreen screen;
    private Breakout breakout;
    private LevelManager levelManager;
    
    /**
     * Creates a new BreakoutFrame object.
     * @param width The width of the game screen.
     * @param height The height of the game screen.
     */
    public BreakoutFrame(int width, int height) {
        super(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        initBreakout(width, height);
        
        pack();
    }
    
    /**
     * Initializes the game.
     * @param w The width of the game screen.
     * @param h The height of the game screen.
     */
    private void initBreakout(int w, int h) {
        levelManager = new LevelManager("./res/levels");
        levelManager.loadLevels(w, h);
        
        breakout = new Breakout(w, h);
        GameState state = new GameState();
        InterpolatedLoop loop = new InterpolatedLoop(3, 16, TimeUnit.MILLISECONDS);
        
        String[] lvlNames = levelManager.getLevelKeys();
        
        controlPanel = new BreakoutControlPanel(100, h, lvlNames, this);
        
        screen = new BreakoutScreen(breakout, state, loop, w, h);
        screen.setBackground(Color.white);
        add(screen);
        add(controlPanel);
    }
    
    /**
     * Starts breakout in a new thread.
     */
    public void startBreakout() {
        new Thread(() -> {
            controlPanel.setState(STATE_RUNNING);
            screen.start();
            controlPanel.setState(STATE_GAMEOVER);
        }).start();
    }
    
    /**
     * Stops the game loop.
     */
    public void stopBreakout() {
        screen.stop();
    }
    
    /**
     * Resets the current scene.
     */
    public void resetBreakout() {
        screen.reset();
    }
    
    /**
     * Wheter a breakout game is running or not.
     * @return 
     */
    public boolean isBreakoutRunning() {
        return screen.isRunning();
    }
    
    /**
     * Sets the given level in the scene. This method proofs if a breakout
     * game is still running. Otherwise it does nothing.
     * @param lvlKey The key of a level.
     */
    public void setCurrentLevel(String lvlKey) {
        if(!screen.getGame().isRunning()) {
            HashMap<String, Level> map = levelManager.getLevels();
            Level level = map.get(lvlKey);
            breakout.setLevel(level);
        }
    }
}
