package classRepresentation.decorators;

public class CompositeLeafDecorator extends IClassDecorator {
	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		str.append("\\<\\<Leaf\\>\\>\\n");
		decorates.appendGraphVizStereotype(str);
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=yellow\n");
	}	
}
