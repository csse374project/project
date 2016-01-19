package umlDiagram;

import org.objectweb.asm.Opcodes;

import com.sun.org.apache.bcel.internal.generic.Type;

import interfaces.IClass;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;


public class SingletonVisitor extends ClassVisitor {

	private IClass currentClass;
	
	public SingletonVisitor(int opCode, ClassVisitor toDecorate, IClass currentClass) {
		super(opCode, toDecorate);
		this.currentClass = currentClass;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		
		if ((access & Opcodes.ACC_STATIC) != 0) {
			Type type = Type.getType(desc);
			if (currentClass.getName().equals(type.toString())) {
				
			}
		}
		return toDecorate;
	}
}
