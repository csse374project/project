package testingData.compositePattern;

public class LeafChildSample extends CompositeLeafSample {
	private boolean dummyData;
	
	public LeafChildSample() {
		// do nothing
	}
	
	public boolean getDummy() {
		return dummyData;
	}
	
	public boolean getTrue(){
		return true;
	}
}
