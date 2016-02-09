package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class guiApp {
	
	private static Properties config;
	private static JFrame landingWindow, mainWindow;
	private static String inputDirec, outputDirec, dotPath;
	private static String[] targetClasses, designPatterns;
	private static String phases;
	private static List<JCheckBox> checkBoxes;
 
	public static void main(String[] args) {
//		displayLandingScreen();
		loadConfigFile(new File("input_output/config"));
		displayMainWindow();
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
		phases = config.getProperty("phases");
	}
	
	private static void displayMainWindow() {
		mainWindow = new JFrame();
		mainWindow.setTitle("Design Parser :: " + inputDirec);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(1000, 1000);
		
		mainWindow.add(getOptionPanel(), BorderLayout.WEST);
		mainWindow.add(getImagePanel(), BorderLayout.EAST);
		
//		landingWindow.setVisible(false);
		mainWindow.setVisible(true);
//		landingWindow.dispose();
	}
	
	private static JScrollPane getOptionPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(getButtonTree());
		JScrollPane scrollPanel = new JScrollPane(panel,
				 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(300, 1000));
		return scrollPanel;
	}
	
	private static JScrollPane getImagePanel() {
		JPanel panel = new JPanel();
		ImageIcon image = new ImageIcon("input_output/TolkienMiddleEarthMap2.jpg");
		
		
		JScrollPane scrollPanel = new JScrollPane(new JLabel(image),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(700, 1000));
		return scrollPanel;
	}
	
	private static JTree getButtonTree() {
		JTree tree;
//		TreeNode head = new DefaultMutableTreeNode("top of the big 'awesome' tree.");
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Top apowjfdpoawejdpojawpodjawpodjapowdjpoawj");
		DefaultMutableTreeNode node;
		String[] strings = new String[]{"1) one", "2) two", "3) three", "4) four", "5) five"};
		for (int i = 0; i < strings.length; i++) {
			node = new DefaultMutableTreeNode(strings[i]);
			top.add(node);
		}
		
		tree = new JTree(top);
//		tree.setSize(new Dimension(300, 1000));
		
		
		return tree;
	}
	
}
