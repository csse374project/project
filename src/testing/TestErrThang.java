package testing;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import csse374project.ClassDeclarationVisitor;
import csse374project.DesignParser;

public class TestErrThang {

	private static String className = "java.lang.String";
	private PrintStream stdout;
	private ByteArrayOutputStream out;
	
	@Before
	public void setup() {
		stdout = System.out;
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void tearDown() {
		System.setOut(stdout);
	}

//	private char aChar;
//	public boolean aBool;
//	public SampleClassForReadingInATest() {} 
//	private static void useless() {}
//	public static int identity(int x) {return x;}
//	private final String finalStringMaker(String[] strings) {return "unimplemented";}
//	public void mutateSomething(int[] array) {}
	@Test
	public void testHasSOME_WORD() {
		try {
			DesignParser.main(new String[]{className});
		} catch (IOException e) {
			fail("An IO-exception was thrown.");
		}
		
		String result = out.toString();
		String expected = "more than just hello";
		assertTrue(result.contains("<u>-SOME_WORD:String<u/>"));
		assertEquals(expected, result);
	}
	@Test
	public void testHasSOME_CONSTANT() {
		try {
			DesignParser.main(new String[]{className});
		} catch (IOException e) {
			fail("An IO-exception was thrown.");
		}
		String result = out.toString();
		String expected = "more than just hello";
		assertTrue(result.contains("<u>-SOME_CONSTANT:int<u/>"));
		assertEquals(expected, result);
	}
}
