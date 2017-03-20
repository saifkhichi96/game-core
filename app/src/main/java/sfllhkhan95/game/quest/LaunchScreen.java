package sfllhkhan95.game.quest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import sfllhkhan95.game.ludo.R;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(3000, 3000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(LaunchScreen.this, GameStory.class));
                finish();
            }
        }.start();

    }

}
