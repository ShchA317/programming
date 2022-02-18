package lab2;

import ru.ifmo.se.pokemon.*;

public class Igglybuff extends Pokemon{
    public Igglybuff(String name, int lvl){
        super(name, lvl);
        setStats(90,30,15, 40,20,15);
        setType(Type.NORMAL, Type.FAIRY);
        setMove(new WorkUp(), new WildCharge());

    }
}

class WorkUp extends StatusMove {
    public WorkUp() {
        super(Type.NORMAL, 0, 1.0);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return true;
    }

    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, 1);
        p.setMod(Stat.SPECIAL_ATTACK, 1);
    }
}

class WildCharge extends PhysicalMove {
    public WildCharge() {
        super(Type.NORMAL, 90, 1.0);
    }

    @Override
    protected void applySelfDamage(Pokemon att, double damage) {
        att.setMod(Stat.HP, -(int) (0.25 * damage));
    }
}
