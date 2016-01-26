package classRepresentation;

public class InterfaceDecorator extends IClassDecorator {

	@Override
	public void toGraphViz(StringBuilder string) {
		decorates.addStereotype("Interface");
		decorates.toGraphViz(string);
	}

}
