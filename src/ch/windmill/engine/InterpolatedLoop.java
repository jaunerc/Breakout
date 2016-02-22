/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine;

import ch.windmill.engine.input.GameInput;
import java.awt.Graphics2D;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a gameloop that implements motion interpolation.
 */
public class InterpolatedLoop implements Loopable {
    
    public long frameRate;
    public int maxUpdates;
    private long time;

    public InterpolatedLoop(double frameRate, int maxUpdates) {
        this.frameRate = (long)(frameRate * 1000000000l);
        this.maxUpdates = maxUpdates;
    }
    
    public InterpolatedLoop(int maxUpdates, long frameRate, TimeUnit timeUnit) {
        this.maxUpdates = maxUpdates;
        this.frameRate = timeUnit.toNanos( frameRate );
    }
    
    @Override
    public void onStart(GameState state) {
        state.reset();
        state.setElapsed(frameRate);
    }

    @Override
    public boolean iterate(Game game, GameState state, GameInput input, Graphics2D g2) {
        long nanosElapsed = state.tick();
        time += nanosElapsed;
        int updateCount = 0;
        
        while (time >= frameRate && updateCount < maxUpdates) {
            game.input(input);
            input.clear();
            
            if(!game.isRunning()) {
                return false;
            }
            
            state.updateChronoCycle();
            game.update(state);
            
            if(!game.isRunning()) {
                return false;
            }
            
            updateCount++;
            time -= frameRate;
        }
        
        state.interpolate = getStateInterpolation();
        state.forward = state.interpolate * state.seconds;
	state.backward = state.forward - state.seconds;
        
        state.drawChronoCycle();
        game.draw(state, g2);
        
        return true;
    }
    
    public float getStateInterpolation() {
        return (float) ((double) time / (double) frameRate);
    }
}
