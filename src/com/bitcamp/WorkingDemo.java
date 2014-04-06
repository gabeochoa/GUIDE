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
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserFactory;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import java.util.logging.*;
public class WorkingDemo
{

	public static JAccordian jac = null;
	public static JInternalFrame internalFrameBr;
	public static JInternalFrame rdioFrame;
	public static Browser rdioBrowser;
	private static ImprovedTabs imp;
	public static HashMap<String, String> keywordToUrl = new HashMap<String, String>();
	public static DesktopScrollPane scrollyTheScrollPane;
	private static final int RDIO_HEIGHT = 75;
	private static final int SIDE_WIDTH = 632;
	
	public static void main(String[] args) throws IOException
	{
		initializeURL_Map();
		
		LoggerProvider.getBrowserLogger().setLevel(Level.OFF);
		LoggerProvider.getIPCLogger().setLevel(Level.OFF);
		LoggerProvider.getChromiumProcessLogger().setLevel(Level.OFF);
		
		JDesktopPane desktopPane = new JDesktopPane();
		codeTabs = initalizeCodeTabs(desktopPane);
		codeTabs.setSize(SIDE_WIDTH, 720);
		
		desktopPane.add(codeTabs);
		desktopPane.add(createInternalFrame("Browser",
				"http://www.cplusplus.com", 100));
		desktopPane.add(createRdioFrame("rdio"));
		scrollyTheScrollPane = new DesktopScrollPane(desktopPane);
		
		new wind(desktopPane);
		
		scrollyTheScrollPane.resizeDesktop();
	}

	public static JInternalFrame getInternalFrame()
	{
		return internalFrameBr;
	}

	public static DesktopScrollPane getScrolly()
	{
		return scrollyTheScrollPane;
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
	public static void setText(String s)
	{
		imp.textArea.setText(s);
	}
	
	public static String getSelectedText()
	{
		return imp.textArea.getSelectedText();
	}

	public static void pasteText()
	{
		imp.textArea.paste();
	}
	
	public static void selectAll()
	{
		imp.textArea.selectAll();
	}

	public static void cut()
	{
		imp.textArea.cut();
	}
	
	public static void clearText()
	{
		setText("");
	}
	public static void appentText(String s)
	{
		imp.textArea.append(s+"\n");
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
		internalFrame.setContentPane(new ImprovedTabs());
		internalFrame.setLocation(0, 0);
		internalFrame.setVisible(true);
		internalFrame.pack();
		return internalFrame;
	}
	
	public static JInternalFrame initalizeCodeTabs(JDesktopPane deskPa, String s)
	{
		CodeViewComponent internalFrame = new CodeViewComponent();
		imp = new ImprovedTabs(s);
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
		internalFrameBr.setLocation(SIDE_WIDTH, RDIO_HEIGHT);
		internalFrameBr.setSize(SIDE_WIDTH, 720 - RDIO_HEIGHT);
		internalFrameBr.setVisible(true);
		
		return internalFrameBr;
	}

	private static JInternalFrame createRdioFrame(String title)
	{
		BrowserContext context = new BrowserContext("bstorage_yolo");
		rdioBrowser = BrowserFactory.create(context);
		
		rdioBrowser.getCacheStorage().clearCache();
		
		rdioFrame = new JInternalFrame(title, false);
		rdioFrame.setContentPane(rdioBrowser.getView().getComponent());
		rdioFrame.setLocation(SIDE_WIDTH, 0);
		rdioFrame.setSize(SIDE_WIDTH, RDIO_HEIGHT);
		rdioFrame.setVisible(true);
		
		rdioBrowser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    
                    browser.executeJavaScript("var keys = [37, 38, 39, 40];");
                    
                    browser.executeJavaScript("function preventDefault(e) { e = e || window.event; if (e.preventDefault) e.preventDefault(); e.returnValue = false; }");
                    browser.executeJavaScript("function keydown(e) { for (var i = keys.length; i--;) { if (e.keyCode === keys[i]) { preventDefault(e); return; } } }");
                    browser.executeJavaScript("function wheel(e) { preventDefault(e); }");
                    
                    browser.executeJavaScript("window.addEventListener('DOMMouseScroll', wheel, false);");
                    browser.executeJavaScript("window.onmousewheel = document.onmousewheel = wheel;");
                    browser.executeJavaScript("document.onkeydown = keydown;");
                    browser.executeJavaScript("$('body').css('overflow', 'hidden'); $('body').mousedown(function(e){if(e.button==1)return false});");
                    
                    //browser.executeJavaScript("window.scrollTo(0, 0);");
                    
                    //PlayAlbum(browser, "a637574");
                }
            }
        });
		
		rdioBrowser.loadURL("http://guide-shaben.rhcloud.com");
		
		return rdioFrame;
	}

	public static void PlayAlbum(Browser browser, String id)
	{
		browser.executeJavaScript("$('#play_key').val('" + id + "'); $('#play').trigger('click');");
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