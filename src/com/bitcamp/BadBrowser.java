package com.bitcamp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BadBrowser extends JFrame
{

	public JPanel address_panel, window_panel;

	public JLabel address_label;

	public JTextField address_tf;

	public JEditorPane window_pane;

	public JScrollPane window_scroll;

	public JButton address_b;

	private Go go = new Go();

	public BadBrowser() throws IOException
	{

		// Define address bar
		address_label = new JLabel(" address: ", SwingConstants.CENTER);
		address_tf = new JTextField("http://www.yahoo.com");
		address_tf.addActionListener(go);
		address_b = new JButton("Go");
		address_b.addActionListener(go);

		window_pane = new JEditorPane("http://www.yahoo.com");
		window_pane.setContentType("text/html");
		window_pane.setEditable(false);

		address_panel = new JPanel(new BorderLayout());
		window_panel = new JPanel(new BorderLayout());

		address_panel.add(address_label, BorderLayout.WEST);
		address_panel.add(address_tf, BorderLayout.CENTER);
		address_panel.add(address_b, BorderLayout.EAST);

		window_scroll = new JScrollPane(window_pane);
		window_panel.add(window_scroll);

		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());

		pane.add(address_panel, BorderLayout.NORTH);
		pane.add(window_panel, BorderLayout.CENTER);

		setTitle("web browser");
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public class Go implements ActionListener
	{

		public void actionPerformed(ActionEvent ae)
		{

			try
			{

				window_pane.setPage(address_tf.getText());

			}
			catch(MalformedURLException e)
			{ // new URL() failed
				window_pane.setText("MalformedURLException: " + e);
			}
			catch(IOException e)
			{ // openConnection() failed
				window_pane.setText("IOException: " + e);
			}

		}

	}
}
