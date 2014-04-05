package com.bitcamp;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;

/* Used by InternalFrameDemo.java. */
public class Newdayclass extends JInternalFrame {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static String refObj = "";
	int inset = 50;

	int width = screenSize.width / 2 - 125;
	int height = screenSize.height - 175;
	int xOffset = (screenSize.width / 2), yOffset = 0;

	public Newdayclass(String ref) {
		super("Reference Documentation " + ref, 
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
		pack();
		setVisible(true);
		revalidate();
	}

	public void p()
	{
		JAccordian jac = new JAccordian();
		jac.addBar( "One", JAccordian.getDummyPanel( "One" ) );
		jac.addBar( "Two", JAccordian.getDummyPanel( "Two" ) );
		jac.addBar( "Three", JAccordian.getDummyPanel( "Three" ) );
		jac.addBar( "Four", JAccordian.getDummyPanel( "Four" ) );
		jac.addBar( "Five", JAccordian.getDummyPanel( "Five" ) );
		jac.setVisibleBar(2);
		jac.setVisible(true);
		jac.setSize(100,100);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jac, BorderLayout.CENTER);
		pack();
	}

	public Newdayclass() {
		super("Reference Documentation ", 
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

	public void paintComponent(Graphics g)
	{
		g.fillOval(0,0,221,220);
		repaint();
	}
	@Override
	public void paint(Graphics g) {
	    super.paint(g);  
	}
}