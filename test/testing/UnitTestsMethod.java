package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import classRepresentation.UMLMethod;

public class UnitTestsMethod {

	@Test
	public void nullMethodEqualsSelf() {
		UMLMethod method = new UMLMethod();
		assertEquals(method, method);
	}
	
	@Test
	public void doesNotEqualWithDifferentName() {
		UMLMethod method1 = new UMLMethod();
		method1.setName("one");
		UMLMethod method2 = new UMLMethod();
		method2.setName("two");
		assertNotEquals(method1, method2);
	}

}
