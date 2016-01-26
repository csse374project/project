package classRepresentation;

public class TopLevelDecorator extends IClassDecorator {

	public TopLevelDecorator() {
		// do nothing
	}
	
	@Override
	public void decorate(IClassDecorator cls) {
		cls.setDecorates(this.decorates);
		this.decorates = cls;
	}

	@Override
	public void toGraphViz(StringBuilder string) {
		decorates.toGraphViz(string);
	}
}
