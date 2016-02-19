package gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class guiApp {

	private static final String[] designPatternNames = new String[] { "Adapter", "Composite", "Decorator",
			"Singleton" };

	private static Properties config;
	private static JFrame landingWindow, mainWindow;

	protected static HashMap<String, String[]> getPatternToSettings() {
		HashMap<String, String[]> map = new HashMap<>();
		for (String pattern : designPatternNames) {
			String[] array = config.getProperty(pattern).split(" ");
			if (array == null) {
				array = new String[] {};
			}
			map.put(pattern, array);
		}
		return map;
	}

	public static void main(String[] args) {
		displayLandingScreen();
	//	loadConfigFile(new File("input_output/config"));
	//	displayMainWindow();
	}

	private static void displayLandingScreen() {
		landingWindow = new JFrame();
		landingWindow.setTitle("Design Parser");
		landingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		landingWindow.setSize(400, 200);

		JPanel panel = new JPanel();
		panel.add(getLoadConfigButton(), BorderLayout.WEST);
		panel.add(getAnalyzeButton(), BorderLayout.EAST);
		panel.add(getAboutButton());
		panel.add(getHelpButton());
		landingWindow.add(panel, BorderLayout.CENTER);

		landingWindow.setVisible(true);
	}

	private static JButton getAboutButton() {
		JButton aboutButt = new JButton("About");

		aboutButt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedReader about = new BufferedReader(new FileReader("About"));
					String line;
					StringBuilder str = new StringBuilder();
					while ((line = about.readLine()) != null) {
						str.append(line);
						str.append("\n");
					}
					JOptionPane.showMessageDialog(landingWindow, str);
					about.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});

		return aboutButt;
	}

	private static JButton getHelpButton() {
		JButton helpButt = new JButton("Help");

		helpButt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedReader help = new BufferedReader(new FileReader("Help"));
					String line;
					StringBuilder str = new StringBuilder();
					while ((line = help.readLine()) != null) {
						str.append(line);
						str.append("\n");
					}
					JOptionPane.showMessageDialog(landingWindow, str);
					help.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});

		return helpButt;
	}

	private static JButton getAnalyzeButton() {

		JButton analyzeButt = new JButton();
		analyzeButt.setText("Analyze Design!");
		analyzeButt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (config == null) {
					String message = "please load a config file before trying to analyze";
					System.out.println(message);
					JOptionPane.showMessageDialog(landingWindow, message);
				} else {
					landingWindow.setVisible(false);
					landingWindow.dispose();
					displayMainWindow();
				}
			}
		});

		return analyzeButt;
	}

	private static JButton getLoadConfigButton() {
		JButton configButt = new JButton();
		configButt.setText("Load Config");
		configButt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialogue = new JFileChooser();
				dialogue.setCurrentDirectory(new File("input_output/"));
				int result = dialogue.showOpenDialog(landingWindow);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = dialogue.getSelectedFile();
					loadConfigFile(new File(selectedFile.getAbsolutePath()));
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
	}

	private static void displayMainWindow() {
		MainWindow window = new MainWindow(config);
		mainWindow = window.get();
		mainWindow.setTitle("Design Parser :: " + config.getProperty("inputDirec"));
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(1000, 1000);
		mainWindow.setVisible(true);
	}
}
