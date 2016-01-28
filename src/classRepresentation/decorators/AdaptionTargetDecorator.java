package classRepresentation.decorators;

public class AdaptionTargetDecorator extends IClassDecorator {
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillColor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Target\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
	
}