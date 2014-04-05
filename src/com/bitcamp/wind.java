package com.bitcamp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class wind extends JFrame implements ActionListener
{

	public wind(DesktopScrollPane sc)
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(sc, BorderLayout.CENTER);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setJMenuBar(createMenuBar(this));
		setVisible(true);
	}

	protected JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("Refresh");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("refresh");
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
     
    public void actionPerformed(ActionEvent e) {

    	System.out.println(e.getActionCommand());
        if ("refresh".equals(e.getActionCommand())) { //new
            //donothing
        	System.out.println("R");
        } else { //quit
            System.exit(0);
        }
    }
}
