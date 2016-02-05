package testingData.compositePattern;

public class AbstractCompositeLeaf extends AbstractCompositeComponent {
	
	private boolean hatesYouAndWantsYouTooDie = true;
	public boolean actsFriendlyTowardYou = true;
	
	public AbstractCompositeLeaf() {
		// do nothing
	}
	
	public boolean isPassiveAgressive() {
		return hatesYouAndWantsYouTooDie && actsFriendlyTowardYou;
	}

	@Override
	public void doSomething() {
		// do nothing
	}
}
