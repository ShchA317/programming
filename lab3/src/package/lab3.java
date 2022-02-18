package package;

public class lab3 {

	public static void main(String[] args) {
		Carlson carlson = new Carlson("Carlson");
		TV announcer = new TV("Announcer");
		Human baby = new Human("Baby");
		
		announcer.smile();
		carlson.smile(announcer);
		
		carlson.push(baby);
		announcer.appeared();
	}
}
