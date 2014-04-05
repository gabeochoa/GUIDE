package com.bitcamp;

/*
 * Either we handle every key press and write that character to the
 * stringbuilder, then use set text to commit the text. OR, we use a text field
 * in a pane instead of this panel.
 * 
 * When the user hits backspace, we want to delete a character. Selection and
 * deletion would also be nice.
 * 
 * The issue lies in that JEditorPane can handle html formatted text (think
 * colors for syntax hightlighting), but I dont think textfields can.
 */
import javax.swing.JEditorPane;
import javax.swing.JPanel;

public class CodePanel extends JPanel
{
	private JEditorPane codePane = new JEditorPane();
	private StringBuilder curCodeString = new StringBuilder();

	public void commitText(String userKeyIn)
	{
		curCodeString.append(userKeyIn);
		codePane.setText(curCodeString.toString());
	}

	public void deleteText(String userBackSpace)
	{
		curCodeString.deleteCharAt(curCodeString.lastIndexOf(curCodeString
				.toString()));
		codePane.setText(curCodeString.toString());
	}
}