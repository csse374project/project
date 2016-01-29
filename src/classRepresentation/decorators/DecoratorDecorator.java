package classRepresentation.decorators;

import interfaces.IClass;

public class DecoratorDecorator extends IClassDecorator {

	private String component;
	private IClass decorator;
	
	public DecoratorDecorator(String component) {
		this.component = component;
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=green");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Decorator\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
	@Override
	public void appendGraphVizFooter(StringBuilder string){
		decorates.appendGraphVizFooter(string);
		if(component != null) {
			string.append("\t");
			int lastSlash = decorates.getName().lastIndexOf('/');
			string.append(decorates.getName().substring(lastSlash + 1));
			string.append(" -> ");
			lastSlash = component.lastIndexOf('/');
			string.append(component.substring(lastSlash + 1));
			string.append(" [label = \"\\<\\<decorates\\>\\>\"]\n\n");
		}
	}
}
