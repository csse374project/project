package umlDiagram;

import java.util.List;

import org.objectweb.asm.ClassVisitor;

public class ClassNameVisitor extends ClassVisitor {
	
	List<String> names;

	public ClassNameVisitor(int api, List<String> names) {
		super(api);
		this.names = names;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.names.add(name);
	}

}
