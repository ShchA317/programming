package pak;

public class Human extends Creature {
	public Human(String name) {
		super(name);
	}
	
	public void smile() {
		System.out.println(this.toString() + " smiles");
	}
	
	public void smile(Human h) {
		System.out.println(this.toString() + " smiles at " + h.toString());
	}
	
	@Override
	public void appeared() {
	}
	
	@Override
	public void disappeared() {		
	}	
}
