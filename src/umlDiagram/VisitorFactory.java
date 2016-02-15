package umlDiagram;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.designPaterns.AdapterClassVisitor;
import classRepresentation.designPaterns.CompositeVisitor;

public class VisitorFactory {

	public static ClassVisitor generateVisitors(List<String> patternDetectors, IClassDecorator topLevelDecorator){
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, topLevelDecorator);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, topLevelDecorator);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, topLevelDecorator);
		ClassVisitor finalVisitor = new MethodDeclarationVisitor(Opcodes.ASM5, methodVisitor,
				topLevelDecorator);

		for (String phase : patternDetectors){
			if (phase.equals("Singleton")){				
				finalVisitor = new SingletonFieldVisitor(Opcodes.ASM5, finalVisitor, topLevelDecorator);
			}
			else if (phase.equals("Adapter")){
				finalVisitor = new AdapterClassVisitor(Opcodes.ASM5, finalVisitor, topLevelDecorator);				
			}
			else if (phase.equals("Composite")){
				finalVisitor = new CompositeVisitor(Opcodes.ASM5, finalVisitor, topLevelDecorator);				
			}
		}
		return finalVisitor;
	}
	
}
