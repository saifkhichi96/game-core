package sfllhkhan95.game.ludo.model.objects;


public class Snake extends GameObject {

    public Snake(int head, int tail) {
        super(head, tail);
    }

    public int moveTo() {
        return tail;
    }
}
