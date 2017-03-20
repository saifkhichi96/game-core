package sfllhkhan95.game.ludo.model.characters;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import sfllhkhan95.game.AnimationPlayer;
import sfllhkhan95.game.ludo.dharna.Board;


public class GameCharacter {

    public final TextView scoreCard;
    private final View characterView;
    private boolean bitten = false;
    private boolean climbedLadder = false;
    private int lastPosition;
    private int currentPosition;

    public GameCharacter(Activity context, int characterView, int scoreCard, int label) {
        this.characterView = context.findViewById(characterView);
        this.scoreCard = (TextView) context.findViewById(scoreCard);

        this.characterView.setVisibility(View.GONE);

        ((TextView) context.findViewById(label)).setText("Machine");

        currentPosition = 0;
        lastPosition = 0;
    }

    public void play(int newPosition) {
        this.characterView.setVisibility(View.VISIBLE);

        this.lastPosition = currentPosition;
        this.currentPosition = newPosition;
    }

    public int getPosition() {
        return currentPosition;
    }

    public void move(final Animation.AnimationListener listener) {
        final int[] startFrom = {lastPosition};
        final int endAt = currentPosition;

        final float[] x1 = {getX(startFrom[0])};
        final float[] y1 = {getY(startFrom[0])};

        final float[] x2 = {getX(startFrom[0] + 1)};
        final float[] y2 = {getY(startFrom[0] + 1)};

        if (bitten && currentPosition - lastPosition != 6) {
            currentPosition = lastPosition;
            x2[0] = getX(startFrom[0]);
            y2[0] = getY(startFrom[0]);
        } else if (climbedLadder) {
            climbedLadder = false;
        }

        TranslateAnimation animation = new TranslateAnimation(x1[0], x2[0], y1[0], y2[0]);
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                listener.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (bitten && currentPosition - lastPosition != 6) {
                    listener.onAnimationEnd(animation);
                    currentPosition = lastPosition;
                    return;
                }

                startFrom[0]++;

                // Get new initial and ending points
                x1[0] = x2[0];
                y1[0] = y2[0];

                x2[0] = getX(startFrom[0] + 1);
                y2[0] = getY(startFrom[0] + 1);

                animation = new TranslateAnimation(x1[0], x2[0], y1[0], y2[0]);
                animation.setDuration(300);
                animation.setFillAfter(true);

                if (startFrom[0] != endAt) {
                    if (startFrom[0] < endAt - 1) {
                        animation.setAnimationListener(this);
                    } else {
                        animation.setAnimationListener(listener);
                    }
                    characterView.startAnimation(animation);
                } else if (currentPosition - lastPosition == 1) {
                    listener.onAnimationEnd(animation);
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                listener.onAnimationEnd(animation);
            }
        });
        characterView.startAnimation(animation);
        animation.setFillAfter(true);
    }

    public void moveDirectly(Animation.AnimationListener listener) {
        Point from = new Point(getX(lastPosition), getY(lastPosition));
        Point to = new Point(getX(currentPosition), getY(currentPosition));

        AnimationPlayer.translateAnimation(characterView, from, to, 1000, listener);
    }

    private int getX(int position) {
        int col = position % 10;
        col = (position % 10 == 0) ? 10 : col;

        int row = position / 10;
        row = (position % 10 == 0) ? row - 1 : row;

        col = (row % 2 != 0) ? (10 - col) : col - 1;
        return (int) (col / 10.f * Board.WIDTH);
    }

    private int getY(int position) {
        int row = position / 10;
        row = (position % 10 == 0) ? row : row + 1;
        return Board.HEIGHT - (int) (row / 10.f * Board.HEIGHT);
    }

    public void reset() {
        this.currentPosition = 0;
        this.lastPosition = 0;

        this.characterView.setVisibility(View.GONE);
    }

    public void goBack() {
        this.currentPosition = this.lastPosition;
    }

    public void climbedLadder(boolean climbedLadder) {
        this.climbedLadder = climbedLadder;
    }

    public boolean hasClimbedLadder() {
        return climbedLadder;
    }

    public boolean isBitten() {
        return bitten;
    }

    public void setBitten(boolean bitten) {
        this.bitten = bitten;
    }
}