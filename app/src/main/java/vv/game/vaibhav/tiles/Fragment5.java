package vv.game.vaibhav.tiles;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.vaibhav.tiles.R;

import java.util.Random;

public class Fragment5 extends Fragment {

    private static final int xmax = 4;
    Button[] rowButtons = new Button[xmax + 1];
    int[] gameOn = {0, 0, 0, 0, 0};
    int fragTag;
    int pixel1dp;
    VVScreenSize vvScreenSize;
    int screenWidth, screenHeight, tileDimenPixel;
    LinearLayout.LayoutParams buttonParams;

    public interface TalkToActivity {
        public Boolean sendButton(int fragId, int ButtonId, int flagBoth);
        public void startResult(int resultId);
        public void addScore();
    }

    TalkToActivity talkToActivity;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        talkToActivity = (TalkToActivity) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment5, container, false);

        rowButtons[0] = (Button) view.findViewById(R.id.score);
        rowButtons[1] = (Button) view.findViewById(R.id.button2);
        rowButtons[2] = (Button) view.findViewById(R.id.button3);
        rowButtons[3] = (Button) view.findViewById(R.id.button4);
        rowButtons[4] = (Button) view.findViewById(R.id.button5);

        vvScreenSize = new VVScreenSize();
        screenHeight = (int)vvScreenSize.vvScreenHeightPixel(getActivity());
        screenWidth = (int)vvScreenSize.vvScreenWidthPixel(getActivity());
        pixel1dp = getResources().getDimensionPixelSize(R.dimen.pixelConvert);
        tileDimenPixel = (int)(screenWidth-5*pixel1dp)/5;

        setButtonDimen(tileDimenPixel);

        reset();

        //LOOP FOR ON TOUCH LISTENER
        for (int i = 0; i <= xmax; i++) {
            final int x = i;
            final Button theButton = rowButtons[x];
            theButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        selectButton(x);
                        if(fragCheckLost(x) || talkToActivity.sendButton(fragTag, x, 1))
                            talkToActivity.startResult(2);
                        else
                            talkToActivity.addScore();
                        return true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        return true;
                    }
                    return false;
                }
            });
        }

        return view;
    }

    public void setFragTag(int Tag) {
        fragTag = Tag;
    }

    public int getFragTag() {
        return fragTag;
    }

    public void selectButton(int x) {
        rowButtons[x].setBackgroundColor(Color.BLACK);
        rowButtons[x].setEnabled(false);
        gameOn[x] = 1;
    }

    public void alertButton(int x) {
        rowButtons[x].setBackgroundColor(Color.RED);
        rowButtons[x].setEnabled(false);
        gameOn[x] = 2;
    }

    public void reset() {
        int pos;
        if (fragTag == 1) {
            selectButton(0);
            selectButton(2);
            selectButton(4);
        }
        else {
            Random random = new Random();
            pos = random.nextInt(xmax + 1);
            while (talkToActivity.sendButton(fragTag, pos, 0)) {
                pos = random.nextInt(xmax + 1);
            }
            selectButton(pos);
        }
    }

    public Boolean fragCheckLost(int x) {
        if (x == 0) {
            if (gameOn[x + 1] == 1)
                return true;
        }
        else if (x == xmax) {
            if (gameOn[x - 1] == 1)
                return true;
        }
        else {
            if (gameOn[x + 1] == 1 || gameOn[x - 1] == 1)
                return true;
        }
        return false;
    }

    public Boolean fragCheck1(int x) {
        if (gameOn[x] == 1)
            return true;
        return false;
    }

    public void disableButtons(){
        for(int i = 0; i < 5; i++){
            rowButtons[i].setEnabled(false);
        }
    }

    public void setButtonDimen(int DimenPixel){
        buttonParams = new LinearLayout.LayoutParams(DimenPixel, DimenPixel);
        buttonParams.setMargins(pixel1dp/2, pixel1dp/2, pixel1dp/2, pixel1dp/2);

        for(int i = 0; i < 5; i++){
            rowButtons[i].setLayoutParams(buttonParams);
        }
    }

}

