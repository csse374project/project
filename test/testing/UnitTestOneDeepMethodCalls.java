package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.SequenceMethodCall;
import sequenceDiagram.SequenceParser;
import sequenceDiagram.SingleMethodVisitor;

public class UnitTestOneDeepMethodCalls {
//	methodName, invokerName, ownerName, returnType, parameters, isInit
	
	private static final String className = "java.lang.String";
	private static final String methodName = "java.lang.String.replace(char,char)";
	private SequenceMethodCall toTest;
	private Iterator<SequenceMethodCall> iterator;
	
	@Before
	public void setup() throws IOException {
		ClassReader reader = new ClassReader(className);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, methodName, className);
		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		iterator = SequenceParser.calls.getIterator();
		toTest = iterator.next();
	}
	
	@Test
	public void hasOnlyOneMethod() {
		assertFalse(iterator.hasNext());
	}

}
