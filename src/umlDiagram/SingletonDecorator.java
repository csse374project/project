package umlDiagram;

import classRepresentation.IClassDecorator;

public class SingletonDecorator extends IClassDecorator {

	@Override
	public void toGraphViz(StringBuilder string) {
		decorates.setColor("blue");
		decorates.addStereotype("Singleton");
		decorates.toGraphViz(string);
	}

}
