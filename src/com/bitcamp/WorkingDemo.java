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
	private static ImprovedTabs imp;
	public static HashMap<String, String> keywordToUrl = new HashMap<String, String>();

	public static void main(String[] args) throws IOException
	{
		initializeURL_Map();
		JDesktopPane desktopPane = new JDesktopPane();
		JInternalFrame codeTabs = initalizeCodeTabs(desktopPane);
		codeTabs.setSize(640, 720);
		desktopPane.add(codeTabs);
		desktopPane.add(createInternalFrame("Browser Two",
				"http://www.cplusplus.com", 100));
		DesktopScrollPane scrollPane = new DesktopScrollPane(desktopPane);
		wind frame = new wind(desktopPane);
		desktopPane.add(frame);
		scrollPane.resizeDesktop();
		// while(true)
		// update();
	}

	// private static void update()
	// {
	// System.out.println(imp.textArea.getText());
	// }

	public static String updateS()
	{
		return imp.textArea.getText();
	}

	public static JAccordian getJacced()
	{
		return jac;
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
		/*jac.addBar("One", browser.getView().getComponent());
		jac.addBar("Two", JAccordian.getDummyPanel("Two"));
		jac.addBar("Three", JAccordian.getDummyPanel("Three"));
		jac.addBar("Four", JAccordian.getDummyPanel("Four"));
		jac.addBar("Five", JAccordian.getDummyPanel("Five"));*/
		jac.setVisible(true);
		jac.setSize(100, 100);
		jac.setVisibleBar(0);

		JInternalFrame internalFrame = new JInternalFrame(title, true);
		internalFrame.setContentPane(jac);
		//internalFrame.setContentPane(browser.getView().getComponent());
		internalFrame.setLocation(640, 0);
		internalFrame.setSize(640, 720);
		internalFrame.setVisible(true);
		return internalFrame;
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