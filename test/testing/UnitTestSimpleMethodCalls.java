package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.MethodCalls;
import classRepresentation.SequenceMethodCall;
import sequenceDiagram.SequenceParser;
import sequenceDiagram.SingleMethodVisitor;

public class UnitTestSimpleMethodCalls {
//	methodName, invokerName, ownerName, returnType, parameters, isInit
	
	private static final String className = "testingData.SampleClassMethodSequence";
	private static final String methodName = "testingData.SampleClassMethodSequence.doLotsOfThings()";
	private Iterator<SequenceMethodCall> iterator;
	
	@Before
	public void setup() throws IOException {
		ClassReader reader = new ClassReader(className);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, 0, 5, className,
				methodName, null);
		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		iterator = SequenceParser.calls.getIterator();
	}
	
	@After
	public void tearDown() {
		SequenceParser.calls = new MethodCalls();
	}
	
	@Test
	public void hasCorrectNumberOfMethods() {
		iterator.next(); // 1
		iterator.next(); // 2
		iterator.next(); // 3
		iterator.next(); // 4
		assertFalse(iterator.hasNext());
	}
//	methodName, invokerName, ownerName, returnType;

	@Test
	public void firstMethodHasCorrectName() {
		SequenceMethodCall call = iterator.next();
		assertEquals("doLotsOfThings", call.getName());
	}
	@Test
	public void firstMethodHasCorrectInvoker() {
		SequenceMethodCall call = iterator.next();
		assertEquals(null, call.getInvoker());
	}
	@Test
	public void firstMethodHasCorrectOwner() {
		SequenceMethodCall call = iterator.next();
		assertEquals("testingData.SampleClassMethodSequence", call.getOwner());
	}
	@Test
	public void firstMethodHasCorrectReturn() {
		SequenceMethodCall call = iterator.next();
		assertEquals("void", call.getReturnType());
	}
	//

	@Test
	public void innerMethodHasCorrectName() {
		iterator.next(); // 1
		iterator.next(); // 2
		iterator.next(); // 3
		SequenceMethodCall call = iterator.next(); // 4
		assertEquals("identity", call.getName());
	}
	@Test
	public void innerMethodHasCorrectInvoker() {
		iterator.next(); // 1
		iterator.next(); // 2
		iterator.next(); // 3
		SequenceMethodCall call = iterator.next();
		assertEquals("testingData.SampleClassMethodSequence", call.getInvoker());
	}
	@Test
	public void innerMethodHasCorrectOwner() {
		iterator.next(); // 1
		iterator.next(); // 2
		iterator.next(); // 3
		SequenceMethodCall call = iterator.next();
		assertEquals("testingData.SampleClassForReadingInATest", call.getOwner());
	}
	@Test
	public void innerMethodHasCorrectReturn() {
		iterator.next(); // 1
		iterator.next(); // 2
		iterator.next(); // 3
		SequenceMethodCall call = iterator.next();
		assertEquals("int", call.getReturnType());
	}
	
}
