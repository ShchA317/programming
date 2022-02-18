package pak;

public final class TV extends Human {
	public TV(String name) {
		super(name);
	}
	
	public void appeared() {
		System.out.print(this.toString() + " appear on screen ");
	}
	
	public void disappeared() {
		System.out.println(this.toString() + " disappeared from the screen ");
	}
}
