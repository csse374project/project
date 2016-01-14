package sequenceDiagram;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import interfaces.IClass;

public class SequenceMethodDeclarationVisitor extends ClassVisitor {

	private IClass currentClass;
	
	public SequenceMethodDeclarationVisitor(int opCode, ClassVisitor toDecorate, IClass currentClass) {
		super(opCode, toDecorate);
		this.currentClass = currentClass;
	}
	
	public SequenceMethodDeclarationVisitor(int asm5, IClass currentClass) {
		super(asm5);
		this.currentClass = currentClass;
		
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions); // ???
		MethodVisitor codeVisitor = new SequenceMethodCodeVisitor(Opcodes.ASM5, toDecorate, currentClass);
		
//		currentClass.add
		
		return codeVisitor;
	}

}
