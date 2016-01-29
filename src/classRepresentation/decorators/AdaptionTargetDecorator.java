package classRepresentation.decorators;

public class AdaptionTargetDecorator extends IClassDecorator {
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillcolor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Target\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
	
}
