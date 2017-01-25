package vv.game.vaibhav.tiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vaibhav.tiles.R;

public class Result extends AppCompatActivity {

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int level = getIntent().getExtras().getInt("level");
        int levelScore = getIntent().getExtras().getInt("levelScore");
        int result = getIntent().getExtras().getInt("result");

        TextView resultText = (TextView) findViewById(R.id.result);
        TextView levelText = (TextView) findViewById(R.id.levelText);
        TextView scoreText = (TextView) findViewById(R.id.scoreText);

        LinearLayout scoreBoardButton, levelButton;
        scoreBoardButton = (LinearLayout) findViewById(R.id.scoreBoardButton);
        levelButton = (LinearLayout) findViewById(R.id.levelButton);

        mydb = new DBHelper(this);

        if (level == -1) {
            resultText.setText("End");
            levelText.setText("Continuous Game");
            scoreText.setText("" + levelScore);
            if (mydb.getScore(level) == -1)
                mydb.addScore(level, levelScore);
            else if (levelScore > mydb.getScore(level))
                mydb.updateScore(level, levelScore);
        }
        else if (level == -2) {
            resultText.setText("End");
            levelText.setText("Continuous Game");
            scoreText.setText("" + levelScore);
            if (mydb.getScore(level) == -2)
                mydb.addScore(level, levelScore);
            else if (levelScore > mydb.getScore(level))
                mydb.updateScore(level, levelScore);
        }else {
            switch (result) {
                case 1:
                    resultText.setText("You Win");
                    levelText.setText("Level " + level);
                    scoreText.setText("" + levelScore);
                    if (mydb.getScore(level) == -1)
                        mydb.addScore(level, levelScore);
                    else if (levelScore > mydb.getScore(level))
                        mydb.updateScore(level, levelScore);
                    break;
                case 2:
                    resultText.setText("You Loose");
                    levelText.setText("Level " + level);
                    scoreText.setText("0");
                    break;
                case 3:
                    resultText.setText("Time Up!");
                    levelText.setText("Level " + level);
                    scoreText.setText("" + levelScore);
                    break;
            }
        }

        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Levels.class);
                startActivity(intent);
            }
        });

        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Scores.class);
                startActivity(intent);
            }
        });
    }
}
