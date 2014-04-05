package com.bitcamp;

/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;

/**
 * The sample demonstrates how to use Browser in JInternalFrame components.
 */
public class WorkingDemo {
    public static void main(String[] args) {
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.add(createCodeFrame("Browser One", "http://www.teamdev.com", 0));
        desktopPane.add(createInternalFrame("Browser Two", "http://www.cplusplus.com", 100));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(desktopPane, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JInternalFrame createInternalFrame(String title, String url, int offset) {
        Browser browser = BrowserFactory.create();
        browser.loadURL(url);

        JInternalFrame internalFrame = new JInternalFrame(title, true);
        internalFrame.setContentPane(browser.getView().getComponent());
        internalFrame.setLocation(100 + offset, 100 + offset);
        internalFrame.setSize(400, 800);
        internalFrame.setVisible(true);
        return internalFrame;
    }

    private static JInternalFrame createCodeFrame(String title, String url, int offset) {
        JAccordian jac = new JAccordian();
        jac.addBar( "One", JAccordian.getDummyPanel( "One" ) );
		jac.addBar( "Two", JAccordian.getDummyPanel( "Two" ) );
		jac.addBar( "Three", JAccordian.getDummyPanel( "Three" ) );
		jac.addBar( "Four", JAccordian.getDummyPanel( "Four" ) );
		jac.addBar( "Five", JAccordian.getDummyPanel( "Five" ) );
		jac.setVisibleBar(2);
		jac.setVisible(true);
		jac.setSize(100,100);

    	
        CodeInternalFrame internalFrame = new CodeInternalFrame();
        internalFrame.setContentPane(jac);
        internalFrame.setLocation(100 + offset, 100 + offset);
        internalFrame.setSize(400, 400);
        internalFrame.setVisible(true);
        return internalFrame;
    }
}