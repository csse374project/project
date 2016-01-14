package classRepresentation;

import java.util.List;

import interfaces.IMethod;

public class SequenceMethod implements IMethod {

	private String name;
	private List<String> parameters;
	
	@Override
	public String getReturnType() {
		return null;
	}

	@Override
	public void setReturnType(String type) {
		// do nothing
	}

	@Override
	public List<String> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<String> params) {
		this.parameters = params;
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
		return '\0';
	}

	@Override
	public void setVisibility(char vis) {
		// do nothing
	}

	@Override
	public List<String> getNonAccessModifiers() {
		return null;
	}

	@Override
	public void setNonAccessModifiers(List<String> mods) {
		// do nothing
	}

}
