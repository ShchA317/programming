package lab2;

import ru.ifmo.se.pokemon.*;

public class Wigglytuff extends Jigglypuff {
    public Wigglytuff(String name, int lvl){
        super(name, lvl);
        setStats(140, 70, 45, 85, 50, 45);
        setType(Type.NORMAL, Type.FAIRY);
        setMove(new WorkUp(), new WildCharge(), new  WakeUpSlap(), new Flamethrower());
    }
}

class Flamethrower extends SpecialMove{
    public Flamethrower(){
        super(Type.FIRE, 90, 1.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        if(Math.random() < 0.1){
            Effect.burn(p);
        }
    }
}
