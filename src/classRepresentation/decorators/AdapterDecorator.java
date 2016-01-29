package classRepresentation.decorators;

public class AdapterDecorator extends IClassDecorator {

	private String adaptee, target;
	
	public AdapterDecorator(String adaptee, String target) {
		this.adaptee = adaptee;
		this.target = target;
	}
	
	public String getAdaptee() {
		return adaptee.replace('.', '/');
	}
	
	public String getTarget() {
		return target.replace('.', '/');
	}
	
	@Override
	public void appendGraphVizColor(StringBuilder str) {
		str.append("style=filled\\nfillcolor=red");
	}
	
	@Override
	public void appendGraphVizStereotype(StringBuilder string) {
		string.append("\\<\\<Adapter\\>\\>\\n");
		decorates.appendGraphVizStereotype(string);
	}
	
}
