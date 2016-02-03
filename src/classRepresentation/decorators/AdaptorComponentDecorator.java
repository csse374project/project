package classRepresentation.decorators;

public class AdaptorComponentDecorator extends IClassDecorator {
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=green\n");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		str.append("\\<\\<Component\\>\\>\\n");
		decorates.appendGraphVizStereotype(str);
	}
}
