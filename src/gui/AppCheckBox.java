package gui;

import javax.swing.JCheckBox;

public class AppCheckBox extends JCheckBox {

	private DesignPatternInstance designInstance;
	
	public AppCheckBox(DesignPatternInstance designInstance) {
		super(designInstance.getInstanceName());
		this.designInstance = designInstance;
	}
	
	public DesignPatternInstance getDesignInstance() {
		return designInstance;
	}
	
	
	
	
	
}
