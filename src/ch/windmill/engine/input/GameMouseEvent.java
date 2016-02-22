/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine.input;

import java.awt.event.MouseEvent;

/**
 * This class represents a mouse event in the game.
 */
public class GameMouseEvent {
    
    /**
     * The enum to indicate which type of event occured.
     */
    public enum EventType {
        Click, Press, Release, Enter, Exit
    }
    
    public EventType type;
    public MouseEvent e;
    
    /**
     * Creates a new GameMouseEvent object.
     * @param type The event type.
     * @param e The awt mouse event.
     */
    public GameMouseEvent(EventType type, MouseEvent e) {
        this.type = type;
        this.e = e;
    }
}
