package testingData;

public class SampleSingletonClass {

	private static SampleSingletonClass instance = new SampleSingletonClass();
	public static SampleClassForInitializing useless = new SampleClassForInitializing();
	
	private SampleSingletonClass() {
		
	}
	
	public static SampleSingletonClass getInstance() {
		return instance;
	}
	
}
