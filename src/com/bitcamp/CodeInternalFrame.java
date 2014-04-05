package com.bitcamp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;


public class CodeInternalFrame extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2378102885318671721L;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static String refObj = "";
    int inset = 50;
    
    int width = screenSize.width / 2 - 125;
    int height = screenSize.height - 175;
    int xOffset = (5), yOffset = 0;
    
    public CodeInternalFrame(String ref) {
        super("CodePanel " + ref, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(width,height);

        //Set the window's location.
        setLocation(xOffset, yOffset);
        p();
        setVisible(true);
    }
    
    public void p()
    {
    	CodePanel cp = new CodePanel();
    	cp.setVisible(true);
    	
    	getContentPane().setLayout(new BorderLayout());
    	getContentPane().add(cp, BorderLayout.WEST);
        pack();
        
    }

    public CodeInternalFrame() {
        super("CodePanel ", 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(width,height);

        //Set the window's location.
        setLocation(xOffset, yOffset);
    }
}