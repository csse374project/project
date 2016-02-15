package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class DesignPatternInstance implements Iterable<String> {
	
	private List<String> classNames;
	
	public DesignPatternInstance(List<String> classNames) {
		this.classNames = classNames;
	}
	
	public DesignPatternInstance() {
		this(new ArrayList<String>());
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
