package testing;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import csse374project.ClassDeclarationVisitor;
import csse374project.DesignParser;

public class TestErrThang {

	private static String className = "java.lang.String";
	
	@Test
	public void testErrThang() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		try {
			DesignParser.main(new String[]{className});
		} catch (IOException e) {
			fail("An IO-exception was thrown.");
		}
		
		String result = out.toString();
		String expected = "more than just hello";
		assertEquals(expected, result);
	}

}
