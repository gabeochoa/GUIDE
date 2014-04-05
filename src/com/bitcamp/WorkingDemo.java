package com.bitcamp;

/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved. TeamDev PROPRIETARY
 * and CONFIDENTIAL. Use is subject to license terms.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

public class WorkingDemo
{

	public static JAccordian jac = null;
	private static ImprovedTabs imp;

	public static void main(String[] args)
	{
		JDesktopPane desktopPane = new JDesktopPane();
		JInternalFrame codeTabs = initalizeCodeTabs(desktopPane);
		desktopPane.add(codeTabs);
		desktopPane.add(createInternalFrame("Browser Two",
				"http://www.cplusplus.com", 100));
		DesktopScrollPane scrollPane = new DesktopScrollPane(desktopPane);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		scrollPane.resizeDesktop();
		while(true)
			update();
	}

	private static void update()
	{
		System.out.println(imp.textArea.getText());
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

		jac.addBar("One", browser.getView().getComponent());
		jac.addBar("Two", JAccordian.getDummyPanel("Two"));
		jac.addBar("Three", JAccordian.getDummyPanel("Three"));
		jac.addBar("Four", JAccordian.getDummyPanel("Four"));
		jac.addBar("Five", JAccordian.getDummyPanel("Five"));
		jac.setVisibleBar(2);
		jac.setVisible(true);
		jac.setSize(100, 100);
		jac.setVisibleBar(0);

		JInternalFrame internalFrame = new JInternalFrame(title, true);
		internalFrame.setContentPane(jac);
		// internalFrame.setContentPane(browser.getView().getComponent());
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
}