package com.bitcamp;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class idea extends JFrame implements Runnable, ActionListener
{
	private static final long serialVersionUID = -2673914251390653455L;

	// WINDOW SIZE
	public static double RES = 4 / 3;
	public static int HEIGHT = 600;
	public static int WIDTH = (int) (RES * HEIGHT);
	Dimension size;

	// GRAPHICS
	Graphics gr;
	Image buffer;

	private Thread thread;
	private boolean running;

	JDesktopPane desktop;

	public idea() throws IOException
	{
		size = new Dimension(WIDTH, HEIGHT);
		setSize(size);
//		setPreferredSize(size);
//		setMinimumSize(size);
//		setMaximumSize(size);
//
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("guIDE");

		int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
		
		// CREATING THE IMAGE AND BUFFER TO DRAW ON
		//buffer = createImage(size.width, size.height);
		//gr = buffer.getGraphics();

		desktop = new JDesktopPane();
		createFrame();
		setContentPane(desktop);
		setJMenuBar(createMenuBar());
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}
	

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }
	

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
            createFrame();
        } else { //quit
            quit();
        }
    }

    //Create a new internal frame.
    protected void createFrame() {
    	try{
        BadBrowser frame = new BadBrowser();
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }


	private void tick()
	{
		// updateWindows();

	}


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @throws IOException 
     */
    private static void createAndShowGUI() throws IOException {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        idea frame = new idea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try
				{
					createAndShowGUI();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }

	public void run()
	{
		try
		{
			Thread.sleep(20);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		while(true)
		{
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if(passedTime < 0)
				passedTime = 0;
			if(passedTime > 100000000)
				passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while(unprocessedSeconds > secondsPerTick)
			{
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if(tickCount % 60 == 0)
				{
					System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
				repaint();
			}

			if(ticked)
			{
				repaint();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(20);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(buffer, 0, 0, this);
	}

	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop()
	{
		if(!running)
			return;
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
