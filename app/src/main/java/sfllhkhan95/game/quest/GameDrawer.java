package sfllhkhan95.game.quest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import sfllhkhan95.game.ludo.R;
import sfllhkhan95.game.ludo.view.GameActivity;

public class GameDrawer extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.dharna).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);
        setTeam();
        loadAds();
    }

    private void setTeam() {
        ((TextView) findViewById(R.id.team)).setText("Team " + getSharedPreferences("PREFS", MODE_PRIVATE).getString("Team", null));
    }

    private void loadAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6293532072634065~3569270434");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dharna) {
            if (getSharedPreferences("PREFS", MODE_PRIVATE).getString("Team", "").equals("Kaptaan")) {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("Kaptaan", true);
                startActivity(intent);
            } else if (getSharedPreferences("PREFS", MODE_PRIVATE).getString("Team", "").equals("Sharif")) {

                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("Kaptaan", false);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.settings) {
            startActivityForResult(new Intent(GameDrawer.this, ChooseTeam.class), 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultData, Intent intent) {

        if (requestCode == 1 && resultData == 1) {
            finish();
        }

    }
}