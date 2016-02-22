/*
 * The MIT License
 *
 * Copyright 2016 Cyrill Jauner.
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
package ch.windmill.breakout.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

/**
 * This class provides a control panel for breakout.
 * @author Cyrill Jauner
 */
public class BreakoutControlPanel extends JPanel implements ActionListener {
    
    private JList<String> listLevels;
    private JLabel labelLvl, labelState;
    private JButton btnStart, btnStop, btnReset;
    
    private final BreakoutFrame frame;
    
    /**
     * Creates a new BreakoutControlPanel object.
     * @param w The width of the game screen.
     * @param h The height of the game screen.
     * @param levels A list with level keys.
     * @param frame The frame that invokes breakout.
     */
    public BreakoutControlPanel(int w, int h, String[] levels, BreakoutFrame frame) {
        Dimension d = new Dimension(w, h);
        setPreferredSize(d);
        setSize(d);
        setLayout(new FlowLayout());
        initComponents(levels);
        this.frame = frame;
    }
    
    /**
     * Sets the current game state.
     * @param state The current state of the game.
     */
    public void setState(String state) {
        labelState.setText(state);
    }
    
    /**
     * Initializes all ui components.
     * @param levels A list with level keys.
     */
    private void initComponents(String[] levels) {
        listLevels = new JList<>();
        labelLvl = new JLabel("Choose Level:");
        labelState = new JLabel(BreakoutFrame.STATE_GAMEOVER);
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        btnStop.setEnabled(false);
        btnReset = new JButton("Reset");
        
        listLevels.setListData(levels);
        listLevels.setPreferredSize(new Dimension(100, 200));
        listLevels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        btnReset.addActionListener(this);
        
        add(labelLvl);
        add(listLevels);
        add(btnStart);
        add(btnStop);
        add(btnReset);
        add(labelState);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(frame != null) {
            if(e.getSource() == btnStart) {
                String lvlKey = listLevels.getSelectedValue();
                if(lvlKey != null && !frame.isBreakoutRunning()) {
                    frame.setCurrentLevel(lvlKey);
                    frame.startBreakout();
                    btnStop.setEnabled(true);
                }
            }
            if(e.getSource() == btnStop) {
                frame.stopBreakout();
                btnStop.setEnabled(false);
            }
            if(e.getSource() == btnReset) {
                frame.resetBreakout();
                btnStop.setEnabled(false);
            }
        }
    }
}
