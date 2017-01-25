package vv.game.vaibhav.tiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vaibhav.tiles.R;

public class ContinuousGameLevels extends AppCompatActivity {

    Button buttonEasyGame, buttonMediumGame, buttonDifficultGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuous_game_levels);

        buttonMediumGame = (Button) findViewById(R.id.buttonMediumGame);
        buttonDifficultGame = (Button) findViewById(R.id.buttonDifficultGame);

        buttonMediumGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ContinuousGame4.class);
                startActivity(i);
            }
        });

        buttonDifficultGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ContinuousGame.class);
                startActivity(i);
            }
        });
    }
}
