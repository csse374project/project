package classRepresentation;

import java.util.ArrayList;
import java.util.List;

import interfaces.*;

public class Class implements IClass {
	
	String name;
	String superClass;
	List<String> interfaces;
	List<IField> fields;
	List<IMethod> methods;
	
	public Class(){
		this.name = "";
		this.superClass = "";
		this.interfaces = new ArrayList<String>();
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		
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

}
