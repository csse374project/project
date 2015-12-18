package testing;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import org.junit.Test;


import csse374project.ClassDeclarationVisitor;

public class UnitTestsDeclarationVisitor {

	private static String className = "java.lang.String";
	
	
	@Test
	public void test() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		
//		ClassVisitor vis = new ClassDeclarationVisitor(Opcodes.ASM5);
//		ClassReader reader = null;
//		try {
//			reader = new ClassReader(className);
//		} catch (IOException e) {
//			fail("IOException thrown by ClassReader");
//		}
//		reader.accept(vis, ClassReader.EXPAND_FRAMES);
		
		
		String result = out.toString();
		String expected = "more than just hello";
		assertEquals(expected, result);
		
	}

}
