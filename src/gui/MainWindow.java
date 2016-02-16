package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
	private List<String> classArgs;

	public MainWindow(List<String> classArgs) {
		this.classArgs = classArgs;
		setupFrame();
	}
	
	public JFrame get() {
		return frame;
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
		UMLParser parser = new UMLParser(classArgs);
		parser.parseByteCode();
	}
	
	private JScrollPane getOptionPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(new PatternViewsTree("decorator", Arrays.asList(new String[]{"decorator1", "decorator2", "thing"})));
		panel.add(new PatternViewsTree("singleton", Arrays.asList(new String[]{"Lonely", "SingleThing"})));
		panel.add(new PatternViewsTree("adapter", Arrays.asList(new String[]{"AdapterToDecoratorAdapter"})));
		JScrollPane scrollPanel = new JScrollPane(panel,
				 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(300, 1000));
		return scrollPanel;
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
				setupFrame();
			}
		});
		panel.add(button);
		return panel;
	}
	
	private JScrollPane getImagePanel() {
		ImageIcon image = new ImageIcon("input_output/TolkienMiddleEarthMap2.jpg");
//		Icon image = new LoadingProxy("input_output/TolkienMiddleEarthMap2.jpg");
		
		JScrollPane scrollPanel = new JScrollPane(new JLabel(image),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(700, 1000));
		return scrollPanel;
	}	
	
}
