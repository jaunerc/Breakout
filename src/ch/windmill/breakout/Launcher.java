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
package ch.windmill.breakout;

import ch.windmill.breakout.control.Breakout;
import ch.windmill.breakout.view.BreakoutFrame;
import ch.windmill.breakout.view.BreakoutScreen;
import ch.windmill.engine.GameState;
import ch.windmill.engine.InterpolatedLoop;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jaunerc
 */
public class Launcher {
    
    private static int WIDTH = 600;
    private static int HEIGHT = 600;
    
    public static void main(String[] args) {
        /**Breakout breakout = new Breakout(600, 500);
        GameState state = new GameState();
        InterpolatedLoop loop = new InterpolatedLoop(3, 20, TimeUnit.MILLISECONDS);
        BreakoutScreen screen = new BreakoutScreen(breakout, state, loop, 600, 500);
        screen.setBackground(Color.white);*/
        BreakoutFrame frame = new BreakoutFrame(WIDTH, HEIGHT);
        
        //frame.add(screen);
        //frame.pack();
        frame.setVisible(true);
        //screen.start();
        //frame.startBreakout();
    }
}
