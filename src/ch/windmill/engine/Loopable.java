/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 */
package ch.windmill.engine;

import ch.windmill.engine.input.GameInput;
import java.awt.Graphics2D;

/**
 * A loopable is a general abstraction for a game loop.
 */
public interface Loopable {
    
    /**
     * Initializes this loop. This method should be invoked first.
     * @param state The current application state.
     */
    void onStart(GameState state);
    
    /**
     * Invokes one iteration step. This method should contain routines of the game loop.
     * @param game The running application.
     * @param state The current application state.
     * @param input The game input object.
     * @param g2 Graphics context.
     * @return 
     */
    boolean iterate(Game game, GameState state, GameInput input, Graphics2D g2);
}
