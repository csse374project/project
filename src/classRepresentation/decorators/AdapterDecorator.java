package classRepresentation.decorators;

public class AdapterDecorator extends IClassDecorator {

	private String adaptee;
	
	public AdapterDecorator(String adaptee) {
		this.adaptee = adaptee;
	}
	
	public String getAdaptee() {
		return adaptee;
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
