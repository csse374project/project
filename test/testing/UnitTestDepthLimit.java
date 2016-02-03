package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.After;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classRepresentation.MethodCalls;
import classRepresentation.SequenceMethodCall;
import sequenceDiagram.SequenceParser;
import sequenceDiagram.SingleMethodVisitor;

public class UnitTestDepthLimit {

	private static final String className = "testingData.SampleSequenceDepthClass";
	private static final String methodName = "testingData.SampleSequenceDepthClass.doSomethingDeep1()";
	private static final String constructorName = "testingData.SampleSequenceDepthClass.createSomething()";
	private Iterator<SequenceMethodCall> iterator;
	
	private static final int STARTING_DEPTH = 1;

	@After
	public void tearDown() {
		SequenceParser.calls = new MethodCalls();
	}	
	@Test
	public void testMethodDepthStopsAtCorrectLevel5() throws IOException {
		int depthLimit = 5;
		ClassReader reader = new ClassReader(className);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, STARTING_DEPTH,
				depthLimit, className, methodName, null);
		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		iterator = SequenceParser.calls.getIterator();
		for (int i = 0; i < depthLimit; i++) {
			iterator.next();
		}
		assertTrue(iterator.hasNext());
		iterator.next();
		assertFalse(iterator.hasNext());
	}
	@Test
	public void testMethodDepthStopsAtCorrectLevel7() throws IOException {
		int depthLimit = 7;
		ClassReader reader = new ClassReader(className);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, STARTING_DEPTH,
				depthLimit, className, methodName, null);
		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		iterator = SequenceParser.calls.getIterator();
		for (int i = 0; i < depthLimit; i++) {
			iterator.next();
		}
		assertTrue(iterator.hasNext());
		iterator.next();
		assertFalse(iterator.hasNext());
	}
	@Test
	public void testMethodDepthStopsAtCorrectLevel3() throws IOException {
		int depthLimit = 3;
		ClassReader reader = new ClassReader(className);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, STARTING_DEPTH,
				depthLimit, className, methodName, null);
		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		iterator = SequenceParser.calls.getIterator();
		for (int i = 0; i < depthLimit; i++) {
			iterator.next();
		}
		assertTrue(iterator.hasNext());
		iterator.next();
		assertFalse(iterator.hasNext());
	}
	@Test
	public void testConstructorDepthStopsAtCorrectLevel5() throws IOException {
		int depthLimit = 6;
		ClassReader reader = new ClassReader(className);
		ClassVisitor singleMethodVisitor = new SingleMethodVisitor(Opcodes.ASM5, STARTING_DEPTH,
				depthLimit, className, constructorName, null);
		reader.accept(singleMethodVisitor, ClassReader.EXPAND_FRAMES);
		iterator = SequenceParser.calls.getIterator();
		for (int i = 0; i < depthLimit; i++) {
			iterator.next();
		}
		assertTrue(iterator.hasNext());
		iterator.next();
		assertFalse(iterator.hasNext());
	}

}
