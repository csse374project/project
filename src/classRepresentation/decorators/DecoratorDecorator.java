package classRepresentation.decorators;

public class DecoratorDecorator extends IClassDecorator {

	public DecoratorDecorator() {
		
	}
	
	@Override
	public void toGraphViz(StringBuilder string) {
		decorates.setColor("green");
		decorates.addStereotype("Decorator");
		decorates.toGraphViz(string);
	}

}
