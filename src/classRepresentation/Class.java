package classRepresentation;

import java.util.ArrayList;
import java.util.List;

import csse374project.DesignParser;
import interfaces.*;

public class Class implements IClass {

	private String name;
	private String superClass;
	private List<String> interfaces, associatedClasses, usedClasses;
	private List<IField> fields;
	private List<IMethod> methods;
	private boolean isInterface;

	public Class() {
		this.name = "";
		this.superClass = "";
		this.interfaces = new ArrayList<String>();
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.isInterface = false;
	}

	@Override
	public void setFields(List<IField> fields) {
		this.fields = fields;
	}

	@Override
	public List<IField> getFields() {
		return this.fields;
	}

	@Override
	public void setMethods(List<IMethod> methods) {
		this.methods = methods;
	}

	@Override
	public List<IMethod> getMethods() {
		return this.methods;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setSuperClass(String superName) {
		this.superClass = superName;
	}

	@Override
	public String getSuperClass() {
		return this.superClass;
	}

	@Override
	public void setInterfaces(List<String> interfaceNames) {
		this.interfaces = interfaceNames;
	}

	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}

	@Override
	public void addMethod(IMethod method) {
		this.methods.add(method);
	}

	@Override
	public void addField(IField field) {
		this.fields.add(field);
	}

	@Override
	public boolean getIsInterface() {
		return this.isInterface;
	}

	@Override
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	@Override
	public void addAssociatedClass(List<String> arg) {
		int size = arg.size();
		for (int i = 0; i < size; i++) {
			if (DesignParser.classIsUsed(arg.get(i))) {
				associatedClasses.add(arg.get(i));
			}
		}
	}

	@Override
	public void addUsedClass(List<String> arg) {
		int size = arg.size();
		for (int i = 0; i < size; i++) {
			if (DesignParser.classIsUsed(arg.get(i))) {
				usedClasses.add(arg.get(i));
			}
		}
	}
	
	@Override
	public List<String> getAssociatedClasses() {
		return associatedClasses;
	}
	@Override
	public List<String> getUsedClasses() {
		return usedClasses;
	}

}
