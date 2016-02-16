package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class guiApp {
	
	private static Properties config;
	private static JFrame landingWindow, mainWindow;
	private static String inputDirec, outputDirec, dotPath;
	private static String[] targetClasses, designPatterns;
	private static String[] phases;
	private static List<JCheckBox> checkBoxes;
 
	public static void main(String[] args) {
		displayLandingScreen();
//		loadConfigFile(new File("input_output/config"));
//		displayMainWindow();
	}
	
	private static void displayLandingScreen() {
		landingWindow = new JFrame();
		landingWindow.setTitle("Design Parser");
		landingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		landingWindow.setSize(400, 200);
		
		JPanel panel = new JPanel();
		panel.add(getLoadConfigButton(), BorderLayout.WEST);
		panel.add(getAnalyzeButton(), BorderLayout.EAST);
		landingWindow.add(panel, BorderLayout.CENTER);
		
		landingWindow.setVisible(true);
	}
	
	private static JButton getAnalyzeButton() {
		
		JButton analyzeButt = new JButton();
		analyzeButt.setText("Analyze Design!");
		analyzeButt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ANALYZE");
				if (config == null) {
					// TODO
					String message = "please load a config file before trying to analyze";
					System.out.println(message);
					JOptionPane.showMessageDialog(landingWindow, message);
				} else {
					landingWindow.setVisible(false);
					displayMainWindow();
				}
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
				int result = dialogue.showOpenDialog(landingWindow);
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
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		inputDirec = config.getProperty("inputDirec");
		targetClasses = config.getProperty("targetClasses").split(" ");
		designPatterns = config.getProperty("designPatterns").split(" ");
		outputDirec = config.getProperty("outputDirec");
		dotPath = config.getProperty("dotPath");
		phases = config.getProperty("phases").split(" ");
	}
	
	private static void displayMainWindow() {
		System.out.print("target classes: ");
		for (int i = 0; i < targetClasses.length; i++) {
			System.out.print(targetClasses[i] + " ");
		}
		System.out.println();
		MainWindow window = new MainWindow(Arrays.asList(targetClasses));
		mainWindow = window.get();
		mainWindow.setTitle("Design Parser :: " + inputDirec);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(1000, 1000);
//		
//		mainWindow.add(getOptionPanel(), BorderLayout.WEST);
//		mainWindow.add(getImagePanel(), BorderLayout.EAST);
		
		landingWindow.setVisible(false);
		mainWindow.setVisible(true);
		landingWindow.dispose();
	}
}
