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

	private static String className = "SampleClassForReadingInATest";
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

	@Test
	public void testHas_mutateSomething() throws IOException {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("+mutateSomething(int[]):void"));
	}
	@Test
	public void testHas_finalStringMaker() throws IOException {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("-finalStringMaker(String[]):String"));
	}
	
	@Test
	public void testHas_identiry() throws IOException {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("<u>+identity(int):int<u/>"));
	}

	@Test
	public void testHas_useless() throws IOException  {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("<u>-useless():void<u/>"));
	}

	@Test
	public void testHas_aBool() throws IOException  {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("+aBool:boolean"));
	}
	@Test
	public void testHas_aChar() throws IOException  {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("-aChar:char"));
	}
	@Test
	public void testHasSOME_WORD()  throws IOException {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("<u>-SOME_WORD:String<u/>"));
	}
	@Test
	public void testHasSOME_CONSTANT()  throws IOException  {
		DesignParser.main(new String[]{className});
		String result = out.toString();
		assertTrue(result.contains("<u>-SOME_CONSTANT:int<u/>"));
	}
}
