package classRepresentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class Classes {
	private Map<String, IClass> classes;
	private static final String DEFAULT_COLOR = "black";

	public Classes() {
		this.classes = new HashMap<String, IClass>();
	}

	public Map<String, IClass> getClasses() {
		return this.classes;
	}

	public void setClasses(Map<String, IClass> classes) {
		this.classes = classes;
	}

	public void addClass(IClass newClass) {
		if (!classes.containsKey(newClass.getName()))
			classes.put(newClass.getName(), newClass);
	}

	public String printGraphVizInput() {
		StringBuilder string = new StringBuilder();

		Set<String> keys = classes.keySet();

		// Add the string to start the UML structure
		string.append("digraph UML {\n\n\tnode [\n\t\tshape = ");
		string.append("\"record\"\n\t]\n\n");

		appendClasses(string, keys);

		string.append("\tedge [\n\t\tarrowhead = \"empty\"\n\t]\n\n");
		appendSuperClass(string, keys);

		string.append("\tedge [\n\t\tstyle = \"dashed\"\n\t]\n\n");
		appendInterfaces(string, keys);

		string.append("\tedge [\n\t\tarrowhead = \"normal\" style = \"solid\"\n\t]\n\n");
		appendAssociatedClasses(string, keys);

		string.append("\tedge [\n\t\tarrowhead = \"normal\" style = \"dashed\"\n\t]\n\n");
		appendUsedClasses(string, keys);

		string.append("}");
		return string.toString();
	}
	
	private void appendClassHeader(StringBuilder str, IClass cls) {
		int lastFwdSlash = cls.getName().lastIndexOf('/');
		str.append("\t");
		str.append(cls.getName().substring(lastFwdSlash + 1));
		str.append(" [\n\t\tlabel = \"{");
	}
	private void appendClassFooter(StringBuilder str, IClass cls) {
		str.append("color=");
//		str.append(cls.getColor());
		str.append("\n\t]\n\n");
	}

	private void appendUsedClasses(StringBuilder string, Set<String> keys) {
		for (String key : keys) {
			IClass cls = classes.get(key);
			int lastFwdSlash = cls.getName().lastIndexOf('/');
			for (String usedClass : cls.getUsedClasses()) {
				int usedLastFwdSlash = usedClass.lastIndexOf('/');
				string.append("\t");
				string.append(cls.getName().substring(lastFwdSlash + 1));
				string.append(" -> ");
				string.append(usedClass.substring(usedLastFwdSlash + 1));
				string.append("\n\n");
			}
		}
	}

	private void appendAssociatedClasses(StringBuilder string, Set<String> keys) {
		for (String key : keys) {
			IClass cls = classes.get(key);
			int lastFwdSlash = cls.getName().lastIndexOf('/');
			for (String assocClass : cls.getAssociatedClasses()) {
				int assocLastFwdSlash = assocClass.lastIndexOf('/');
				string.append("\t");
				string.append(cls.getName().substring(lastFwdSlash + 1));
				string.append(" -> ");
				string.append(assocClass.substring(assocLastFwdSlash + 1));
				string.append("\n\n");
			}
		}
	}

	private void appendClasses(StringBuilder string, Set<String> keys) {
		for (String key : keys) {
			IClass current = classes.get(key);
//			appendClassHeader(string, current);
			current.toGraphViz(string);
//			appendClassFooter(string, current);
		}
	}

	private void appendInterfaces(StringBuilder string, Set<String> keys) {
		for (String key : keys) {
			IClass cls = classes.get(key);
			int lastFwdSlash = cls.getName().lastIndexOf('/');
			for (String intface : cls.getInterfaces()) {
				int intLastFwdSlash = intface.lastIndexOf('/');
				if (keys.contains(intface)) {
					string.append("\t" + cls.getName().substring(lastFwdSlash + 1) + " -> "
							+ intface.substring(intLastFwdSlash + 1) + "\n\n");
				}
			}
		}
	}

	private void appendSuperClass(StringBuilder string, Set<String> keys) {
		for (String key : keys) {
			IClass cls = classes.get(key);
			int lastFwdSlash = cls.getName().lastIndexOf('/');
			String superCls = cls.getSuperClass();
			int superLastFwdSlash = superCls.lastIndexOf('/');
			if (keys.contains(superCls)) {
				string.append("\t" + cls.getName().substring(lastFwdSlash + 1) + " -> "
						+ superCls.substring(superLastFwdSlash + 1) + "\n\n");
			}
		}
	}
}
