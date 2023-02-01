package de.thore_dev.cookieclicker;
abstract class Task {
    protected GameState state;
    protected String name;
    protected int progress;
    protected int goal;
    protected int boni;

    public boolean isCompleted(){
        if(progress >= goal){
            return true;
        } else {
            return false;
        }
    }
    public String getName(){
        return name;
    }
    abstract public void update();
    abstract public void finish();
}

class Task_Easy_Click extends Task{
    public Task_Easy_Click(GameState s){
        goal= s.getCcount() + 3*60* s.getClickspersecond()+500;
        name = "Besitze " + goal + " Kekse";
        progress = s.getCcount();
        boni = 100 + s.getMultiplier() * s.getClickspersecond() * 120;
        state = s;
    }

    @Override
    public void update() {
        progress = state.getCcount();
    }

    @Override
    public void finish() {
        state.incCcount(boni);
    }
}

