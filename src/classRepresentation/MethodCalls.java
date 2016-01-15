package classRepresentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MethodCalls {
	
	private List<SequenceMethodCall> calls;
	private Set<String> usedClasses;
	
	public MethodCalls(){
		this.calls = new ArrayList<SequenceMethodCall>();
		this.usedClasses = new HashSet<String>();
	}
	
	public void addMethodCall(SequenceMethodCall arg0){
		this.calls.add(arg0);
	}
	
	public Iterator<SequenceMethodCall> getIterator() {
		return calls.iterator();
	}
	public String toSDEdit(){
		StringBuilder output = new StringBuilder();
		
		addClasses(output);
		
		output.append("\n");
		
		addMethodCalls(output);
		
		return output.toString();
	}
	
	private void addClasses(StringBuilder str) {
		for (SequenceMethodCall metCall : calls) {
			if (!usedClasses.contains(metCall.getOwner())) {
				str.append(metCall.getOwner() + ":" + metCall.getOwner() + "\n");
				usedClasses.add(metCall.getOwner());
			}
		}
	}
	
	private void addMethodCalls(StringBuilder str) {
		for (SequenceMethodCall metCall : calls) {
			if (metCall.getInvoker() == null) {
				continue;
			}
			str.append(metCall.getInvoker());
			str.append(":");
			str.append(metCall.getOwner());
			str.append(".");
			if (metCall.isInit()) {
				str.append("<init>");
			}
			else {
				str.append(metCall.getName());
				str.append('(');
				str.append(removeParamDots(metCall.getParameters()));
				str.append(')');
				str.append(":");
				str.append(removeReturnDots(metCall.getReturnType()));
			}
			str.append("\n");
		}
	}
	
	private String removeReturnDots(String ret){
		int lastDot = ret.lastIndexOf('.');
		return ret.substring(lastDot+1);
	}
	
	private String removeParamDots(List<String> params){
		StringBuilder newParams = new StringBuilder();
		for(String param : params){
			int lastDot = param.lastIndexOf('.');
			newParams.append(param.substring(lastDot+1));
			newParams.append(" ");
		}
		return newParams.toString();
	}

	
}
