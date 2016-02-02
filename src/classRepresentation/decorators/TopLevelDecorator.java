package classRepresentation.decorators;

import interfaces.IClass;

public class TopLevelDecorator extends IClassDecorator {

	public TopLevelDecorator(IClass clazz) {
		this.decorates = clazz;
	}
	
	@Override
	public void decorate(IClassDecorator cls) {
		cls.setDecorates(this.decorates);
		this.decorates = cls;
	}

	@Override
	public void toGraphViz(StringBuilder str) {
		decorates.appendGraphVizHeader(str);
		decorates.appendGraphVizStereotype(str);
		decorates.appendGraphVizClassName(str);
		str.append("|");
		decorates.appendGraphVizFields(str);
		str.append("|");
		decorates.appendGraphVizMethods(str);
		str.append("|");
		str.append("}\"\n\t\t");
		decorates.appendGraphVizColor(str);
		decorates.appendGraphVizFooter(str);
	}
}
