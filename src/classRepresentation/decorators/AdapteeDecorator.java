package classRepresentation.decorators;

public class AdapteeDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=red\n");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adaptee\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
}
