package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UnitTestsDeclarationSample.class,
	UnitTestsDeclarationString.class,
	UnitTestsFieldVisitor.class,
	UnitTestsMethodVisitor.class
})
public class AllTests {

}
