package sfllhkhan95.game.ludo.model.objects;

public class Ladder extends GameObject {

    public Ladder(int foot, int top) {
        super(top, foot);
    }

    public int moveTo() {
        return head;
    }

}
