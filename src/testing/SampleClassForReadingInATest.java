package testing;

@SuppressWarnings("unused")
public class SampleClassForReadingInATest {

	private static final int SOME_CONSTANT = 1;
	public static final String SOME_WORD = "hello";

	private char aChar;
	public boolean aBool;
	
	public SampleClassForReadingInATest() {} 
	
	private static void useless() {}
	public static int identity(int x) {return x;}
	private final String finalStringMaker(String[] strings) {return "unimplemented";}
	public void mutateSomething(int[] array) {}
	
}
