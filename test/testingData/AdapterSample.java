package testingData;

public class AdapterSample implements AdapterTargetSample {

	private AdapteeSample adaptee;
	public AdapterSample(AdapteeSample adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void methodOfThings() {
		adaptee.doNothing();
	}

	@Override
	public void methodOfStuff() {
		adaptee.doNothing();
	}

	@Override
	public int testMePlease() {
		return adaptee.getNumber();
	}

}
