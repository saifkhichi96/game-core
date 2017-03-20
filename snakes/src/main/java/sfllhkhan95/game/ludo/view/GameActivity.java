package sfllhkhan95.game.ludo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sfllhkhan95.game.GameOverListener;
import sfllhkhan95.game.GameStartListener;
import sfllhkhan95.game.ludo.Game;
import sfllhkhan95.game.ludo.dharna.R;
import sfllhkhan95.game.ludo.model.Difficulty;
import sfllhkhan95.game.ludo.model.characters.GameCharacter;
import sfllhkhan95.game.ludo.model.characters.HumanCharacter;

public class GameActivity extends Activity {

    private Game game;
    private Difficulty difficulty;
    private ProgressBar progressBar;
    private TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progress = (TextView) findViewById(R.id.game_level);

        game = new Game(this);
        game.setStartListener(new GameSetup());
        game.setOverListener(new GameOver());
        game.start();

        String message = getString(R.string.game_level, 1, String.valueOf(progressBar.getProgress()) + "%");
        progress.setText(message);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.getDice().getGestureDetector().onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void adjustGameBoard(final RelativeLayout board) {
        board.post(new Runnable() {
            @Override
            public void run() {
                int size = board.getHeight();
                game.getBoard().setSize(size, size);

                ViewGroup.LayoutParams params = board.getLayoutParams();
                params.width = size;
                board.setLayoutParams(params);
            }
        });
    }

    private class GameSetup implements GameStartListener {
        @Override
        public void init() {
            boolean isKaptaan = getIntent().getBooleanExtra("Kaptaan", true);
            if (isKaptaan) {
                game.setPlayer(new HumanCharacter(GameActivity.this, R.id.kaptaan, R.id.scoreSharif, R.id.labelSharif));
                game.setOpponent(new GameCharacter(GameActivity.this, R.id.sharif, R.id.scoreKaptaan, R.id.labelKaptaan));
            } else {
                game.setPlayer(new HumanCharacter(GameActivity.this, R.id.sharif, R.id.scoreKaptaan, R.id.labelKaptaan));
                game.setOpponent(new GameCharacter(GameActivity.this, R.id.kaptaan, R.id.scoreSharif, R.id.labelSharif));
            }

            difficulty = game.setDifficulty(20);

            adjustGameBoard((RelativeLayout) findViewById(R.id.board));
        }
    }

    private class GameOver implements GameOverListener {

        @Override
        public void onGameOver() {
            // If all levels completed, open game result
            if (difficulty.getLevel() >= 5) {
                startActivity(new Intent(GameActivity.this, GameResult.class));
                finish();
            }

            // If not all levels completed, updated progress
            else {
                // Each win gives the player five points
                if (game.getWinner().equals(game.getPlayer())) {
                    Toast.makeText(GameActivity.this, "Player won the round!", Toast.LENGTH_SHORT).show();
                    game.getPlayer().addScore(5);

                    // Calculate and display player's progress
                    int p = (int) (game.getPlayer().getScore() / (float) difficulty.getWinThreshold() * 100);
                    progressBar.setProgress(p);

                    String message = getString(R.string.game_level, difficulty.getLevel(), String.valueOf(p) + "%");
                    progress.setText(message);

                    // If player has finished this level, advance to next level
                    if (p >= 100) {
                        Toast.makeText(GameActivity.this, "Level up!", Toast.LENGTH_SHORT).show();

                        difficulty = game.nextLevel();

                        message = getString(R.string.game_level, difficulty.getLevel(), "0%");
                        progress.setText(message);
                        progressBar.setProgress(0);
                    }

                } else {
                    Toast.makeText(GameActivity.this, "Computer won the round!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}