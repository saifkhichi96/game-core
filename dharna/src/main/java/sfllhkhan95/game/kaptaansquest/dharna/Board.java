package sfllhkhan95.game.kaptaansquest.dharna;

import android.util.SparseArray;
import android.view.animation.Animation;

import sfllhkhan95.game.kaptaansquest.dharna.model.characters.GameCharacter;
import sfllhkhan95.game.kaptaansquest.dharna.model.objects.GameObject;
import sfllhkhan95.game.kaptaansquest.dharna.model.objects.Ladder;
import sfllhkhan95.game.kaptaansquest.dharna.model.objects.Snake;


public class Board {

    public static int WIDTH;
    public static int HEIGHT;
    private final SparseArray<GameObject> snakesAndLadders;

    Board() {
        snakesAndLadders = new SparseArray<>();

        // Add ladders
        snakesAndLadders.put(3, new Ladder(3, 39));
        snakesAndLadders.put(10, new Ladder(10, 12));
        snakesAndLadders.put(27, new Ladder(27, 53));
        snakesAndLadders.put(56, new Ladder(56, 84));
        snakesAndLadders.put(61, new Ladder(61, 99));
        snakesAndLadders.put(72, new Ladder(72, 90));

        // Add snakes
        snakesAndLadders.put(97, new Snake(97, 75));
        snakesAndLadders.put(66, new Snake(66, 52));
        snakesAndLadders.put(63, new Snake(63, 60));
        snakesAndLadders.put(47, new Snake(47, 25));
        snakesAndLadders.put(31, new Snake(31, 4));
        snakesAndLadders.put(16, new Snake(16, 13));
    }

    void moveOnBoard(final GameCharacter character, final Animation.AnimationListener onMotionEnd) {
        if (character.getPosition() > 100) {
            character.goBack();
        }

        final int position = character.getPosition();

        character.scoreCard.setText(String.valueOf(position));

        character.move(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onMotionEnd.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                GameObject snakeOrLadder = snakesAndLadders.get(position);
                if (snakeOrLadder != null) {
                    character.play(snakeOrLadder.moveTo());
                    character.moveDirectly(null);

                    if (snakeOrLadder.getClass().equals(Snake.class)) {
                        character.setBitten(true);
                    } else if (snakeOrLadder.getClass().equals(Ladder.class)) {
                        character.climbedLadder(true);
                    }
                }

                onMotionEnd.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                onMotionEnd.onAnimationRepeat(animation);
            }
        });
    }

    public void setSize(int x, int y) {
        WIDTH = x;
        HEIGHT = y;
    }
}