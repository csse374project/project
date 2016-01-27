package classRepresentation.decorators;

public class DecoratorDecorator extends IClassDecorator {

	private String component;
	public DecoratorDecorator(String component) {
		this.component = component;
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillColor=green");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Decorator\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
}
