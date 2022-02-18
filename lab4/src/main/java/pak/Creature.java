package pak;

public abstract class Creature implements shielded {
	private String name;
	
	public String getName() {
		return name;
	}
	
	public Creature(String name) {
		this.name = name;
	}
	
	public void push(Creature c) {
		System.out.println(this.toString() + " push " +c.toString());
	}
	
	@Override
	public String toString(){
		return this.getName();
	}
}
