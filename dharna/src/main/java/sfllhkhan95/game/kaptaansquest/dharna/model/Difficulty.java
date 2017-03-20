package sfllhkhan95.game.kaptaansquest.dharna.model;


public class Difficulty {
    private static int count = 0;

    private final int level;
    private final int winThreshold;

    public Difficulty(int winThreshold) {
        this.level = ++count;
        this.winThreshold = winThreshold;
    }

    public int getWinThreshold() {
        return winThreshold;
    }

    public int getLevel() {
        return level;
    }
}
