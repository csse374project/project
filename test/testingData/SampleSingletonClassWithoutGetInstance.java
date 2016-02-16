package testingData;

public class SampleSingletonClassWithoutGetInstance {

	private static SampleSingletonClassWithoutGetInstance instance = new SampleSingletonClassWithoutGetInstance();
	public static SampleClassForInitializing useless = new SampleClassForInitializing();
	
	private SampleSingletonClassWithoutGetInstance() {
		
	}
	
	public static SampleSingletonClassWithoutGetInstance getThingy() {
		return instance;
	}
	
}
