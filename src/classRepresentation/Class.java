package classRepresentation;

import java.util.HashMap;

import interfaces.*;

public class Class implements IClass {
	
	int visibility;
	HashMap<String, IField> fields;
	HashMap<String, IMethod> methods;

	@Override
	public void setVisability(int vis) {
		visibility = vis;
	}

	@Override
	public int getVisability() {
		return visibility;
	}

	@Override
	public void setFields(HashMap<String, IField> fields) {
		this.fields = fields;
	}

	@Override
	public HashMap<String, IField> getFields() {
		return fields;
	}

	@Override
	public void setMethods(HashMap<String, IMethod> methods) {
		this.methods = methods;
	}

	@Override
	public HashMap<String, IMethod> getMethods() {
		return methods;
	}

}
