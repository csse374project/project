package testing;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class SampleClassForReadingInATest extends SampleSuperClass implements SampleInterface01, SampleInterface02 {

	private static final int SOME_CONSTANT = 1;
	public static final String SOME_WORD = "hello";
	
	public SampleClassForInitializingTwo sample = new SampleClassForInitializingTwo();
	private char aChar;
	public boolean aBool;
	
	public SampleClassForReadingInATest() {} 
	
	private static void useless() {}
	public static int identity(int x) {return x;}
	private final String finalStringMaker(String[] strings) {return "unimplemented";}
	public void mutateSomething(int[] array) {}
	public void initializeClass() {new SampleClassForInitializing();}
	public void initializeList() {ArrayList<String> what = new ArrayList<String>();}
	public void initializeArray() {String[] what = new String[1];}
	public SampleClassForInitializingThree whatever() {return null;}
	public void something(SampleClassForInitializingFour some) {}
	
}
