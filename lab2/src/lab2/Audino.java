package lab2;
import ru.ifmo.se.pokemon.*;

public class Audino extends Pokemon {
	public Audino(String name, int lvl ) {
		super(name, lvl);
		setStats(103, 60, 86, 60, 86, 50);
		setType(Type.NORMAL);
		setMove(new HyperVoice(), new DreamEater(), new Growl(), new BabyDollEyes());
	}
}

class HyperVoice extends SpecialMove {
	public HyperVoice(){
		super(Type.NORMAL, 90, 1.0);
	}
}

class DreamEater extends SpecialMove{
	public DreamEater(){
		super(Type.PSYCHIC, 100, 1.0);
	}

	@Override
	protected boolean checkAccuracy(Pokemon att, Pokemon def){
		if (def.getCondition() != Status.SLEEP){
			return false;
		}
		else return true;
	}

	protected void applySelfEffects(Pokemon p){
		p.setMod(Stat.HP, (int) ((103 - p.getHP())/2));
	}

}

class Growl extends StatusMove{
	public Growl(){
		super(Type.NORMAL, 0, 1.0);
	}

	@Override

	protected void applyOppEffects(Pokemon p){
		p.setMod(Stat.ATTACK, -1);
		p.setMod(Stat.SPECIAL_ATTACK, -1);
	}
}

class BabyDollEyes extends StatusMove{
	public BabyDollEyes(){
		super(Type.FAIRY, 0, 1.0);
	}

	@Override
	protected void applyOppEffects(Pokemon p){
		p.setMod(Stat.ATTACK, -1);
	}
}
