package classRepresentation;

import java.util.List;

public class SequenceMethodCall {

	private String methodName, invokerName, ownerName, returnType;
	private List<String> parameters;
	private boolean isInit;
	
	
	public String getName() {
		return methodName;
	}

	public void setName(String name) {
		this.methodName = name;
	}

	public String getInvoker() {
		return invokerName;
	}

	public String getOwner() {
		return ownerName;
	}

	public void setInvoker(String name) {
		this.invokerName = name;
	}

	public void setOwner(String name) {
		this.ownerName = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

}
