package sfllhkhan95.game.kaptaansquest;

import android.app.Activity;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import sfllhkhan95.game.GameCore;
import sfllhkhan95.game.input.FlingListener;
import sfllhkhan95.game.kaptaansquest.model.Dice;
import sfllhkhan95.game.kaptaansquest.model.Difficulty;
import sfllhkhan95.game.kaptaansquest.model.characters.GameCharacter;
import sfllhkhan95.game.kaptaansquest.model.characters.HumanCharacter;


public class Game extends GameCore {

    private final Dice dice;
    private final Board board;
    private Difficulty difficulty;
    private boolean machinePlaying;
    private HumanCharacter humanPlayer;
    private GameCharacter automatedOpponent;
    private GameCharacter winner;
    private TextView status;

    public Game(Activity context) {
        board = new Board();

        dice = new Dice((ImageView) context.findViewById(R.id.dice));
        dice.setOnRollListener(new DiceRolledListener());
        dice.setFlingListener(context, new FlingListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (machinePlaying) return false;

                Point start = new Point(
                        (int) (e1.getX() - Board.WIDTH / 2),
                        (int) (e1.getY() - Board.HEIGHT / 2));

                Point end = new Point(
                        (int) (e2.getX() - Board.WIDTH / 2),
                        (int) (e2.getY() - Board.HEIGHT / 2));

                dice.rollDice(start, end);
                return true;
            }
        });

        status = (TextView) context.findViewById(R.id.game_status);
        machinePlaying = false;
    }

    public Board getBoard() {
        return board;
    }

    public HumanCharacter getPlayer() {
        return humanPlayer;
    }

    public void setPlayer(HumanCharacter humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    public void setOpponent(GameCharacter automatedOpponent) {
        this.automatedOpponent = automatedOpponent;
    }

    public GameCharacter getWinner() {
        return winner;
    }

    private void takeTurn(final GameCharacter character) {
        // Roll dice and update player/dice
        int diceValue = dice.roll(machinePlaying ? difficulty.getWinThreshold() : 100 - difficulty.getWinThreshold());
        character.play(character.getPosition() + diceValue);
        dice.updateFace();

        // Move player on board
        board.moveOnBoard(character, new PlayerMovedListener(character));
    }

    public Dice getDice() {
        return dice;
    }

    public Difficulty nextLevel() {
        // Update game level
        switch (difficulty.getLevel()) {
            case 1:
                setDifficulty(40);
                break;
            case 2:
                setDifficulty(50);
                break;
            case 3:
                setDifficulty(60);
                break;
            case 4:
                setDifficulty(80);
                break;
        }

        // Reset game state
        humanPlayer.reset();
        automatedOpponent.reset();
        machinePlaying = false;
        status.setText("Player's turn. Swipe to roll dice.");

        return difficulty;
    }

    public Difficulty setDifficulty(int difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this.difficulty;
    }

    private void automatedTurn() {
        if (!isOver()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    Point start = new Point(
                            random.nextInt() % Board.WIDTH / 2,
                            random.nextInt() % Board.HEIGHT / 2);

                    Point end = new Point(
                            random.nextInt() % Board.WIDTH / 2,
                            random.nextInt() % Board.HEIGHT / 2);

                    dice.rollDice(start, end);
                }
            }, 1500);
        }
    }

    private class PlayerMovedListener implements Animation.AnimationListener {

        private final GameCharacter character;

        PlayerMovedListener(GameCharacter character) {
            this.character = character;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            if (machinePlaying) {
                status.setText("Computer's threw " + String.valueOf(dice.getLastRoll()) + ". Moving ... ");
            } else {
                status.setText("Player's threw " + String.valueOf(dice.getLastRoll()) + ". Moving ... ");
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (character.getPosition() == 100) {
                        end();
                        winner = character;
                    } else {
                        if (dice.getLastRoll() != 6) {
                            if (machinePlaying) {
                                if (!character.hasClimbedLadder()) {
                                    machinePlaying = false;
                                } else {
                                    automatedTurn();
                                }
                            } else {
                                if (!character.hasClimbedLadder()) {
                                    machinePlaying = true;
                                    automatedTurn();
                                }
                            }
                        } else {
                            character.setBitten(false);
                            if (machinePlaying) {
                                automatedTurn();
                            }
                        }
                    }

                    if (!machinePlaying) {
                        status.setText("Player's turn. Swipe to roll dice.");
                    } else {
                        status.setText("Computer's turn ... ");
                    }
                }
            }, 1500);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class DiceRolledListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            dice.startRolling();
            status.setText("Rolling dice ... ");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dice.hide();

            if (machinePlaying) {
                takeTurn(automatedOpponent);
            } else {
                takeTurn(humanPlayer);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}