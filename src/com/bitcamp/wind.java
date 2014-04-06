package com.bitcamp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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

		// Set up the Compile and Run item
		menuItem = new JMenuItem("Compile and Run");
		menuItem.setMnemonic(KeyEvent.VK_M);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("Compile and Run");
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
		/*
		 * User wants a refreash
		 */
		if("refresh".equals(e.getActionCommand()))
		{ // new
			pushUrlToBrowser();
			// try
			// {
			// /*
			// * Clear existing bars
			// */
			// WorkingDemo.getJacced().removeAll();
			// /*
			// * Put the urls into the respective browser bars.
			// */
			// fetchUrl();
			// }
			// catch(IOException e1)
			// {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// System.out.println("R");
		}
		// Put Compile and Run stuff here
		else if("Compile and Run".equals(e.getActionCommand()))
		{
			// do stuff
			try
			{
				compileCodeViewCode();
			}
			catch(FileNotFoundException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch(UnsupportedEncodingException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch(IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{ // quit
			System.exit(0);
		}
	}

	/*
	 * public void fetchUrl() throws IOException {
	 * 
	 * The text the user entered into the CodeViewComponent (left side of
	 * program).
	 * 
	 * String rawText = WorkingDemo.updateS();
	 * 
	 * Our accordian panel for the bars that hold a browser
	 * 
	 * JAccordian jac = WorkingDemo.getJacced();
	 * 
	 * 
	 * Fill the bars
	 * 
	 * populateBrowserIntAcrd(jac, rawText); }
	 * 
	 * public void populateBrowserIntAcrd(JAccordian jac, String rText) throws
	 * IOException {
	 * 
	 * 
	 * WorkingDemo contains the master mapping of all imports in referencePages
	 * to their respective reference page urls.
	 * 
	 * stuffToAdd is a subset of this map that contains only the mappings
	 * present within the user's code (i.e. if the user wrote <vector>, <list>,
	 * it only has the two mappings from the master mapping: vector's and list's
	 * 
	 * HashMap<String, String> stuffToAdd = new HashMap<String, String>(); Make
	 * the initial bar Browser brow = BrowserFactory.create();
	 * brow.loadURL("http://www.cplusplus.com");
	 * 
	 * // jac.addBar("Welcome", JAccordian.getDummyPanel("Welcome"));
	 * jac.setVisibleBar(0); jac.setVisible(true); jac.setSize(100, 100);
	 * 
	 * Populate stuffToAdd findExistentMappings(stuffToAdd, rText);
	 * 
	 * Use the completed hashmap to initialize the bars addMyBars(stuffToAdd,
	 * jac);
	 * 
	 * }
	 * 
	 * public void findExistentMappings(HashMap<String, String>
	 * presentImportMap, String rText) throws IOException { // convert String
	 * into InputStream InputStream is = new
	 * ByteArrayInputStream(rText.getBytes());
	 * 
	 * // read it with BufferedReader BufferedReader br = new BufferedReader(new
	 * InputStreamReader(is));
	 * 
	 * 
	 * We read lines until we reach a line that doesn't start with '#', i.e. a
	 * non-#include line
	 * 
	 * String line; while((line = br.readLine()) != null && line.charAt(0) ==
	 * '#') { String stringComponents[] = line.split("\\s+");
	 * 
	 * [0] is the #include part, useless [1] is "<blah>" = key
	 * 
	 * String importKey = stringComponents[1]; String url =
	 * WorkingDemo.keywordToUrl.get(importKey); presentImportMap.put(importKey,
	 * url); }
	 * 
	 * br.close(); }
	 * 
	 * public void addMyBars(HashMap<String, String> curImportMaps, JAccordian
	 * jac) {
	 * 
	 * Get the keys into an iterable set so as to facillitate bar titling
	 * 
	 * Set<String> importSet = curImportMaps.keySet();
	 * 
	 * for(String curImport : importSet) { A browser for each bar Browser brow =
	 * BrowserFactory.create(); brow.loadURL(curImportMaps.get(curImport));
	 * 
	 * (incorrectly) size it jac.addBar(curImport,
	 * brow.getView().getComponent()); jac.setVisible(true); jac.setSize(100,
	 * 100); jac.setVisibleBar(0); } }
	 */
	public void pushUrlToBrowser()
	{
		/*
		 * The text the user entered into the CodeViewComponent (left side of
		 * program).
		 */
		JAccordian jac = WorkingDemo.getJacced();
		jac.invalidate();
		JAccordian jac2 = new JAccordian();
		WorkingDemo.setJacced(jac2);
		Browser brow = BrowserFactory.create();
		String selectedText = WorkingDemo.updateS();
		String url = "http://www.cplusplus.com";
		if(WorkingDemo.keywordToUrl.containsKey(selectedText))
		{
			url = WorkingDemo.keywordToUrl.get(selectedText);
		}
		else
		{
			return;
		}

		// String stringComponents[] = rawText.split("\\s+");
		/*
		 * [0] is the #include part, useless [1] is "<blah>" = key
		 */
		// String importKey = stringComponents[1];
		// String url = WorkingDemo.keywordToUrl.get(selectedText);
		/*
		 * Our accordian panel for the bars that hold a browser
		 */

		brow.loadURL(url);
		jac2.addBar(selectedText, brow.getView().getComponent());
		jac2.setLocation(0, 0);
		jac2.setSize(640, 720);
		jac2.setVisible(true);
		jac2.validate();
		WorkingDemo.getInternalFrame().setContentPane(jac2);
		// WorkingDemo.getScrolly().resizeDesktop();
	}

	public void compileCodeViewCode() throws IOException
	{
		/* Our code */
		String code = WorkingDemo.getRawCodeTextBlock();

		/* Our source file */
		String sourceName = "seg-fault.cpp";
		String executableName = "seggyTheSegFault.yolo";
		PrintWriter writer = new PrintWriter("seg-fault.cpp", "UTF-8");
		writer.println(code);
		writer.close();

		/*
		 * Write the compile command to the terminal.
		 */
		StringBuilder command = new StringBuilder("g++ -o ");
		command.append(sourceName);
		command.append("executableName ");
		command.append("sourceName");
		String cmdString = command.toString();
		ProcessBuilder pb = new ProcessBuilder(cmdString);
		Process compileDatCode = pb.start();
	}
}
