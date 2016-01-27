package classRepresentation.decorators;

public class ComponentDecorator extends IClassDecorator {
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("fillColor=green");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		str.append("\\<\\<Componenet\\>\\>\\n");
		decorates.appendGraphVizStereotype(str);
	}
}
