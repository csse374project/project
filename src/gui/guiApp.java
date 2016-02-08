package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class guiApp {
 
	public static void main(String[] args) {
		displayLandingScreen();
	}
	
	private static void displayLandingScreen() {
		JFrame window = new JFrame();
		window.setTitle("Design Parser");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000, 1000);
		
		window.add(getLoadConfigButton());
		// TODO add things
		
		window.setVisible(true);
	}
	
	private static JButton getLoadConfigButton() {
		JButton configButt = new JButton();
		configButt.setText("Load Config");
		configButt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("YOU CLICKED A BUITTNONwkpodajwpodj");
			}
		});
		
		return configButt;
	}
	
	private static JPanel makeOptionPane() {
		// TODO
		return null;
	}
}
