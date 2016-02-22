/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine;

import java.util.concurrent.TimeUnit;

/**
 * This class represents the current state of the game.
 */
public class GameState {
    
    public float seconds;
    public long millis;
    public long nanos;
    public long micros;
    public long startTime;
    public long currentTime;
    public float forward = 0;
    public float backward = 0;
    public float interpolate = 0;
    public long lastTime;
    public Chronograph updateChrono;
    public Chronograph drawChrono;
    
    /**
     * Creates a new GameState object.
     */
    public GameState() {
        updateChrono = new Chronograph(500, "Updates per second (Tick rate)", TimeUnit.MILLISECONDS);
        drawChrono = new Chronograph(500, "Draws per second (Frame rate)", TimeUnit.MILLISECONDS);
    }
    
    /**
     * Calculates the delta time of now and the last time tick. This method saves
     * the last time tick as last time.
     * @return The delta time.
     */
    public long tick() {
        lastTime = currentTime;
        currentTime = System.nanoTime();
        return (currentTime - lastTime);
    }
    
    /**
     * Gets the delta time of now and the last time tick.
     * @return The delta time.
     */
    public long getElapsedSinceTick() {
	return (System.nanoTime() - currentTime);
    }
    
    /**
     * Sets the elapsed time of the current cycle.
     * @param nanosElapsed Elapsed time in nanoseconds.
     */
    public void setElapsed(long nanosElapsed) {
        nanos = nanosElapsed;
        micros = nanosElapsed / 1000L;
        millis = nanosElapsed / 1000000L;
        seconds = (float) (nanosElapsed * 0.000000001);
    }

    /**
     * Resets this GameState.
     * @return The current time in nanos.
     */
    public long reset() {
        long resetT = System.nanoTime();
        
        startTime = resetT;
        currentTime = resetT;
        lastTime = resetT;
        
        updateChrono.reset();
        drawChrono.reset();
        
        return resetT;
    }
    
    /**
     * Calculates the delta time of the update chronograph.
     */
    public void updateChronoCycle() {
        updateChrono.cycle();
    }
    
    /**
     * Calculates the delta time of the draw chronograph.
     */
    public void drawChronoCycle() {
        drawChrono.cycle();
    }
}
