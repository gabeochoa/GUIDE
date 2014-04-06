package com.bitcamp;

/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved. TeamDev PROPRIETARY
 * and CONFIDENTIAL. Use is subject to license terms.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

public class WorkingDemo
{

	public static JAccordian jac = null;
	public static JInternalFrame internalFrameBr;
	private static ImprovedTabs imp;
	public static HashMap<String, String> keywordToUrl = new HashMap<String, String>();
	public static DesktopScrollPane scrollyTheScrollPane;

	public static void main(String[] args) throws IOException
	{
		initializeURL_Map();
		JDesktopPane desktopPane = new JDesktopPane();
		JInternalFrame codeTabs = initalizeCodeTabs(desktopPane);
		codeTabs.setSize(640, 700);
		
		desktopPane.add(codeTabs);
		desktopPane.add(createInternalFrame("Browser",
				"http://www.cplusplus.com", 100));
		scrollyTheScrollPane = new DesktopScrollPane(desktopPane);
		wind frame = new wind(desktopPane);
		desktopPane.add(frame);
		scrollyTheScrollPane.resizeDesktop();
		// while(true)
		// update();
	}

	public static JInternalFrame getInternalFrame()
	{
		return internalFrameBr;
	}

	public static DesktopScrollPane getScrolly()
	{
		return scrollyTheScrollPane;
	}

	private static String update()
	{
		return imp.textArea.getText();
	 }

	public static String updateS()
	{
		/*
		 * For bars processing
		 */
		// return imp.textArea.getText();

		/*
		 * Gets the text that the user has selected.
		 */
		return imp.textArea.getSelectedText();
	}

	public static String getRawCodeTextBlock()
	{
		/*
		 * For bars processing
		 */
		return imp.textArea.getText();

		/*
		 * Gets the text that the user has selected.
		 */
		// return imp.textArea.getSelectedText();
	}

	public static JAccordian getJacced()
	{
		return jac;
	}

	public static void setJacced(JAccordian jac2)
	{
		WorkingDemo.jac = jac2;
	}

	public static JInternalFrame getBrowserFrame()
	{
		return internalFrameBr;
	}

	private static JInternalFrame initalizeCodeTabs(JDesktopPane deskPa)
	{
		CodeViewComponent internalFrame = new CodeViewComponent();
		imp = new ImprovedTabs();
		internalFrame.setContentPane(imp);
		internalFrame.setLocation(0, 0);
		internalFrame.setSize(640, 720);
		internalFrame.setVisible(true);
		internalFrame.pack();
		return internalFrame;
	}

	private static JInternalFrame createInternalFrame(String title, String url,
			int offset)
	{
		Browser browser = BrowserFactory.create();
		browser.loadURL(url);
		jac = new JAccordian();
		/*
		 * jac.addBar("One", browser.getView().getComponent());
		 * jac.addBar("Two", JAccordian.getDummyPanel("Two"));
		 * jac.addBar("Three", JAccordian.getDummyPanel("Three"));
		 * jac.addBar("Four", JAccordian.getDummyPanel("Four"));
		 * jac.addBar("Five", JAccordian.getDummyPanel("Five"));
		 */
		jac.setVisible(true);
		jac.setSize(100, 100);
		jac.setVisibleBar(0);

		internalFrameBr = new JInternalFrame(title, true);
		internalFrameBr.setContentPane(jac);
		// internalFrame.setContentPane(browser.getView().getComponent());
		internalFrameBr.setLocation(640, 0/* offset */);
		internalFrameBr.setSize(640, 720);
		internalFrameBr.setVisible(true);
		return internalFrameBr;
	}

	private static JInternalFrame createCodeFrame()
	{
		CodeViewComponent internalFrame = new CodeViewComponent();
		internalFrame.setLocation(0, 0);
		internalFrame.setSize(640, 720);
		internalFrame.setVisible(true);
		return internalFrame;
	}

	public static void initializeURL_Map() throws IOException
	{

		InputStream fis;
		BufferedReader br;
		String line;

		fis = new FileInputStream("referencePages.txt");
		br = new BufferedReader(new InputStreamReader(fis,
				Charset.forName("UTF-8")));
		while((line = br.readLine()) != null)
		{
			String stringComponents[] = line.split("\\s+");
			String importKey = stringComponents[0];
			String correspondUrl = stringComponents[1];
			keywordToUrl.put(importKey, correspondUrl);
		}

		// Done with the file
		br.close();
		br = null;
		fis = null;
	}
}