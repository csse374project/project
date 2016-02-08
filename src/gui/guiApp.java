package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class guiApp {
	
	private static Properties config;
	private static JFrame window;
 
	public static void main(String[] args) {
		displayLandingScreen();
	}
	
	private static void displayLandingScreen() {
		window = new JFrame();
		window.setTitle("Design Parser");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(400, 200);
		
		JPanel panel = new JPanel();
		panel.add(getLoadConfigButton(), BorderLayout.WEST);
		panel.add(getAnalyzeButton(), BorderLayout.EAST);
		window.add(panel, BorderLayout.CENTER);
		
		window.setVisible(true);
	}
	
	private static JButton getAnalyzeButton() {
		
		JButton analyzeButt = new JButton();
		analyzeButt.setText("Analyze Design!");
		analyzeButt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("ANALYZE");
			}
		});
		
		return analyzeButt;
	}
	
	private static JButton getLoadConfigButton() {
		JButton configButt = new JButton();
		configButt.setText("Load Config");
		configButt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("DEBUG - load confige");
				JFileChooser dialogue = new JFileChooser();
				dialogue.setCurrentDirectory(new File("input_output/"));
				int result = dialogue.showOpenDialog(window);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = dialogue.getSelectedFile();
					loadConfigFile(selectedFile);
//					System.out.println("you selected the file " + selectedFile.getAbsolutePath());
				}
			}
		});
		return configButt;
	}
	
	private static void loadConfigFile(File file) {
		config = new Properties();
		try {
			config.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Object key : config.keySet()) {
			System.out.printf("%s = %s\n", (String) key, config.getProperty((String) key));
		}
	}
	
	private static JPanel makeOptionPane() {
		// TODO
		return null;
	}
}
