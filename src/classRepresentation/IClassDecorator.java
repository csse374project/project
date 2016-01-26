package classRepresentation;

import java.util.List;

import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public abstract class IClassDecorator implements IClass {

	protected IClass decorates;
	public IClassDecorator() {
		decorates = null;
	}
	
	public void decorate(IClassDecorator cls) {
		//TODO maybe fix this
		//do nothing
	}
	
	public void setDecorates(IClass decorates) {
		this.decorates = decorates;
	}

	@Override
	public void addStereotype(String stereotype) {
		decorates.addStereotype(stereotype);
	}
	
	@Override
	public void setName(String name) {
		decorates.setName(name);
	}

	@Override
	public String getName() {
		return decorates.getName();
	}

	@Override
	public void setSuperClass(String superName) {
		decorates.setSuperClass(superName);
	}

	@Override
	public String getSuperClass() {
		return decorates.getSuperClass();
	}

	@Override
	public void setInterfaces(List<String> interfaceNames) {
		decorates.setInterfaces(interfaceNames);
	}

	@Override
	public List<String> getInterfaces() {
		return decorates.getInterfaces();
	}

	@Override
	public void setFields(List<IField> fields) {
		decorates.setFields(fields);
	}

	@Override
	public List<IField> getFields() {
		return decorates.getFields();
	}

	@Override
	public void setMethods(List<IMethod> methods) {
		decorates.setMethods(methods);
	}

	@Override
	public List<IMethod> getMethods() {
		return decorates.getMethods();
	}

	@Override
	public void addMethod(IMethod method) {
		decorates.addMethod(method);
	}

	@Override
	public void addField(IField field) {
		decorates.addField(field);
	}

	@Override
	public boolean isInterface() {
		return decorates.isInterface();
	}

	@Override
	public void setIsInterface(boolean isInterface) {
		decorates.setIsInterface(isInterface);
	}

	@Override
	public void addAssociatedClass(String arg) {
		decorates.addAssociatedClass(arg);
	}

	@Override
	public void addUsedClass(String arg) {
		decorates.addUsedClass(arg);
	}

	@Override
	public List<String> getAssociatedClasses() {
		return decorates.getAssociatedClasses();
	}

	@Override
	public List<String> getUsedClasses() {
		return decorates.getUsedClasses();
	}
	
	@Override
	public void setColor(String color) {
		decorates.setColor(color);
	}
	
	@Override
	public String getColor() {
		return decorates.getColor();
	}
}
