package classRepresentation.decorators;

public class AdapterDecorator extends IClassDecorator {

	private String adaptee, target;
	
	public AdapterDecorator(String adaptee, String target) {
		this.adaptee = adaptee;
		this.target = target;
	}
	
	public String getAdaptee() {
		return adaptee;
	}
	
	public String getTarget() {
		return target;
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillColor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adapter\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
}
