package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import umlDiagram.UMLParser;

public class MainWindow {
	
	private JFrame frame;
	private Properties config;
	private JScrollPane optionPanel;
	private List<String> classArgs, phases;
	private String inputFolder, outputDirectory, dotPath;
	private List<PatternViewsTree> buttonTrees;
	private UMLParser parser;

	public MainWindow(Properties config) {
		this.config = config;
		buttonTrees = new ArrayList<>();
		setupConfigs();
		loadClassArgs();
		setupFrame();
	}
	
	public JFrame get() {
		return frame;
	}
	
	private void setupConfigs() {
		this.phases = Arrays.asList(config.getProperty("phases").split(" "));
		this.inputFolder = config.getProperty("inputDirec");
		this.outputDirectory = config.getProperty("outputDirec");
		this.dotPath = config.getProperty("dotPath");
	}

	private void loadClassArgs() {
		if (buttonTrees.size() == 0) {
			this.classArgs = new ArrayList<String>(Arrays.asList(config.getProperty("targetClasses").split(" ")));
		} else {
			loadClassArgsFromButtons();
		}
	} 

	private void loadClassArgsFromButtons() {
		this.classArgs = new ArrayList<>();
		for (PatternViewsTree tree : buttonTrees) {
			classArgs.addAll(tree.getClassesToParse());
		}
	}
	
	private void setupFrame() {
		frame = new JFrame("name");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		try {
			runUMLparser();
			addOptionPanel();
//			frame.add(getOptionPanel(), BorderLayout.WEST);
			frame.add(getImagePanel(), BorderLayout.EAST);
			frame.add(getCommandPanel(), BorderLayout.SOUTH);
		} catch (IOException e) {
			frame.add(getExceptionPanel());
			e.printStackTrace();
		}
		frame.setVisible(true);
	}
	
	private void addOptionPanel() {
		if (optionPanel == null) {
			optionPanel = getOptionPanel();
		}
		frame.add(optionPanel, BorderLayout.WEST);
	}
	
	private JPanel getExceptionPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("failed to load design :("));
		return panel;
	}
	
	private void runUMLparser() throws IOException {
		parser = new UMLParser(classArgs, inputFolder, outputDirectory, dotPath, phases, guiApp.getPatternToSettings());
		parser.parseByteCode();
		parser.detectPatterns();
		parser.createGraph();
	}
	
	private JScrollPane getOptionPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		List<DesignPatternInstance> allInstances = parser.getDesignPatternInstances();
		Set<String> patternsUsed = getDesignPatternNames(allInstances);
		for (String pattern : patternsUsed) {
			List<DesignPatternInstance> list = getInstancesOfPattern(pattern, allInstances);
			PatternViewsTree newTree = new PatternViewsTree(list, pattern);
			buttonTrees.add(newTree);
			panel.add(newTree);
		}
		JScrollPane scrollPanel = new JScrollPane(panel,
				 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(300, 1000));
		return scrollPanel;
	}
	
	private List<DesignPatternInstance> getInstancesOfPattern(String pattern, List<DesignPatternInstance> allInstances) {
		List<DesignPatternInstance> instancesToUse = new ArrayList<>();
		for (DesignPatternInstance inst : allInstances) {
			if (inst.getDesignPattern().equals(pattern)) {
				instancesToUse.add(inst);
			}
		}
		return instancesToUse;
	}
	
	private Set<String> getDesignPatternNames(List<DesignPatternInstance> instances) {
		Set<String> set = new HashSet<String>();
		for (DesignPatternInstance inst : instances) {
			set.add(inst.getDesignPattern());
		}
		return set;
	}
	
	private JPanel getCommandPanel() {
		JPanel panel = new JPanel();
		JButton reloadButton = new JButton("reload design");
		reloadButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				loadClassArgs();
				setupFrame();
			}
		});
		panel.add(reloadButton);
		
		JButton exportButton = new JButton("export");
		exportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					runUMLparser();
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("select a directory to export to");
//					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.showOpenDialog(frame);
					File saveFile = chooser.getSelectedFile();
					if (saveFile == null) {
						return;
					}
					String savePath = saveFile.getAbsolutePath();
					if (saveFile.exists()) {
						saveFile.delete();
					}
					Files.copy(Paths.get("input_output/out.png"), Paths.get(savePath));
					
//					File fileToCopy = new File("input_output/out.png");
//					exportImage(fileToRead, fileToSaveTo);
				} catch (IOException e1) {
					e1.printStackTrace();
					String message = "failed to load design on export!\n" + e1.getMessage();
					System.out.println(message);
					JOptionPane.showMessageDialog(frame, message);
				}
			}
		});
		panel.add(exportButton);
		
		return panel;
	}
	
	private JScrollPane getImagePanel() {
		Icon image = new ImageProxy(outputDirectory + "/out.png");
		JLabel picture = new JLabel(image);
		
		JScrollPane scrollPanel = new JScrollPane(picture,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(700, 1000));
		return scrollPanel;
	}	
	
}
