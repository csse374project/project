package classRepresentation.decorators;

public class CompositeDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		str.append("\\<\\<Composite\\>\\>\\n");
		decorates.appendGraphVizStereotype(str);
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=yellow\n");
	}
	
}
