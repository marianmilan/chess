package src.main.java;

public abstract class figure {

    private boolean white = false;
    private boolean dead = false;

    public figure(boolean white){
        this.white = white;
    }

    public void killed(){
        this.dead = true;
    }

    public boolean isWhite(){
        return this.white;
    }
}
