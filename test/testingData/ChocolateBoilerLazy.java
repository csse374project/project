package testingData;

public class ChocolateBoilerLazy {
	private boolean empty;
	private boolean boiled;
	private volatile static ChocolateBoilerLazy uniqueInstance;
	
	private ChocolateBoilerLazy(){}
	
	public static ChocolateBoilerLazy getInstance(){
		if (uniqueInstance == null) {
			synchronized (ChocolateBoilerLazy.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new ChocolateBoilerLazy();
				}
			}
		}
		return uniqueInstance;
	}
	
	public void fill(){
		if (isEmpty()) {
			empty=false;
			boiled=false;
			// fill the boiler with a milk/chocolate mixture
		}
	}
	
	public void drain() {
		if (!isEmpty() && isBoiled()) {
			//drain the boiled milk and chocolate
			empty = true;
		}
	}
	
	public void boil(){
		if (!isEmpty() && !isBoiled()) {
			//bring the contents to a boil
			boiled = true;
		}
	}
	
	public boolean isEmpty(){
		return empty;
	}
	
	public boolean isBoiled(){
		return boiled;
	}
}
