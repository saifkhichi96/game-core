package sfllhkhan95.game.kaptaansquest.dharna.view;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import sfllhkhan95.game.kaptaansquest.dharna.R;

public class DharnaResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameend);

        String team = getSharedPreferences("PREFS", MODE_PRIVATE).getString("Team", null);
        try {
            if (team.equals("Kaptaan")) {
                findViewById(R.id.victory).setBackgroundResource(R.drawable.image_victoryplayerone);
                MediaPlayer.create(this, R.raw.tabdeeli).start();
            } else {
                findViewById(R.id.victory).setBackgroundResource(R.drawable.image_victoryplayertwo);
                MediaPlayer.create(this, R.raw.sher).start();
            }
        } catch (NullPointerException ex) {
            finish();
        }

    }

}
