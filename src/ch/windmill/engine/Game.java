/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine;

import ch.windmill.engine.input.GameInput;
import java.awt.Graphics2D;

/**
 * A app is a general abstraction for an application.
 */
public interface Game {
    
    /**
     * Starts the application.
     */
    void start();
    
    /**
     * Handles game input.
     * @param input 
     */
    void input(GameInput input);
    
    /**
     * Updates the application. A typical routine in this method is position updates of
     * drawable objects.
     * @param state The current application state.
     */
    void update(GameState state);
    
    /**
     * Draw drawable objects with the given graphics context.
     * @param state The current application state.
     * @param g2 Graphics context to draw with.
     */
    void draw(GameState state, Graphics2D g2);
    
    /**
     * Terminates the application.
     */
    void terminate();
    
    /**
     * Wheter the application is running or not.
     * @return Wheter the application is running or not
     */
    boolean isRunning();
}
