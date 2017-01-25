package vv.game.vaibhav.tiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vaibhav.tiles.R;

public class Levels extends AppCompatActivity {

    Intent intent;
    int i = 1;
    public static final int totalLevels = 12;
    Button [] levels = new Button[totalLevels];
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        mydb = new DBHelper(this);

        levels[0] = (Button) findViewById(R.id.level1Button);
        levels[1] = (Button) findViewById(R.id.level2Button);
        levels[2] = (Button) findViewById(R.id.level3Button);
        levels[3] = (Button) findViewById(R.id.level4Button);
        levels[4] = (Button) findViewById(R.id.level5Button);
        levels[5] = (Button) findViewById(R.id.level6Button);
        levels[6] = (Button) findViewById(R.id.level7Button);
        levels[7] = (Button) findViewById(R.id.level8Button);
        levels[8] = (Button) findViewById(R.id.level9Button);
        levels[9] = (Button) findViewById(R.id.level10Button);
        levels[10] = (Button) findViewById(R.id.level11Button);
        levels[11] = (Button) findViewById(R.id.level12Button);

        levels[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), Level1.class);
                startActivity(intent);
            }
        });

        levels[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(1) != -1){
                    intent = new Intent(getBaseContext(), Level2.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(2) != -1){
                    intent = new Intent(getBaseContext(), Level3.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(3) != -1){
                    intent = new Intent(getBaseContext(), Level4.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(4) != -1){
                    intent = new Intent(getBaseContext(), Level5.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(5) != -1){
                    intent = new Intent(getBaseContext(), Level6.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(6) != -1){
                    intent = new Intent(getBaseContext(), Level7.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(7) != -1){
                    intent = new Intent(getBaseContext(), Level8.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(8) != -1){
                    intent = new Intent(getBaseContext(), Level9.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(9) != -1){
                    intent = new Intent(getBaseContext(), Level10.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(10) != -1){
                    intent = new Intent(getBaseContext(), Level11.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        levels[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mydb.getScore(11) != -1){
                    intent = new Intent(getBaseContext(), Level12.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Level Locked.. " +
                            "Win All Previous Levels First",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        highlightLevels();

    }

    public void highlightLevels(){
        for(i = 1; i < totalLevels; i++){
            if(mydb.getScore(i) != -1){
                levels[i].setAlpha(1);
            }
        }
    }
}
