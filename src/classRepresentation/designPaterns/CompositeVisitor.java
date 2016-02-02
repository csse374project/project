package classRepresentation.designPaterns;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import classRepresentation.decorators.IClassDecorator;

public class CompositeVisitor extends ClassVisitor {
	
	private static List<String> collections;

	private IClassDecorator currentClass;
	public CompositeVisitor(int opCode, ClassVisitor toDecorate, IClassDecorator currentClass) {
		super(opCode, toDecorate);
		this.currentClass = currentClass;
		
		collections = new ArrayList<String>();
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
		type = type.substring(lastDot);
		System.out.println(type);
		if(collections.contains(type)){
			System.out.println("Types!");
		}
		
		handleSignature(signature);
		
		return toDecorate;
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
			currentClass.addAssociatedClass(name);
		}
	}
	
}
