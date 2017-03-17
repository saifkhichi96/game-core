package sfllhkhan95.game.kaptaansquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import sfllhkhan95.game.kaptaansquest.R;

public class GameStory extends Activity implements View.OnClickListener {

    int activeStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        activeStory = 1;

        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.skip).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            switch (activeStory) {
                case 1:
                    findViewById(R.id.story).setBackgroundResource(R.drawable.image_storytwo);
                    activeStory++;
                    break;
                case 2:
                    findViewById(R.id.story).setBackgroundResource(R.drawable.image_storythree);
                    activeStory++;
                    break;
                case 3:
                    activeStory = 1;
                    skip();
                    break;
            }
        } else if (v.getId() == R.id.skip) {
            skip();
        }
    }

    @Override
    public void onBackPressed() {
        switch (activeStory) {
            case 3:
                findViewById(R.id.story).setBackgroundResource(R.drawable.image_storytwo);
                activeStory--;
                break;
            case 2:
                findViewById(R.id.story).setBackgroundResource(R.drawable.image_storyone);
                activeStory--;
                break;
            default:
                super.onBackPressed();
        }
    }

    private void skip() {
        if (getSharedPreferences("PREFS", MODE_PRIVATE).getString("Team", null) == null) {
            startActivity(new Intent(GameStory.this, ChooseTeam.class));
        } else {
            startActivity(new Intent(GameStory.this, GameDrawer.class));
        }

        finish();
    }
}
