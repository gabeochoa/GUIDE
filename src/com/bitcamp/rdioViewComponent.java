package com.bitcamp;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/*
 * Built from Text.java. Made this a panel since the frame part was only being
 * used to display the demo. Designed to be ported into CodeInternalFrame
 */
public class rdioViewComponent extends JInternalFrame
{
	private rdioInternalFrame rint;

	public rdioViewComponent()
	{
		JPanel cp = new JPanel(new BorderLayout());
		//cp.add();

		setContentPane(cp);
		setTitle("Code");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		// setLocationRelativeTo(null);
	}

	// public static void main(String[] args) {
	// // Start all Swing applications on the EDT.
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// new CodeViewComponent().setVisible(true);
	// }
	// });
}