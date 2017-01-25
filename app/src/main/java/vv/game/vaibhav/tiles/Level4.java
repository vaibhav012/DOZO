package vv.game.vaibhav.tiles;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vaibhav.tiles.R;

import java.util.Random;

public class Level4 extends AppCompatActivity {

    public static final int xmax = 3;
    public static final int ymax = 2;
    public static final int beginBlack = 1;
    private final long startTime = 5 * 1000;
    private final long interval = 1 * 1000;
    private final int LEVEL_ID = 4;
    int i = 0, j = 0;
    int score=0;
    int [][] gameOn = new int[xmax+1][ymax+1];

    TextView notice;
    TextView timer;
    Button [][] buttons = new Button[xmax+1][ymax+1];
    Button ButtonStart = null;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);

        notice = (TextView) findViewById(R.id.notice);
        timer = (TextView) findViewById(R.id.Timer);
        ButtonStart = (Button) findViewById(R.id.start);
        buttons[0][0] = (Button) findViewById(R.id.score);
        buttons[0][1] = (Button) findViewById(R.id.button2);
        buttons[0][2] = (Button) findViewById(R.id.button3);
        buttons[1][0] = (Button) findViewById(R.id.button4);
        buttons[1][1] = (Button) findViewById(R.id.button5);
        buttons[1][2] = (Button) findViewById(R.id.button6);
        buttons[2][0] = (Button) findViewById(R.id.button7);
        buttons[2][1] = (Button) findViewById(R.id.button8);
        buttons[2][2] = (Button) findViewById(R.id.button9);
        buttons[3][0] = (Button) findViewById(R.id.button10);
        buttons[3][1] = (Button) findViewById(R.id.button11);
        buttons[3][2] = (Button) findViewById(R.id.button12);

        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allReset();
            }
        });

        for (i = 0; i <= xmax; i++) {
            for (j = 0; j <= ymax; j++) {
                final int x = i;
                final int y = j;
                final Button theButton = buttons[i][j];
                theButton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            selectButton(x, y);
                            if (checkLost(x, y)) {
                                startResult(2);
                            } else if (checkWin()) {
                                startResult(1);
                            }
                            return true;
                            //Toast.makeText(getBaseContext(),"yes", Toast.LENGTH_SHORT).show();
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        reset();

    }

    @Override
    protected void onPause() {
        super.onPause();

        countDownTimer.cancel();

    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onFinish() {
            timer.setText("Time's up!");
            if(checkWin())
                startResult(1);
            else
                startResult(3);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("" + millisUntilFinished / 1000);
        }
    }

    public boolean checkLost(int x, int y) {
        if(x == 0){
            if(y == 0){
                return (gameOn[x+1][y] == 1 || gameOn[x][y+1] == 1);
            }
            else if(y == ymax){
                return (gameOn[x+1][y] == 1 || gameOn[x][y-1] == 1);
            }
            else {
                return (gameOn[x+1][y] == 1 || gameOn[x][y+1] == 1 || gameOn[x][y-1] == 1);
            }
        }
        else if(x == xmax) {
            if(y == 0){
                return (gameOn[x-1][y] == 1 || gameOn[x][y+1] == 1);
            }
            else if(y == ymax){
                return (gameOn[x-1][y] == 1 || gameOn[x][y-1] == 1);
            }
            else {
                return (gameOn[x-1][y] == 1 || gameOn[x][y+1] == 1 || gameOn[x][y-1] == 1);
            }
        }
        else {
            if(y == 0){
                return (gameOn[x-1][y] == 1 || gameOn[x+1][y] == 1 || gameOn[x][y+1] == 1);
            }
            else if(y == ymax){
                return (gameOn[x-1][y] == 1 || gameOn[x+1][y] == 1 || gameOn[x][y-1] == 1);
            }
            else {
                return (gameOn[x-1][y] == 1 || gameOn[x+1][y] == 1 || gameOn[x][y-1] == 1 || gameOn[x][y+1] == 1);
            }
        }
    }

    public boolean checkWin() {
        for (i = 0; i <= xmax; i++) {
            for (j = 0; j <= ymax; j++) {
                if(gameOn[i][j] != 1 && !checkLost(i, j))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void selectButton(int x, int y) {
        buttons[x][y].setBackgroundColor(Color.BLACK);
        buttons[x][y].setEnabled(false);
        gameOn[x][y] = 1;
    }

    public void startResult(int result) {
        countDownTimer.cancel();
        Intent intent = new Intent(getBaseContext(), Result.class);
        intent.putExtra("level", LEVEL_ID);
        intent.putExtra("result", result);
        for (int x[] : gameOn)
            for (int y : x)
                if(y == 1)
                    score++;
        intent.putExtra("levelScore",score);
        startActivity(intent);
    }

    public void reset() {

        countDownTimer = new MyCountDownTimer(startTime, interval);
        timer.setText(String.valueOf(startTime / 1000));
        countDownTimer.start();

        score=0;

        Random random = new Random();
        int [] pos = {random.nextInt(xmax+1), random.nextInt(ymax+1)};
        selectButton(pos[0], pos[1]);

        for(i = 1; i < beginBlack; i++){
            while(gameOn[pos[0]][pos[1]] == 1 || checkLost(pos[0], pos[1])){
                pos[0] = random.nextInt(xmax+1);
                pos[1] = random.nextInt(ymax+1);
            }
            selectButton(pos[0], pos[1]);
        }
    }

    public void allReset(){

        countDownTimer.cancel();

        for (i = 0; i <= xmax; i++) {
            for (j = 0; j <= ymax; j++) {
                gameOn[i][j] = 0;
                buttons[i][j].setBackgroundColor(Color.WHITE);
                buttons[i][j].setEnabled(true);
            }
        }
        reset();
    }
}