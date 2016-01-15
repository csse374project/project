package testingData;

import org.junit.Before;

public class SampleSequenceDepthClass {

	public void doSomethingDeep1() {
		doSomethingDeep2();
	}
	private void doSomethingDeep2() {
		doSomethingDeep3();
	}
	private void doSomethingDeep3() {
		doSomethingDeep4();
	}
	private void doSomethingDeep4() {
		doSomethingDeep5();
	}
	private void doSomethingDeep5() {
		doSomethingDeep6();
	}
	private void doSomethingDeep6() {
		doSomethingDeep7();
	}
	private void doSomethingDeep7() {
		doSomethingDeep8();
	}
	private void doSomethingDeep8() {
		// do nothing
	}
	
	public void createSomething() {
		Creatable1 c = new Creatable1();
	}
	class Creatable1 {
		public Creatable1() {
			Creatable2 c = new Creatable2();
		}
	}
	class Creatable2 {
		public Creatable2() {
			Creatable3 c = new Creatable3();
		}
	}
	class Creatable3 {
		public Creatable3() {
			Creatable4 c = new Creatable4();
		}
	}
	class Creatable4 {
		public Creatable4() {
			Creatable5 c = new Creatable5();
		}
	}
	class Creatable5 {
		public Creatable5() {
			Creatable6 c = new Creatable6();
		}
	}
	class Creatable6 {
		public Creatable6() {
			Creatable7 c = new Creatable7();
		}
	}
	class Creatable7 {
		public Creatable7() {
			Creatable8 c = new Creatable8();
		}
	}
	class Creatable8 {
		public Creatable8() {
			// do nothing
		}
	}
}
