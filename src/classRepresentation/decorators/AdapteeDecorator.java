package classRepresentation.decorators;

public class AdapteeDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillColor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adaptee\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
}
