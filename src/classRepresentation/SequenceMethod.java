package classRepresentation;

import java.util.List;

import interfaces.IMethod;
	

public class SequenceMethod {

	private String methodName, invokerName, ownerName;
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

	public void setInvoker(String ownerName) {
		this.invokerName = ownerName;
	}

	public void setOwner(String recieverName) {
		this.ownerName = recieverName;
	}

}
