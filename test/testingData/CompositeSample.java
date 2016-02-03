package testingData;

import java.util.ArrayList;
import java.util.List;

public class CompositeSample implements SampleInterface01 {
	
	private List<SampleInterface01> stuff;
	
	public CompositeSample(){
		stuff = new ArrayList<SampleInterface01>();
	}
	
	private void dontDoThings(){
		for(int i = 0; i < 100; i++){
			i++;
		}
	}
}
