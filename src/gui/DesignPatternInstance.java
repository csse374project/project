package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class DesignPatternInstance implements Iterable<String> {
	
	private String designName;
	private List<String> classNames;
	
	public DesignPatternInstance(String designPattern, List<String> classNames) {
		this.classNames = classNames;
	}
	
	public DesignPatternInstance(String designPattern) {
		this(designPattern, new ArrayList<String>());
	}
	
	public String getDesignPattern() {
		return designName;
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
