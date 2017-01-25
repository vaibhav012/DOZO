package vv.game.vaibhav.tiles;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaibhav.tiles.R;

public class ContinuousGame extends AppCompatActivity implements Fragment5.TalkToActivity, Animation.AnimationListener{

    long timePassed = 0, totalPixelScrolled = 0;
    int pixelScrolledRev = 0, pixelScrolled = 0;
    ScrollView gameScrollView;
    int score = 0;
    int pixel1dp;
    TextView scoreText;
    FragmentManager fragmentManager;
    int fragmentContainer;
    int topFragTag = 1;
    int checkFragTag = 2;
    int bottomFragTag = 0;
    CountDownTimer countDownTimer, countDownTimer1;
    VVScreenSize vvScreenSize;
    int screenWidth, screenHeight, tileDimenPixel;

    Animation animFadein;
    TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continuous_game);

        pixel1dp = getResources().getDimensionPixelSize(R.dimen.pixelConvert);
        vvScreenSize = new VVScreenSize();
        screenHeight = (int)vvScreenSize.vvScreenHeightPixel(ContinuousGame.this);
        screenWidth = (int)vvScreenSize.vvScreenWidthPixel(ContinuousGame.this);
        tileDimenPixel = (int)(screenWidth-5*pixel1dp)/5;

        gameScrollView = (ScrollView) findViewById(R.id.gameScrollView);
        scoreText = (TextView) findViewById(R.id.Score);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        fragmentContainer = R.id.fragment_container;

//        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.blink);
//        animFadein.setAnimationListener(this);

        fragmentManager = getFragmentManager();

        for(int i = 0; i < 15; i++) {
            addFragment();
        }

        vvScrollViewScroll();

        gameScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation
        textViewScore.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
    }

    public void vvScrollViewScroll(){
        final long totalScrollTime = 2147483647;
        final int scrollPeriod = 20;
        final int heightToScroll = tileDimenPixel/50;

        gameScrollView.post(new Runnable() {
            @Override
            public void run() {
                countDownTimer = new MyCountDownTimer(totalScrollTime, scrollPeriod, heightToScroll).start();
            }
        });
    }

    @Override
    public Boolean sendButton(int fragId, int ButtonId, int flagBoth) {
        if(flagBoth == 0){
            Fragment5 frag = (Fragment5) fragmentManager.findFragmentByTag("row"+(fragId - 1));
            if (!frag.fragCheck1(ButtonId)) {
                return false;
            } else
                return true;
        }
        else {
            if (fragId == 1) {
                Fragment5 frag = (Fragment5) fragmentManager.findFragmentByTag("row" + (fragId + 1));
                if (!frag.fragCheck1(ButtonId)) {
                    return false;
                } else
                    return true;
            }
            else {
                Fragment5 frag = (Fragment5) fragmentManager.findFragmentByTag("row" + (fragId - 1));
                Fragment5 frag1 = (Fragment5) fragmentManager.findFragmentByTag("row" + (fragId + 1));
                if (frag.fragCheck1(ButtonId) || frag1.fragCheck1(ButtonId)) {
                    return true;
                } else
                    return false;
            }
        }
    }

    @Override
    public void startResult(int resultId) {
        countDownTimer.cancel();
        Intent intent = new Intent(getBaseContext(), ResultContinuous.class);
        intent.putExtra("level", -1);
        intent.putExtra("result", resultId);
        intent.putExtra("levelScore", score);
        startActivity(intent);
    }

    @Override
    public void addScore() {
        score++;
        scoreText.setText("" + score);
    }

    public class MyCountDownTimer extends CountDownTimer {
        long scrollPeriod;
        int heightToScroll;
        long flag;
        public MyCountDownTimer(long startTime, long interval, int h) {
            super(startTime, interval);
            scrollPeriod = interval;
            heightToScroll = h;
        }
        @Override
        public void onFinish() {
        }
        @Override
        public void onTick(long millisUntilFinished) {
            try {
                if (timePassed > 2000) {
                    gameScrollView.scrollBy(0, heightToScroll);
                    pixelScrolled += heightToScroll;
                    totalPixelScrolled += heightToScroll;

                    if ((totalPixelScrolled / (5 * (tileDimenPixel + pixel1dp))) > flag) {
                        flag += 2;
                        heightToScroll += tileDimenPixel / 100;
                        textViewScore.setVisibility(View.VISIBLE);
                        textViewScore.setText(score + "");
                        // start the animation
                        //textViewScore.startAnimation(animFadein);

                        //textViewScore.startAnimation(animFadein);
                        MyCountDownTimerScore myCountDownTimerScore = new MyCountDownTimerScore(2000, 1000);
                        myCountDownTimerScore.start();
                    }

                    if (pixelScrolled >= (tileDimenPixel + pixel1dp)) {
                        pixelScrolled -= (tileDimenPixel + pixel1dp);
                        Fragment5 frag = (Fragment5) fragmentManager.findFragmentByTag("row" + checkFragTag);
                        for (int i = 0; i < 5; i++) {
                            if (!frag.fragCheck1(i) && !frag.fragCheckLost(i) && !sendButton(checkFragTag, i, 1)) {
                                countDownTimer.cancel();
                                frag.alertButton(i);
                                for (int j = 1; j <= bottomFragTag; j++) {
                                    Fragment5 frag1 = (Fragment5) fragmentManager.findFragmentByTag("row" + j);
                                    frag1.disableButtons();
                                }
                                endGame();
                            }
                        }
                        addFragment();
                        checkFragTag++;
                    }
                }
                timePassed = timePassed + scrollPeriod;
            }
            catch(Exception e){
            }
        }
    }

    public class MyCountDownTimerScore extends CountDownTimer {
        public MyCountDownTimerScore(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onFinish() {
            textViewScore.setVisibility(View.GONE);
        }
        @Override
        public void onTick(long millisUntilFinished) {

        }
    }

    public void addFragment(){
        bottomFragTag++;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment5 hello = new Fragment5();
        hello.setFragTag(bottomFragTag);
        fragmentTransaction.add(fragmentContainer, hello, "row" + bottomFragTag);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public void removeFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment5 frag = (Fragment5) fragmentManager.findFragmentByTag("row" + topFragTag++);
        fragmentTransaction.remove(frag);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public void endGame(){
        gameScrollView.post(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(1200, 1){
                    @Override
                    public void onFinish() {
                        startResult(2);
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(pixelScrolledRev <= (tileDimenPixel+pixel1dp))
                        {
                            gameScrollView.scrollBy(0, -1);
                            totalPixelScrolled -= 1;
                            pixelScrolledRev += 1;
                        }
                    }
                }.start();
            }
        });
    }
}
