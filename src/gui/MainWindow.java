package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	public MainWindow() {
		super();
		setup();
	}
	
	private void setup() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);
		
		this.add(getOptionPanel(), BorderLayout.WEST);
		this.add(getImagePanel(), BorderLayout.EAST);
		this.add(getReloadPanel(), BorderLayout.SOUTH);
		
//		landingWindow.setVisible(false);
		this.setVisible(true);
//		landingWindow.dispose();
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
//				setup();
				add(new JLabel("nope"), BorderLayout.WEST);
				add(new JLabel("yes"), BorderLayout.EAST);
			}
		});
		panel.add(button);
		return panel;
	}
	
	private JScrollPane getImagePanel() {
//		ImageIcon image = new ImageIcon("input_output/TolkienMiddleEarthMap2.jpg");
		Icon image = new LoadingProxy("input_output/TolkienMiddleEarthMap2.jpg");
		
		JScrollPane scrollPanel = new JScrollPane(new JLabel(image),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(700, 1000));
		return scrollPanel;
	}	
	
}
