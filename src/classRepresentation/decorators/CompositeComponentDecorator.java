package classRepresentation.decorators;

public class CompositeComponentDecorator extends IClassDecorator {
	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		str.append("\\<\\<Composite Component\\>\\>\\n");
		decorates.appendGraphVizStereotype(str);
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=yellow\n");
	}	
}
