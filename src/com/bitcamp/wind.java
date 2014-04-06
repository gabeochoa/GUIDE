package com.bitcamp;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

public class wind extends JFrame implements ActionListener
{
	public String direc = "";
	public String dir = "";
	private JDesktopPane daddy;

	public wind(JDesktopPane sc)
	{
		setTitle("guIDE");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(sc, BorderLayout.CENTER);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setJMenuBar(createMenuBar(this));
		setVisible(true);

		daddy = sc;
		direc = System.getProperty("user.dir");
	}

	protected JMenuBar createMenuBar(JFrame frame)
	{
		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuItem;

		// Set up the lone menu.
		JMenu filemenu = new JMenu("File");
		//filemenu.setMnemonic(KeyEvent.VK_D);
		menuBar.add(filemenu);

		// Set up the Compile and Run item
		menuItem = new JMenuItem("Open");
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("Open");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

		// Set up the second menu item.
		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("quit");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

		JMenu buildmenu = new JMenu("Build");
		menuBar.add(buildmenu);

		// Set up the Compile and Run item
		menuItem = new JMenuItem("Compile and Run");
		menuItem.setMnemonic(KeyEvent.VK_F5);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		menuItem.setActionCommand("Compile and Run");
		menuItem.addActionListener(this);
		buildmenu.add(menuItem);


		JMenu docmenu = new JMenu("Documentation");
		menuBar.add(docmenu);

		// Set up the first menu item.
		menuItem = new JMenuItem("Open Documentation");
		menuItem.setMnemonic(KeyEvent.VK_F11);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11,0));
		menuItem.setActionCommand("refresh");
		menuItem.addActionListener(this);
		docmenu.add(menuItem);
		//F11

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
		}
		// Put Compile and Run stuff here
		else if("Open".equals(e.getActionCommand()))
		{
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            //This is where a real application would open the file.
	            System.out.println("Opening: " + file.getName() + ".");
	            
	    		InputStream    fis = null;
	    		BufferedReader br;
	    		String         line;
	    		try
				{
	    			dir = file.getPath();
					fis = new FileInputStream(file.getPath());
				} catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	    		try
				{
					while ((line = br.readLine()) != null) {
					    //filetext.add(line);
					    WorkingDemo.appentText(line);
					}
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		// Done with the file
	    		try
				{
					br.close();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		br = null;
	    		fis = null;
	        } else {
	        	System.out.println("Open command cancelled by user.");
	        }
			
		}
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
			} catch (InterruptedException e1)
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

		boolean s = false;
		String corkey = ""; 
		for(String key : WorkingDemo.keywordToUrl.keySet())
			for(String selT : selectedText.split("\\r?\\n"))
				if(selT.contains(key))
				{
					corkey = key;
					s = true;
				}
		System.out.println("\n\n\n\n\n\n\n"+s);

		//if(WorkingDemo.keywordToUrl.containsKey(selectedText))
		if(s)
		{
			url = WorkingDemo.keywordToUrl.get(corkey);
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
		brow.zoomOut();
		jac2.addBar(corkey, brow.getView().getComponent());
		jac2.setLocation(0, 0);
		jac2.setSize(640, 720);
		jac2.setVisible(true);
		jac2.validate();
		WorkingDemo.getInternalFrame().setContentPane(jac2);
		//WorkingDemo.getScrolly().resizeDesktop();
	}

	public void compileCodeViewCode() throws IOException, InterruptedException
	{

		ArrayList<String> command = new ArrayList<String>();
	   	command.add("g++");
	    command.add("-o");
	    command.add("avc");
	    command.add("helloworld.cpp");
	    SysComEx sce = new SysComEx(command);
	    		
	    int result = 0;
		System.out.println("result val: " + result);
		// get the output from the command
		StringBuilder stdout = sce.getStandardOutputFromCommand();
		StringBuilder stderr = sce.getStandardErrorFromCommand();

//		// print the output from the command
		System.out.println("STDOUT");
		System.out.println(stdout);
		System.out.println("STDERR");
		System.out.println(stderr);
		
		/* Our code */
		String code = WorkingDemo.getRawCodeTextBlock();

		/* Our source file */

		String[] f = dir.contains("\\") ? dir.split("\\") : dir.split("/");
		String sourceName = f[f.length-1];
		String executableName = "outputexec";
		//PrintWriter writer = new PrintWriter(dir, "UTF-8");
		//writer.println(code);
		//writer.close();

		/*
		 * Write the compile command to the terminal.
		 */
//		ArrayList<String> command = new ArrayList<String>();
//		command.add("usr/var/make ");
//		command.add("g++");
//		command.add(" -o ");
//		command.add(sourceName + " ");
//		command.add(executableName + " ");
//		command.add(sourceName);
		//ProcessBuilder pb = new ProcessBuilder(command);
		//Process compileDatCode = pb.start();
	}
}
