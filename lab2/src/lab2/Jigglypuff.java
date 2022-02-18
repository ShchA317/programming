package lab2;

import ru.ifmo.se.pokemon.*;

public class Jigglypuff extends Igglybuff {
    public Jigglypuff(String name, int lvl){
        super(name, lvl);
        setStats(115, 45, 20, 45, 25, 20);
        setType(Type.NORMAL, Type.FAIRY);
        setMove(new WorkUp(), new WildCharge(), new  WakeUpSlap());
    }
}

class WakeUpSlap extends PhysicalMove {
    public WakeUpSlap(){
        super(Type.FIGHTING, 70, 1.0);
    }

    @Override

    protected void applyOppEffects(Pokemon p){
        if (p.getCondition() == Status.SLEEP){
            applyOppDamage(p, 70 * 2);
            Effect e = new Effect().condition(Status.NORMAL);
            p.setCondition(e);
        }
    }
}
