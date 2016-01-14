package sequenceDiagram;

import java.util.ArrayList;
import java.util.List;

import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class SequenceClass implements IClass {

	private String name, superName;
	private List<String> interfaces;
	private List<IMethod> methods;
	
	public SequenceClass() {
		interfaces = new ArrayList<String>();
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setSuperClass(String superName) {
		this.superName = superName;
	}

	@Override
	public String getSuperClass() {
		return superName;
	}

	@Override
	public void setInterfaces(List<String> interfaceNames) {
		interfaces = interfaceNames;
	}

	@Override
	public List<String> getInterfaces() {
		return interfaces;
	}

	@Override
	public void setFields(List<IField> fields) {
		// do nothing
	}

	@Override
	public List<IField> getFields() {
		return null;
	}

	@Override
	public void setMethods(List<IMethod> methods) {
		this.methods = methods;
	}

	@Override
	public List<IMethod> getMethods() {
		return methods;
	}

	@Override
	public void addMethod(IMethod method) {
		methods.add(method);
	}

	@Override
	public void addField(IField field) {
		// do nothing
	}

	@Override
	public boolean getIsInterface() {
		// do nothing
		return false;
	}

	@Override
	public void setIsInterface(boolean isInterface) {
		// do nothing
	}

	@Override
	public void addAssociatedClass(String arg) {
		// do Nothing
	}

	@Override
	public void addUsedClass(String arg) {
		// do nothing
	}

	@Override
	public List<String> getAssociatedClasses() {
		return null;
	}

	@Override
	public List<String> getUsedClasses() {
		return null;
	}

}
