package testingData.compositePattern;

import java.util.ArrayList;
import java.util.List;

public class AbstractCompositeSample extends AbstractCompositeComponent {
	
//	private AbstractCompositeComponent[] stuff;
	private ArrayList<AbstractCompositeComponent> stuff;
	
	public AbstractCompositeSample(){
		stuff = new ArrayList<AbstractCompositeComponent>();
//		stuff = new AbstractCompositeComponent[1]
	}
	
	private void dontDoThings(){
		for(int i = 0; i < 100; i++){
			i++;
		}
	}

	@Override
	public void doSomething() {
		// do nothing
	}
}
