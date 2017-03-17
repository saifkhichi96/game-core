package sfllhkhan95.game.kaptaansquest.model.objects;


public abstract class GameObject {

    int head;
    int tail;

    GameObject(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public abstract int moveTo();

}
