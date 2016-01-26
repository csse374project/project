package umlDiagram;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classRepresentation.decorators.IClassDecorator;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

//TODO: Rename class
public class MethodDeclarationVisitor extends ClassVisitor {
	
	private IClassDecorator currentClass;

	public MethodDeclarationVisitor(int arg0, ClassVisitor arg1, IClassDecorator currentClass) {
		super(arg0, arg1);
		this.currentClass = currentClass;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor codeVisitor = new MethodCodeVisitor(Opcodes.ASM5, toDecorate, currentClass);
		currentClass.addUsedClass(Type.getReturnType(desc).getClassName().replace('.', '/'));
		handleSignature(signature);
		return codeVisitor;
		
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
			currentClass.addUsedClass(name);
		}
	}

}
