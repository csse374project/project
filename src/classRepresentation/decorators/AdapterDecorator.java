package classRepresentation.decorators;

import umlDiagram.UMLParser;

public class AdapterDecorator extends IClassDecorator {

	private String adaptee, target;
	
	public AdapterDecorator(String adaptee, String target) {
		this.adaptee = adaptee;
		this.target = target;
	}
	
	public String getAdaptee() {
		return adaptee.replace('.', '/');
	}
	
	public String getTarget() {
		return target.replace('.', '/');
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=red\n");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adapter\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	@Override
	public void appendGraphVizFooter(StringBuilder string) {
		decorates.appendGraphVizFooter(string);
		int lastSlash = adaptee.lastIndexOf('.');
		String adapteeNode = adaptee.substring(lastSlash+1);
		lastSlash = decorates.getName().lastIndexOf('/');
		String adapterNode = decorates.getName().substring(lastSlash+1);
		lastSlash = getTarget().lastIndexOf('/');
		String targetNode = getTarget().substring(lastSlash+1);
		
		string.append("\t");
		string.append(adapterNode);
		string.append(" -> ");
		string.append(adapteeNode);
		string.append(" [label = \"\\<\\<adapts\\>\\>\"]\n\n");
		
		if(!UMLParser.classIsUsed(getTarget())){
		
			string.append("\tedge [");
			string.append("\n");
			string.append("\t\tarrowhead = \"empty\" style = \"dashed\"");
			string.append("\n\t]\n");
			
			string.append("\t");
			string.append(adapterNode);
			string.append(" -> ");
			string.append(targetNode);
		}
	}
	
}
