package classRepresentation;

import java.util.List;

import interfaces.IMethod;
	

public class SequenceMethod implements IMethod {

	private String methodName, invokerName, ownerName;
	private List<String> parameters;
	private boolean isInit;
	
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
		return methodName;
	}

	@Override
	public void setName(String name) {
		this.methodName = name;
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

	@Override
	public String getInvoker() {
		return invokerName;
	}

	@Override
	public String getOwner() {
		return ownerName;
	}

	@Override
	public void setInvoker(String ownerName) {
		this.invokerName = ownerName;
	}

	@Override
	public void setOwner(String recieverName) {
		this.ownerName = recieverName;
	}

}
