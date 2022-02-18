package lab2;

import ru.ifmo.se.pokemon.*;

public class Gourgeist extends Pumpkaboo {
    public Gourgeist(String name, int lvl){
        super(name, lvl);
        setStats(65,90,122,58,75,84);
        setType(Type.GHOST, Type.GRASS);
        setMove(new ConfuseRay(), new Rest(), new Psychic(), new FocusBlast());
    }
}

class FocusBlast extends SpecialMove{
    public FocusBlast(){
        super(Type.FIGHTING, 120, 0.7);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() < 0.1){
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }
}
