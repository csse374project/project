package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import umlDiagram.UMLParser;

public class MainWindow {
	
	private JFrame frame;
	private Properties config;
	private List<String> classArgs, phases;
	private String inputFolder, outputDirectory, dotPath;
	private List<PatternViewsTree> buttonTrees;

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
		this.inputFolder = config.getProperty("inputFolder");
		this.outputDirectory = config.getProperty("outputDirectory");
		this.dotPath = config.getProperty("dotPath");
	}

	private void loadClassArgs() {
		if (buttonTrees.size() == 0) {
			this.classArgs = Arrays.asList(config.getProperty("targetClasses").split(" "));
		} else {
			loadClassArgsFromButtons();
		}
	} 

	private void loadClassArgsFromButtons() {
		this.classArgs = new ArrayList<>();
		System.out.print("DEBUG: ");
		for (PatternViewsTree tree : buttonTrees) {
			classArgs.addAll(tree.getClassesToParse());
		}
		
		for (String arg : classArgs) {
			System.out.print(arg + " ");
		} System.out.println();
	}
	
	private void setupFrame() {
		frame = new JFrame("name");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		try {
			runUMLparser();
			frame.add(getOptionPanel(), BorderLayout.WEST);
			frame.add(getImagePanel(), BorderLayout.EAST);
			frame.add(getReloadPanel(), BorderLayout.SOUTH);
		} catch (IOException e) {
			frame.add(getExceptionPanel());
		}
		frame.setVisible(true);
	}
	
	private JPanel getExceptionPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("failed to load design :("));
		return panel;
	}
	
	private void runUMLparser() throws IOException {
		UMLParser parser = new UMLParser(classArgs, inputFolder, outputDirectory, dotPath, phases);
		parser.parseByteCode();
	}
	
	private JScrollPane getOptionPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		List<List<DesignPatternInstance>> designs = getDesignPatterns();
		for (List<DesignPatternInstance> list : designs) {
			PatternViewsTree newTree = new PatternViewsTree(list, "<pattern>");
			buttonTrees.add(newTree);
			panel.add(newTree);
		}
		JScrollPane scrollPanel = new JScrollPane(panel,
				 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(300, 1000));
		return scrollPanel;
	}
	
	private List<List<DesignPatternInstance>> getDesignPatterns() {
		List<DesignPatternInstance> innerList = new ArrayList<>();
		DesignPatternInstance instance = new DesignPatternInstance("decorator");
		instance.addClass("decorator1");
		instance.addClass("decorator2");
		instance.addClass("decorator3");
		innerList.add(instance);
		
		instance = new DesignPatternInstance("singleton");
		instance.addClass("Lonely");
		instance.addClass("singleThing");
		innerList.add(instance);
		
		instance = new DesignPatternInstance("adapter");
		instance.addClass("AdapterToDecoratorAdapter");
		innerList.add(instance);
		
		List<List<DesignPatternInstance>> list = new ArrayList<>();
		list.add(innerList);
		
		innerList = new ArrayList<>();
		instance = new DesignPatternInstance("thingamabob-pattern");
		instance.addClass("[class]");
		innerList.add(instance);
		list.add(innerList);
		return list;
	}
	
	private JPanel getReloadPanel() {
		JPanel panel = new JPanel();
		JButton button = new JButton("reload design");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reload button pressed");
				frame.setVisible(false);
				frame.dispose();
				loadClassArgs();
				setupFrame();
			}
		});
		panel.add(button);
		return panel;
	}
	
	private JScrollPane getImagePanel() {
		ImageIcon image = new ImageIcon("input_output/out.png");
//		Icon image = new LoadingProxy("input_output/TolkienMiddleEarthMap2.jpg");
		
		JScrollPane scrollPanel = new JScrollPane(new JLabel(image),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(700, 1000));
		return scrollPanel;
	}	
	
}
