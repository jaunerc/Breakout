/*
 * Copyright (c) 2013 Philip Diffenderfer http://magnos.org
 *
 * Modified by Cyrill Jauner
 */
package ch.windmill.engine;

import ch.windmill.engine.input.GameInput;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class represents a Screen to display the game.
 */
public class GameScreen extends JPanel {
    
    private Game game;
    private Loopable loop;
    private GameState state;
    private GameInput input;
    private Graphics2D g2;
    private BufferedImage buffer;
    
    /**
     * Creates a new GameScreen object.
     * @param game The game to play.
     * @param state The state of the game.
     * @param loop The gameloop
     * @param width The width of this screen in pixels.
     * @param height The height of this screen in pixels.
     */
    public GameScreen(Game game, GameState state, Loopable loop, int width, int height) {
        this.game = game;
        this.loop = loop;
        this.state = state;
        input = new GameInput();
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Dimension d = new Dimension(width, height);
        setSize(d);
        setPreferredSize(d);
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void paint(Graphics g) {
        if(g != null && buffer != null) {
            renderGraphics(g);
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    /**
     * Starts the game. This method creates a loop that runs as long as the game is running.
     * The loop invokes the game.update and the loop.iterate methods in each iteration.
     */
    public void start() {
        input.mouseInside = getParent().contains( MouseInfo.getPointerInfo().getLocation() );
	addMouseListener( input );
	addMouseMotionListener( input );
        
        resetGraphics();
        
        game.start();
        
        loop.onStart(state);
        
        while(game.isRunning()) {
            if(loop.iterate(game, state, input, g2)) {
                renderGraphics(getGraphics());
                resetGraphics();
            }
        }
        
        state.reset();
        input.clear();
    }
    
    
    /**
     * Draws the buffer to the screen.
     * @param g The Graphics context to draw the buffer.
     */
    private void renderGraphics(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
        g.dispose();
    }
    
    /**
     * Resets the graphics context of the buffer.
     */
    private void resetGraphics() {
        g2 = (Graphics2D) buffer.getGraphics();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
    /**
     * Shows this screen in a JFrame.
     */
    public void showInFrame() {
        JFrame frame = new JFrame("Engine test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }
}
