package classRepresentation.decorators;

public class AdapterDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillColor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adapter\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
}
