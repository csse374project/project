package classRepresentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;

public class Classes {
	private Map<String, IClass> classes;

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

	public String toGraphViz() {
		StringBuilder string = new StringBuilder();

		Set<String> keys = classes.keySet();

		// Add the string to start the UML structure
		string.append("digraph UML {\n\n\tnode [\n\t\tshape = " + "\"record\"\n\t]\n\n");

		appendClass(string, keys);

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

	private void appendClass(StringBuilder string, Set<String> keys) {
		for (String key : keys) {
			IClass cls = classes.get(key);
			int lastFwdSlash = cls.getName().lastIndexOf('/');
			string.append("\t" + cls.getName().substring(lastFwdSlash + 1) + " [\n\t\tlabel = \"{");
			if (cls.getIsInterface())
				string.append("\\<\\<interface\\>\\>\\n");
			string.append(cls.getName().substring(lastFwdSlash + 1) + "|");

			appendFields(string, cls);

			string.append("|");

			appendMethods(string, cls, lastFwdSlash);

			string.append("}\"\n\t]\n\n");
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

	private void appendFields(StringBuilder string, IClass cls) {
		for (IField field : cls.getFields()) {
			string.append(field.getVisibility() + " " + field.getName() + ": " + field.getType() + "\\l");
		}
	}

	private void appendMethods(StringBuilder string, IClass cls, int lastFwdSlash) {
		for (IMethod method : cls.getMethods()) {
			String methodName = method.getName();
			if (methodName.equals("<init>") || methodName.equals("<clinit>")) {
				continue;
			}
			int lastPeriod = method.getReturnType().lastIndexOf('.');
			String methodReturnType = method.getReturnType();
			if (lastPeriod > -1) {
				methodReturnType = methodReturnType.substring(lastPeriod + 1);
			}
			string.append(method.getVisibility() + " " + methodReturnType + " " + methodName + "(");
			String params = method.getParameters().toString().substring(1,
					method.getParameters().toString().length() - 1);
			string.append(params + ")\\l");
		}
	}

}
