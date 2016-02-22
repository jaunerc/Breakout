/*
 * The MIT License
 *
 * Copyright 2016 jaunerc.
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
package ch.windmill.breakout.model;

import ch.windmill.engine.core.Vector2F;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents a player paddle. A paddle has several sections to influence the
 * velocity direction of a ball.
 * @author Cyrill Jauner
 */
public class Paddle extends Rect {
    private Vector2F[] sections;
    private Color color;
    
    /**
     * Creates a new Paddle object.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width of the paddle.
     * @param height The height of the paddle.
     */
    public Paddle(float x, float y, float width, float height) {
        super(x, y, width, height);
        sections = new Vector2F[5];
        color = Color.BLUE;
        initSections();
    }
    
    /**
     * Initializes all section vectors.
     */
    private void initSections() {
        sections[0] = new Vector2F(-1, -1);
        sections[1] = new Vector2F(-0.5f, -1);
        sections[2] = new Vector2F(0, -1);
        sections[3] = new Vector2F(0.5f, -1);
        sections[4] = new Vector2F(1, -1);
        for(int i = 0; i < sections.length; i++) {
            sections[i].normalize();
        }
    }
    
    /**
     * Returns the vector for the affected section. This method expects a
     * x-coordinate on this paddle.
     * @param x The x-coordinate 
     * @return The vector of the affected section.
     */
    public Vector2F getSection(float x) {
        float sectionLen = width / sections.length;
        
        if(x < position.x + sectionLen) {
            //System.out.println("first");
            return sections[0];
        } else if(x < position.x +2*sectionLen) {
            //System.out.println("second");
            return sections[1];
        } else if(x < position.x+3*sectionLen) {
            //System.out.println("third");
            return sections[2];
        } else if(x < position.x+4*sectionLen) {
            //System.out.println("fourth");
            return sections[3];
        } else if(x < position.x+5*sectionLen) {
            //System.out.println("fifth");
            return sections[4];
        }
        
        return null;
    }

    @Override
    public void drawRectangle(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(rectangle);
    }
}
