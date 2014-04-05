package com.bitcamp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

public class wind extends JFrame implements ActionListener
{

	private JDesktopPane daddy;

	public wind(JDesktopPane sc)
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(sc, BorderLayout.CENTER);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setJMenuBar(createMenuBar(this));
		setVisible(true);

		daddy = sc;
	}

	protected JMenuBar createMenuBar(JFrame frame)
	{
		JMenuBar menuBar = new JMenuBar();

		// Set up the lone menu.
		JMenu menu = new JMenu("Document");
		menu.setMnemonic(KeyEvent.VK_D);
		menuBar.add(menu);

		// Set up the first menu item.
		JMenuItem menuItem = new JMenuItem("Refresh");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("refresh");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Set up the second menu item.
		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e)
	{

		System.out.println(e.getActionCommand());
		if("refresh".equals(e.getActionCommand()))
		{ // new
			try
			{
				WorkingDemo.getJacced().removeAll();
				fetchUrl();
			}
			catch(IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("R");
		}
		else
		{ // quit
			System.exit(0);
		}
	}

	public void fetchUrl() throws IOException
	{
		final int CODE_VIEW_IND = 0;
		final int BROWSER_IND = 1;
		final int WIND_IND = 2;

		String rawText = WorkingDemo.updateS();
		JAccordian jac = WorkingDemo.getJacced();

		populateBrowserIntAcrd(jac, rawText);
	}

	public void populateBrowserIntAcrd(JAccordian jac, String rText)
			throws IOException
	{

		HashMap<String, String> stuffToAdd = new HashMap<String, String>();
		Browser brow = BrowserFactory.create();
		brow.loadURL("http://www.cplusplus.com");

		//jac.addBar("Welcome", JAccordian.getDummyPanel("Welcome"));
		jac.setVisibleBar(0);
		jac.setVisible(true);
		jac.setSize(100, 100);

		findExistentMappings(stuffToAdd, rText);

		addMyBars(stuffToAdd, jac);

	}

	public void findExistentMappings(HashMap<String, String> presentImportMap,
			String rText) throws IOException
	{
		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(rText.getBytes());

		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line;
		while((line = br.readLine()) != null && line.charAt(0) == '#')
		{
			String stringComponents[] = line.split("\\s+");
			String importKey = stringComponents[1];
			String url = WorkingDemo.keywordToUrl.get(importKey);
			presentImportMap.put(importKey, url);
		}

		br.close();
	}

	public void addMyBars(HashMap<String, String> curImportMaps, JAccordian jac)
	{
		Set<String> importSet = curImportMaps.keySet();

		for(String curImport : importSet)
		{
			Browser brow = BrowserFactory.create();
			brow.loadURL(curImportMaps.get(curImport));

			jac.addBar(curImport, brow.getView().getComponent());
			jac.setVisible(true);
			jac.setSize(100, 100);
			jac.setVisibleBar(0);
		}
	}
}
