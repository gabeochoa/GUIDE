package com.bitcamp;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * - Neither the name of Oracle or the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * TabbedPaneDemo.java requires one additional file: images/middle.gif.
 */

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class ImprovedTabs extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1914707480635417028L;
	RSyntaxTextArea textArea;
	JMenuBar menu;

	public ImprovedTabs()
	{
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("images/middle.gif");

		textArea = new RSyntaxTextArea(20, 60);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);

		// Refresh button
		//JButton mybutton = new JButton("Refresh");
		// tabbedPane.add(mybutton);
		// Compile and Run
		//JButton mybutton2 = new JButton("Compile and Run");

		//JComponent panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab("Tab 1", icon, sp);
		System.out.println(textArea.getText());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

//		JComponent panel2 = makeTextPanel("Panel #2");
//		tabbedPane.addTab("Tab 2", icon, panel2);
//		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
//
//		JComponent panel3 = makeTextPanel("Panel #3");
//		tabbedPane.addTab("Tab 3", icon, panel3);
//		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
//
//		JComponent panel4 = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).");
//		panel4.setPreferredSize(new Dimension(410, 50));
//		tabbedPane.addTab("Tab 4", icon, panel4);
//		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

	}
	
	public ImprovedTabs(String title)
	{
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("images/middle.gif");

		textArea = new RSyntaxTextArea(20, 60);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);

		// Refresh button
		//JButton mybutton = new JButton("Refresh");
		// tabbedPane.add(mybutton);
		// Compile and Run
		//JButton mybutton2 = new JButton("Compile and Run");

		//JComponent panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab(title, icon, sp);
		System.out.println(textArea.getText());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

//		JComponent panel2 = makeTextPanel("Panel #2");
//		tabbedPane.addTab("Tab 2", icon, panel2);
//		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
//
//		JComponent panel3 = makeTextPanel("Panel #3");
//		tabbedPane.addTab("Tab 3", icon, panel3);
//		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
//
//		JComponent panel4 = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).");
//		panel4.setPreferredSize(new Dimension(410, 50));
//		tabbedPane.addTab("Tab 4", icon, panel4);
//		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

	}
	
	public void addPanel(String filename)
	{
		
	}

	protected JComponent makeTextPanel(String text)
	{
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	protected static JComponent makeTextPanel1(String text)
	{
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path)
	{
		java.net.URL imgURL = ImprovedTabs.class.getResource(path);
		if(imgURL != null)
		{
			return new ImageIcon(imgURL);
		}
		else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	protected static ImageIcon createImageIcon1(String path)
	{
		java.net.URL imgURL = ImprovedTabs.class.getResource(path);
		if(imgURL != null)
		{
			return new ImageIcon(imgURL);
		}
		else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}