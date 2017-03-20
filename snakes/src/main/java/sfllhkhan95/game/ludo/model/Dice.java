package sfllhkhan95.game.ludo.model;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Random;

import sfllhkhan95.game.AnimationPlayer;
import sfllhkhan95.game.input.FlingListener;
import sfllhkhan95.game.ludo.dharna.R;


public class Dice {

    private final ImageView diceView;
    private int lastRoll;
    private GestureDetectorCompat mDetector;
    private AnimationDrawable frameAnimation;
    private Animation.AnimationListener onRollListener;

    public Dice(ImageView view) {
        diceView = view;
    }

    public int getLastRoll() {
        return lastRoll;
    }

    public int roll(int bias) {
        /*lastRoll =  4;
        return lastRoll;*/
        Random random = new Random();

        int chance = 1 + random.nextInt(100);
        if (chance < bias) {
            lastRoll = 3 + new Random().nextInt(3);
            return lastRoll;
        }

        lastRoll = 1 + new Random().nextInt(6);
        return lastRoll;
    }

    private void show() {
        diceView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        diceView.setVisibility(View.GONE);
    }

    public GestureDetectorCompat getGestureDetector() {
        return mDetector;
    }

    public void setFlingListener(Context context, FlingListener listener) {
        mDetector = new GestureDetectorCompat(context, listener);
        mDetector.setOnDoubleTapListener(listener);
    }

    public void startRolling() {
        frameAnimation.start();
    }

    public void updateFace() {
        frameAnimation.stop();
        switch (lastRoll) {
            case 1:
                diceView.setImageResource(R.drawable.die_1);
                break;
            case 2:
                diceView.setImageResource(R.drawable.die_2);
                break;
            case 3:
                diceView.setImageResource(R.drawable.die_3);
                break;
            case 4:
                diceView.setImageResource(R.drawable.die_4);
                break;
            case 5:
                diceView.setImageResource(R.drawable.die_5);
                break;
            case 6:
                diceView.setImageResource(R.drawable.die_6);
                break;
        }
    }

    public void rollDice(Point moveFrom, Point moveTo) {
        // Show dice
        show();

        diceView.setImageResource(R.drawable.roll_dice);
        frameAnimation = (AnimationDrawable) diceView.getDrawable();

        // Translate animation
        AnimationPlayer.translateAnimation(diceView, moveFrom, moveTo, 750, onRollListener);
    }

    public void setOnRollListener(Animation.AnimationListener onRollListener) {
        this.onRollListener = onRollListener;
    }
}