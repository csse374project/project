package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UnitTestAdapterDecorator.class,
	UnitTestCompositeDetector.class,
	UnitTestCompositeJavaSwing.class,
	UnitTestDeclarationSample.class,
	UnitTestDeclarationString.class,
	UnitTestDecoratorDetector.class,
//	UnitTestDepthLimit.class,
	UnitTestFieldVisitor.class,
	UnitTestMethod.class,
	UnitTestMethodCodeVisitor.class,
	UnitTestMethodVisitor.class,
	UnitTestSimpleCompositeDetector.class,
//	UnitTestSimpleMethodCalls.class,
//	UnitTestSingletonVisitors.class,
	UnitTestsToGraphViz.class
})
public class AllTests {

}
