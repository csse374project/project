package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UnitTestDecoratorDetector.class,
	UnitTestsDeclarationSample.class,
	UnitTestsDeclarationString.class,
	UnitTestsFieldVisitor.class,
	UnitTestsMethod.class,
	UnitTestSimpleMethodCalls.class,
	UnitTestsMethodVisitor.class,
	UnitTestsMethodCodeVisitor.class,
	UnitTestSingletonVisitors.class,
	UnitTestsToGraphViz.class
})
public class AllTests {

}
