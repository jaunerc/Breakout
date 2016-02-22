/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine;

import ch.windmill.engine.input.GameInput;
import java.awt.Graphics2D;

/**
 * This class represents a variable loop.
 */
public class VariableLoop implements Loopable {
    
    @Override
    public void onStart(GameState state) {
        state.reset();
    }

    @Override
    public boolean iterate(Game app, GameState state, GameInput input, Graphics2D g2) {
        state.tick();
        state.updateChronoCycle();
        app.update(state);
        
        if(!app.isRunning()) {
            return false;
        }
        
        state.drawChronoCycle();
        app.draw(state, g2);
        
        return true;
    }
    
}
