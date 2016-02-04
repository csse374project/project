package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UnitTestAdapterDecorator.class,
	UnitTestCompositeDetector.class,
	UnitTestCompositeJavaSwing.class,
	UnitTestDecoratorDetector.class,
	UnitTestDeclarationSample.class,
	UnitTestDeclarationString.class,
	UnitTestFieldVisitor.class,
	UnitTestMethod.class,
	UnitTestSimpleMethodCalls.class,
	UnitTestMethodVisitor.class,
	UnitTestMethodCodeVisitor.class,
	UnitTestSimpleCompositeDetector.class,
	UnitTestSingletonVisitors.class,
	UnitTestsToGraphViz.class
})
public class AllTests {

}
