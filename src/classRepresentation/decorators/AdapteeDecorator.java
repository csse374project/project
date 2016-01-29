package classRepresentation.decorators;

public class AdapteeDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillcolor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adaptee\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
}
