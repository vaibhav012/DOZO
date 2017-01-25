package vv.game.vaibhav.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.example.vaibhav.tiles.R;

public class DOZO extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tiles);

        sharedpreferences = getSharedPreferences("tilesFirstOpen", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(!sharedpreferences.contains("opened")){
            editor.putInt("opened", 1);
            Intent intent = new Intent(getBaseContext(), HowToPlay.class);
            startActivity(intent);
        }
        editor.commit();

        Button game = (Button) findViewById(R.id.game);
        Button scores = (Button) findViewById(R.id.score);
        Button help = (Button) findViewById(R.id.help);
        Button practice = (Button) findViewById(R.id.practice);
        Button leaderboard = (Button) findViewById(R.id.leaderboard);

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ContinuousGameLevels.class);
                startActivity(intent);
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Scores.class);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HowToPlay.class);
                startActivity(intent);
            }
        });

        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Levels.class);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Leaderboard.class);
                startActivity(intent);
            }
        });    }

    public float vvScreenHeight(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density = getResources().getDisplayMetrics().density;
        float dpHeight = metrics.heightPixels / density;
        return dpHeight;
    }

    public float vvScreenWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density = getResources().getDisplayMetrics().density;
        float dpWidth = metrics.widthPixels / density;
        return dpWidth;
    }
}


