package classRepresentation;

import java.util.ArrayList;
import java.util.List;

import interfaces.*;

public class Method implements IMethod {

	private List<String> parameters;
	private String name;
	private char visibility;
	private List<String> nonAccessModifiers;
	private String returnType;

	public Method() {
		this.parameters = new ArrayList<String>();
		this.name = "";
		this.visibility = ' ';
		this.nonAccessModifiers = new ArrayList<String>();
		this.returnType = "";
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public void setReturnType(String type) {
		returnType = type;
	}

	@Override
	public List<String> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<String> params) {
		parameters = params;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public char getVisibility() {
		return visibility;
	}

	@Override
	public void setVisibility(char vis) {
		visibility = vis;
	}

	@Override
	public List<String> getNonAccessModifiers() {
		return nonAccessModifiers;
	}

	@Override
	public void setNonAccessModifiers(List<String> mods) {
		nonAccessModifiers = mods;
	}
	
	public boolean equals(Object o){
		if(! (o instanceof Method)) return false;
		Method m = (Method)o;
		if(this.parameters.equals(m.getParameters()) && this.name.equals(m.getName()) && this.returnType.equals(m.getReturnType())
				&& this.visibility==m.getVisibility() && this.nonAccessModifiers.equals(m.getNonAccessModifiers())){
			return true;
		}
		return false;
	}
}
