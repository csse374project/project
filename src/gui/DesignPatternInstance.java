package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class DesignPatternInstance implements Iterable<String> {
	
	private String designPattern;
	private String instanceName;
	private List<String> classNames;
	
	public DesignPatternInstance(String instanceName, String designPattern, List<String> classNames) {
		this.instanceName = instanceName;
		this.designPattern = designPattern;
		this.classNames = classNames;
	}
	
	public DesignPatternInstance(String instanceName, String designPattern) {
		this(instanceName, designPattern, new ArrayList<String>());
	}
	
	public String getDesignPattern() {
		return designPattern;
	}
	
	public String getInstanceName() {
		return instanceName;
	}
	
	public List<String> getClasses() {
		return classNames;
	}
	
	public void addClass(String name) {
		classNames.add(name);
	}

	@Override
	public Iterator<String> iterator() {
		return classNames.iterator();
	}
	
	@Override
	public void forEach(Consumer<? super String> action) {
		classNames.forEach(action);
	}
	
}
