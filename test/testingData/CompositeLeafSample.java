package testingData;

public class CompositeLeafSample implements SampleInterface01 {
	
	private boolean hatesYouAndWantsYouTooDie = true;
	public boolean actsFriendlyTowardYou = true;
	
	public CompositeLeafSample() {
		// do nothing
	}
	
	public boolean isPassiveAgressive() {
		return hatesYouAndWantsYouTooDie && actsFriendlyTowardYou;
	}
}
