package classRepresentation;

import java.util.ArrayList;
import java.util.List;

import interfaces.*;
import umlDiagram.UMLParser;

public class UMLClass implements IClass {

	private String name;
	private String superClass;
	private List<String> interfaces, associatedClasses, usedClasses;
	private List<IField> fields;
	private List<IMethod> methods;
	private boolean isInterface, isSingleton;

	public UMLClass() {
		this.name = "";
		this.superClass = "";
		this.interfaces = new ArrayList<String>();
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.isInterface = false;
		this.associatedClasses = new ArrayList<String>();
		this.usedClasses = new ArrayList<String>();
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
	public void addAssociatedClass(String arg) {
		if (UMLParser.classIsUsed(arg) && !associatedClasses.contains(arg) && (!arg.equals(this.name))) {
			associatedClasses.add(arg);
			if (usedClasses.contains(arg)) {
				usedClasses.remove(arg);
			}
		}
	}

	@Override
	public void addUsedClass(String arg) {
		if (UMLParser.classIsUsed(arg) && !usedClasses.contains(arg) && !associatedClasses.contains(arg)
				&& (!arg.equals(this.name))) {
			usedClasses.add(arg);
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
	@Override
	public boolean isSingleton() {
		return isSingleton;
	}
	@Override
	public void setIsSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;		
	}

}
