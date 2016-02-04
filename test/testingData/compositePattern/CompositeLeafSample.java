package testingData.compositePattern;

import testingData.SampleInterface01;

public class CompositeLeafSample implements SampleInterface01 {
	
	private boolean hatesYouAndWantsYouTooDie = true;
	public boolean actsFriendlyTowardYou = true;
	
	public CompositeLeafSample() {
		// do nothing
	}
	
	public boolean isPassiveAgressive() {
		return hatesYouAndWantsYouTooDie && actsFriendlyTowardYou;
	}
	
	public boolean hatesThomas(){
		return true;
	}
}
