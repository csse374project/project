package classRepresentation.decorators;

public class DecoratorDecorator extends IClassDecorator {

	private String component;
	public DecoratorDecorator(String component) {
		this.component = component;
	}
	
	@Override
	public void toGraphViz(StringBuilder string) {
//		decorates.setColor("green");
//		decorates.addStereotype("Decorator");
		decorates.toGraphViz(string);
	}
}
