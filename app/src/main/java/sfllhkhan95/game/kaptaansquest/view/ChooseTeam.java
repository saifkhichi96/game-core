package sfllhkhan95.game.kaptaansquest.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import sfllhkhan95.game.kaptaansquest.R;

public class ChooseTeam extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        findViewById(R.id.char1).setOnClickListener(this);
        findViewById(R.id.char2).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.char1) {
            Intent intent = new Intent(this, GameDrawer.class);

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("Team", "Kaptaan");
            editor.apply();

            setResult(1);
            intent.putExtra("Kaptaan", true);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.char2) {
            Intent intent = new Intent(this, GameDrawer.class);

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("Team", "Sharif");
            editor.apply();

            setResult(1);
            intent.putExtra("Kaptaan", false);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(0);
        finish();
    }
}