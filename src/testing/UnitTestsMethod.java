package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import classRepresentation.Method;

public class UnitTestsMethod {

	@Test
	public void nullMethodEqualsSelf() {
		Method method = new Method();
		assertEquals(method, method);
	}
	
	@Test
	public void doesNotEqualWithDifferentName() {
		Method method1 = new Method();
		method1.setName("one");
		Method method2 = new Method();
		method2.setName("two");
		assertNotEquals(method1, method2);
	}

}
