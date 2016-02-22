/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine;

import java.util.concurrent.TimeUnit;

/**
 * This class represents a chronograph to track time cylces.
 */
public class Chronograph {
    
    private long lastTime;
    private long time;
    private long cycles;
    private long refreshInterval;
    private double rate;
    private String name;
    private String rateString;

    /**
     * Creates a new Chronograph object.
     * @param interval The refresh interval of the rate String.
     * @param name The name of this.
     * @param unit The time unit.
     */
    public Chronograph(long interval, String name, TimeUnit unit) {
        this.name = name;
        setRefreshInterval(interval, unit);
    }

    public String getRateString() {
        return rateString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the refresh interval.
     * @param interval The refresh interval of the rate String.
     * @param unit The time unit of the interval parameter.
     */
    public void setRefreshInterval(long interval, TimeUnit unit) {
        refreshInterval = unit.toNanos(interval);
    }
    
    /**
     * Calculates the delta time between now and the last cycle.
     */
    public void cycle() {
        long currTime = System.nanoTime();
        long currDelta = currTime - lastTime;
        
        time += currDelta;
        cycles++;
        if(time >= refreshInterval) {
            rate = cycles / (time * 0.000000001);
            updateRateString();
            time -= refreshInterval;
            cycles = 0;
        }
        
        lastTime = currTime;
    }
    
    /**
     * Updates the rate string.
     */
    private void updateRateString() {
        rateString = name+": "+rate;
        System.out.println(rateString);
    }
    
    /**
     * Resets this chronograph. This method resets all time fields and updates
     * the rate string.
     */
    public void reset() {
        lastTime = System.nanoTime();
        time = 0;
        cycles = 0;
        rate = 0;
        updateRateString();
    }

    
}
