package lab2;

import ru.ifmo.se.pokemon.*;

public class Pumpkaboo extends Pokemon {
    public Pumpkaboo(String name, int lvl){
        super(name, lvl);
        setStats(49, 66, 70, 44, 55, 51);
        setType(Type.GHOST, Type.GRASS);
        setMove(new ConfuseRay(), new Rest(), new Psychic());
    }
}

class ConfuseRay extends StatusMove {
    ConfuseRay(){
        super(Type.GHOST, 0, 1.0);
    }

    @Override

    protected void applyOppEffects(Pokemon p){
        p.confuse();
    }
}

class Rest extends StatusMove {
    Rest(){
        super(Type.PSYCHIC, 0, 1.0);
    }

    @Override

    protected void applySelfEffects(Pokemon p){
        Effect e = new Effect().turns(2).condition(Status.SLEEP);
        p.setCondition(e);
        p.setMod(Stat.HP, (int)(49 - p.getHP()));
    }
}

class Psychic extends SpecialMove {
    Psychic() {
        super(Type.PSYCHIC, 90, 1.0);
    }

    @Override

    protected void applyOppEffects(Pokemon p){
        if(Math.random() < 0.1) {
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }
}