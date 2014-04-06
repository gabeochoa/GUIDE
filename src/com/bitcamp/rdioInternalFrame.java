package com.bitcamp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class rdioInternalFrame extends JInternalFrame
{
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

	public rdioInternalFrame(String ref)
	{
		super("rdioPanel " + ref, true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		// ...Then set the window size or call pack...
		setSize(width, height);

		// Set the window's location.
		setLocation(xOffset, yOffset);
		p();
		setVisible(true);
	}

	public void p()
	{
		JDesktopPane desktopPane = new JDesktopPane();
		CodeViewComponent internalFrame = new CodeViewComponent();

		internalFrame.setLocation(0, 0);
		internalFrame.setSize(640, 720);
		internalFrame.setVisible(true);
		CodeViewComponent cp = new CodeViewComponent();
		cp.setVisible(true);
		desktopPane.add(cp);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(cp, BorderLayout.WEST);
		pack();

	}

	public rdioInternalFrame()
	{
		super("rdio", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable

		// ...Then set the window size or call pack...
		setSize(width, height);
		// Set the window's location.
		setLocation(xOffset, yOffset);

		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(width, height);

		// Set the window's location.
		setLocation(xOffset, yOffset);
		p();
		setVisible(true);
	}
}