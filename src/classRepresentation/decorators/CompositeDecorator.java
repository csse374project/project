package classRepresentation.decorators;

public class CompositeDecorator extends IClassDecorator {

	private String component;
	
	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}

	public CompositeDecorator(String comp){
		this.component = comp;
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder str) {
		str.append("\\<\\<Composite\\>\\>\\n");
		decorates.appendGraphVizStereotype(str);
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\n\t\tfillcolor=yellow\n");
	}	
}
