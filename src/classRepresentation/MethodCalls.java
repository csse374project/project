package classRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MethodCalls {
	
	private List<SequenceMethodCall> calls;
	
	public MethodCalls(){
		this.calls = new ArrayList<SequenceMethodCall>();
	}
	
	public void addMethodCall(SequenceMethodCall arg0){
		this.calls.add(arg0);
	}
	
	public String toSDEdit(){
		StringBuilder output = new StringBuilder();
			
		return output.toString();
	}

	
}
