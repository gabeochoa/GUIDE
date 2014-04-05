package com.bitcamp;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WebView extends javafx.application.Application implements MouseListener {
	 private javafx.scene.web.WebView root = null;
	 private int mousex, mousey;
	 
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        // create WebView with specified local content
    	root = new javafx.scene.web.WebView();
        root.getEngine().load("http://www.cplusplus.com/reference/vector/vector/");
      
        primaryStage.setScene(new javafx.scene.Scene(root, 1100, 1000));
        primaryStage.setTitle("WebView");
        primaryStage.show();
    }
   
    public static void main(String[] args) {
    	launch(args);
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mousex = arg0.getX();
		mousex = arg0.getX();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mousex = 0;//arg0.getX();
		mousey = 0;//arg0.getX();
	}
}