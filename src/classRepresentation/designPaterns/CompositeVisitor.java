package classRepresentation.designPaterns;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import classRepresentation.UMLClass;
import classRepresentation.decorators.CompositeDecorator;
import classRepresentation.decorators.DecoratorDecorator;
import classRepresentation.decorators.IClassDecorator;
import interfaces.IClass;

public class CompositeVisitor extends ClassVisitor {
	
	private final static List<String> collections = new ArrayList<String>();;

	private IClassDecorator currentClass;
	public CompositeVisitor(int opCode, ClassVisitor toDecorate, IClassDecorator currentClass) {
		super(opCode, toDecorate);
		this.currentClass = currentClass;
	
		collections.add("List");
		collections.add("[]");
		collections.add("Map");
		collections.add("Queue");
		collections.add("Stack");
		collections.add("SortedMap");
		collections.add("Vector");
		collections.add("Collection");
		collections.add("TreeSet");
		collections.add("Set");
		collections.add("ArrayList");
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		
		String type = Type.getType(desc).getClassName();
		int lastDot = type.lastIndexOf('.');
		type = type.substring(lastDot+1);
		if(collections.contains(type)){
			if(!(isComposite(currentClass))) handleSignature(signature);
		}
		else if(type.contains("[]")){
			if(!(isComposite(currentClass))) handleSignature(signature);
		}
		
		return toDecorate;
	}
	
	private boolean isComposite(IClass clazz) {
		IClassDecorator cls = (IClassDecorator) clazz;
		Object current = cls.getDecorates();
		while (true) {
			if (current instanceof UMLClass) {
				return false;
			}
			cls = (IClassDecorator) current;
			if (cls == null) {
				return false;
			}
			if (cls instanceof CompositeDecorator) {
				return true;
			}
			current = cls.getDecorates();
		}
	}
	
	public void handleSignature(String signature) {
		if (signature == null)
			return;
		SignatureVisitor sigVis = new SigVisitor(Opcodes.ASM5);
		SignatureReader sigReader = new SignatureReader(signature);
		sigReader.accept(sigVis);

	}

	class SigVisitor extends SignatureVisitor {

		public SigVisitor(int opcode) {
			super(opcode);
		}

		@Override
		public void visitClassType(String name) {
			//if(currentClass.getInterfaces().contains(name)) currentClass.decorate(new CompositeDecorator(name));
			//else if(name.equals(currentClass.getSuperClass())) currentClass.decorate(new CompositeDecorator(name));
		}
	}
	
}
