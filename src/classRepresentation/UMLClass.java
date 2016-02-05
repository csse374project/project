package classRepresentation;

import java.util.ArrayList;
import java.util.List;

import interfaces.*;
import umlDiagram.UMLParser;

public class UMLClass implements IClass {

	private String name, superClass, color;
	private List<String> interfaces, associatedClasses, usedClasses;
	private List<IField> fields;
	private List<IMethod> methods;
	private List<String> stereotypes;
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
		this.stereotypes = new ArrayList<String>();
		this.color = "black";
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
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}
	
	@Override
	public void addAssociatedClass(String arg) {
		if (UMLParser.classIsUsed(arg) && !associatedClasses.contains(arg)) {
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
	public void appendGraphVizHeader(StringBuilder str) {
		int lastFwdSlash = name.lastIndexOf('/');
		str.append("\t");
		str.append(name.substring(lastFwdSlash + 1));
		str.append(" [\n\t\tlabel = \"{");
	}

	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		// do nothing
	}

	@Override
	public void appendGraphVizClassName(StringBuilder str) {
		str.append(name);
	}

	@Override
	public void appendGraphVizFields(StringBuilder str) {
		for (IField field : this.getFields()) {
			str.append(field.getVisibility() + " " + field.getName() + ": " + field.getType());
			if (!field.getInteriorTypes().isEmpty()) {
				str.append(field.getInteriorTypes().toString());
			}
			str.append("\\l");
		}
	}

	@Override
	public void appendGraphVizMethods(StringBuilder str) {
		for (IMethod method : this.getMethods()) {
			String methodName = method.getName();
			if (methodName.equals("<init>") || methodName.equals("<clinit>")) {
				continue;
			}
			int lastPeriod = method.getReturnType().lastIndexOf('.');
			String methodReturnType = method.getReturnType();
			if (lastPeriod > -1) {
				methodReturnType = methodReturnType.substring(lastPeriod + 1);
			}
			str.append(method.getVisibility() + " " + methodReturnType + " " + methodName + "(");
			String params = method.getParameters().toString().substring(1,
					method.getParameters().toString().length() - 1);
			str.append(params + ")\\l");
		}		
	}

	@Override
	public void appendGraphVizColor(StringBuilder str) {
		// do nothing (color defaults to black)
	}

	@Override
	public void appendGraphVizFooter(StringBuilder str) {
		str.append("\n\t]\n\n");
	}

	@Override
	public void toGraphViz(StringBuilder string) {
		throw new UnsupportedOperationException();
	}
}
