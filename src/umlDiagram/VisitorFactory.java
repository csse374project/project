package umlDiagram;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.decorators.IClassDecorator;
import classRepresentation.designPatterns.AdapterClassVisitor;
import classRepresentation.designPatterns.CompositeVisitor;
import classRepresentation.designPatterns.SingletonFieldVisitor;
import gui.DesignPatternInstance;

public class VisitorFactory {

	/**
	 * Generates the required visitors for the desired class. Will create a
	 * visitor of each type specified in patternDetectors.
	 * 
	 * @param patternDetectors
	 *            list of the patterns to be detected
	 * @param topLevelDecorator
	 *            class to be visited
	 * @return The decorated visitor
	 */
	public static ClassVisitor generateVisitors(List<String> patternDetectors, IClassDecorator topLevelDecorator,
			Map<String, String[]> phaseAttributes, List<DesignPatternInstance> designPatternInstances) {
		// Base visitors that are always required.
		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, topLevelDecorator);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, topLevelDecorator);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, topLevelDecorator);
		ClassVisitor finalVisitor = new MethodDeclarationVisitor(Opcodes.ASM5, methodVisitor, topLevelDecorator);

		// additional visitors for optional pattern detection.
		for (String phase : patternDetectors) {
			if (phase.equals("Singleton")) {
				finalVisitor = new SingletonFieldVisitor(Opcodes.ASM5, finalVisitor, topLevelDecorator,
						phaseAttributes.get("Singleton"), designPatternInstances);
			} else if (phase.equals("Adapter")) {
				finalVisitor = new AdapterClassVisitor(Opcodes.ASM5, finalVisitor, topLevelDecorator);
			} else if (phase.equals("Composite")) {
				finalVisitor = new CompositeVisitor(Opcodes.ASM5, finalVisitor, topLevelDecorator);
			}
		}
		return finalVisitor;
	}
}
