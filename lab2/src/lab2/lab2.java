package lab2;

import ru.ifmo.se.pokemon.*;

public class lab2 {
	public static void main(String[] args) {
		Battle b = new Battle();
		Audino p1 = new Audino("Au",10);
		Pumpkaboo p2 = new Pumpkaboo("Pu", 10);
		Gourgeist p3 = new Gourgeist("Go", 10);
		Igglybuff p4 = new Igglybuff("Ig", 10);
		Jigglypuff p5 = new Jigglypuff("Ji", 11);
		Wigglytuff p6 = new Wigglytuff("Wi", 9);

		b.addAlly(p1);
        b.addAlly(p2);
		b.addAlly(p3);
		b.addFoe(p4);
		b.addFoe(p5);
		b.addFoe(p6);

		b.go();
	}

}
