package testingData;

public class SampleClassMethodSequence {
	
	public void doLotsOfThings() {
		doThing();
		SampleClassForReadingInATest.identity(1);
	}
	
	public void doThing() {
		doAnotherThing();
		
	}
	
	public void doAnotherThing() {
		// do nothing
	}
}
