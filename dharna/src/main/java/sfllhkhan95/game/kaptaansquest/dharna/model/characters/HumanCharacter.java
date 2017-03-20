package sfllhkhan95.game.kaptaansquest.dharna.model.characters;

import android.app.Activity;
import android.widget.TextView;


public class HumanCharacter extends GameCharacter {

    private int score = 0;

    public HumanCharacter(Activity context, int characterView, int scoreCard, int label) {
        super(context, characterView, scoreCard, label);
        ((TextView) context.findViewById(label)).setText("Human");
        score = 0;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public void reset() {
        this.score = 0;
        super.reset();
    }
}

