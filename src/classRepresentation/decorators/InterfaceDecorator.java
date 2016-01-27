package classRepresentation.decorators;

public class InterfaceDecorator extends IClassDecorator {

	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Interface\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}


}
