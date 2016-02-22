/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.event.MouseInputListener;

/**
 * A GameInput object listens for any game input and saves it.
 */
public class GameInput implements MouseInputListener{
    
    public int mouseX, mouseY, mouseDownCount, mouseUpCount;
    public boolean[] mouseDown = new boolean[ MouseInfo.getNumberOfButtons() ];
    public boolean[] mouseUp = new boolean[ MouseInfo.getNumberOfButtons() ];
    public boolean mouseInside = true;
    public boolean mouseDragging = false;
    public boolean mouseMoving = false;
    public Queue<GameMouseEvent> mouseEvents = new ConcurrentLinkedQueue<>();
    
    /**
     * Removes any input information of this object.
     */
    public void clear() {
        mouseDownCount = 0;
        mouseUpCount = 0;
        mouseInside = false;
        mouseDragging = false;
        mouseMoving = false;
        mouseEvents.clear();
        
        for(int i = 0; i < mouseUp.length; i++) {
            mouseUp[i] = false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseEvents.offer(new GameMouseEvent(GameMouseEvent.EventType.Click, e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown[ e.getButton() ] = true;
	mouseDownCount++;
        mouseEvents.offer(new GameMouseEvent(GameMouseEvent.EventType.Press, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown[ e.getButton() ] = false;
	mouseUp[ e.getButton() ] = true;
	mouseUpCount++;
        mouseEvents.offer(new GameMouseEvent(GameMouseEvent.EventType.Release, e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInside = true;
        mouseEvents.offer(new GameMouseEvent(GameMouseEvent.EventType.Enter, e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseInside = false;
        mouseEvents.offer(new GameMouseEvent(GameMouseEvent.EventType.Exit, e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDragging = true;
        mouseMoving = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseDragging = false;
	mouseMoving = true;
	mouseX = e.getX();
	mouseY = e.getY();
    }
    
}
