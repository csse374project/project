package classRepresentation.decorators;

public class SingletonDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Singleton\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}

	@Override
	public void appendGraphVizColor(StringBuilder string) {
		string.append("color=blue");
		decorates.appendGraphVizColor(string);
	}
	
}
