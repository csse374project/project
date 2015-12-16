package classRepresentation;

import java.util.List;

import interfaces.*;

public class Method implements IMethod {

	List<Parameter> parameters;
	String name;
	char visibility;
	List<String> nonAccessModifiers;
	String returnType;
	
	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public void setReturnType(String type) {
		returnType = type;
	}

	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<Parameter> params) {
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

}
