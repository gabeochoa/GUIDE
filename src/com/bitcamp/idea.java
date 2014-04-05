/*
 * package com.bitcamp;
 * 
 * import java.awt.BorderLayout; import java.awt.Container; import
 * java.awt.Dimension; import java.awt.Graphics; import java.awt.Image; import
 * java.awt.event.ActionEvent; import java.awt.event.KeyEvent;
 * 
 * import javax.swing.JDesktopPane; import javax.swing.JFrame; import
 * javax.swing.JMenu; import javax.swing.JMenuBar; import javax.swing.JMenuItem;
 * import javax.swing.JPanel; import javax.swing.KeyStroke;
 * 
 * public class idea extends JFrame implements Runnable { private static final
 * long serialVersionUID = -2673914251390653455L;
 * 
 * // WINDOW SIZE public static double RES = 4 / 3; public static int HEIGHT =
 * 600; public static int WIDTH = (int) (RES * HEIGHT); Dimension size;
 * 
 * // GRAPHICS Graphics gr; Image buffer;
 * 
 * private Thread thread; private boolean running;
 * 
 * JDesktopPane desktop;
 * 
 * public idea() { size = new Dimension(WIDTH, HEIGHT); setSize(size);
 * setPreferredSize(size); setMinimumSize(size); setMaximumSize(size);
 * 
 * setVisible(true); setDefaultCloseOperation(EXIT_ON_CLOSE); setTitle("guIDE");
 * 
 * // CREATING THE IMAGE AND BUFFER TO DRAW ON buffer = createImage(size.width,
 * size.height); gr = buffer.getGraphics();
 * 
 * desktop = new JDesktopPane(); JFrame in = createFrame(this); //
 * setContentPane(desktop); setContentPane(in.getContentPane());
 * setJMenuBar(createMenuBar());
 * 
 * }
 * 
 * private void tick() { // updateWindows();
 * 
 * }
 * 
 * public static void main(String[] args) { idea i = new idea();
 * 
 * while(true) i.run(); }
 * 
 * public void run() { try { Thread.sleep(20); } catch(Exception e) {
 * e.printStackTrace(); }
 * 
 * int frames = 0;
 * 
 * double unprocessedSeconds = 0; long lastTime = System.nanoTime(); double
 * secondsPerTick = 1 / 60.0; int tickCount = 0;
 * 
 * while(true) { long now = System.nanoTime(); long passedTime = now - lastTime;
 * lastTime = now; if(passedTime < 0) passedTime = 0; if(passedTime > 100000000)
 * passedTime = 100000000;
 * 
 * unprocessedSeconds += passedTime / 1000000000.0;
 * 
 * boolean ticked = false; while(unprocessedSeconds > secondsPerTick) { tick();
 * unprocessedSeconds -= secondsPerTick; ticked = true;
 * 
 * tickCount++; if(tickCount % 60 == 0) { System.out.println(frames + " fps");
 * lastTime += 1000; frames = 0; } repaint(); }
 * 
 * if(ticked) { repaint(); frames++; } else { try { Thread.sleep(20); }
 * catch(InterruptedException e) { e.printStackTrace(); } }
 * 
 * } }
 * 
 * protected JMenuBar createMenuBar() { JMenuBar menuBar = new JMenuBar();
 * 
 * // Set up the lone menu. JMenu menu = new JMenu("Document");
 * menu.setMnemonic(KeyEvent.VK_D); menuBar.add(menu);
 * 
 * // Set up the first menu item. JMenuItem menuItem = new JMenuItem("New");
 * menuItem.setMnemonic(KeyEvent.VK_N);
 * menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
 * ActionEvent.ALT_MASK)); menuItem.setActionCommand("new");
 * menuItem.addActionListener(null); menu.add(menuItem);
 * 
 * // Set up the second menu item. menuItem = new JMenuItem("Quit");
 * menuItem.setMnemonic(KeyEvent.VK_Q);
 * menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
 * ActionEvent.ALT_MASK)); menuItem.setActionCommand("quit");
 * menuItem.addActionListener(null); menu.add(menuItem);
 * 
 * return menuBar; }
 * 
 * // React to menu selections. public void actionPerformed(ActionEvent e) {
 * if("new".equals(e.getActionCommand())) { // new createFrame(this); } else {
 * // quit quit(); } }
 * 
 * // Create a new internal aframe. protected JFrame createFrame(idea i) {
 * WebPanel browser = new WebPanel("How do I even?"); JFrame lol = new JFrame();
 * lol.setVisible(true); // browser.setVisible(true);
 * 
 * JPanel jp = new JPanel(new BorderLayout()); JMenuBar inside =
 * lol.getJMenuBar(); Container jcp = lol.getContentPane(); if(inside != null)
 * jp.add(inside, BorderLayout.NORTH); jp.add(jcp, BorderLayout.CENTER);
 * 
 * i.add(jp); return lol; }
 * 
 * // Quit the application. protected void quit() { System.exit(0); }
 * 
 * public void paint(Graphics g) { g.drawImage(buffer, 0, 0, this); }
 * 
 * public synchronized void start() { if(running) return; running = true; thread
 * = new Thread(this); thread.start(); }
 * 
 * public synchronized void stop() { if(!running) return; running = false; try {
 * thread.join(); } catch(InterruptedException e) { e.printStackTrace(); } } }
 */