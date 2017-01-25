package vv.game.vaibhav.tiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vaibhav.tiles.R;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {

    DBHelper mydb;

    public static final int levelMax = 20;

    ListView levelListView, scoreListView;
    ArrayList<String> level;
    ArrayAdapter<String> levelAdapter;
    ArrayList<String> levelScore;
    ArrayAdapter<String> levelScoreAdapter;

    TextView noScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        mydb = new DBHelper(this);

        Button leaderboardButton = (Button) findViewById(R.id.leaderboardButton);

        int i = 1;
        level = new ArrayList<String>();
        levelScore = new ArrayList<String>();

        levelListView = (ListView) findViewById(R.id.levelListView);
        scoreListView = (ListView) findViewById(R.id.scoreListView);
        noScore = (TextView) findViewById(R.id.noScore);

        levelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, level);
        levelListView.setAdapter(levelAdapter);
        levelScoreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, levelScore);
        scoreListView.setAdapter(levelScoreAdapter);

        if(mydb.getCount() > 0){
            noScore.setVisibility(View.GONE);
            if(mydb.getScore(-2) != -1){
                addItems(-2, mydb.getScore(-2));
            }
            if(mydb.getScore(-1) != -1){
                addItems(-1, mydb.getScore(-1));
            }
            for(i = 1; i <= levelMax; i++){
                if(mydb.getScore(i) != -1){
                    addItems(i, mydb.getScore(i));
                }
            }
        }

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearItems();
                mydb.deleteScore();
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Leaderboard.class);
                startActivity(i);
            }
        });

        levelListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View v = view.getChildAt(0);
                if (v != null)
                    scoreListView.setSelectionFromTop(firstVisibleItem, v.getTop());
            }
        });
        scoreListView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void addItems(int x, int y)
    {
        if(x == -1)
            level.add("Player");
        else if(x == -2)
            level.add("Beginner");
        else
            level.add(""+x);
        levelScore.add(""+y);
        levelAdapter.notifyDataSetChanged();
        levelScoreAdapter.notifyDataSetChanged();
    }

    public void clearItems()
    {
        level.clear();
        levelScore.clear();
        levelAdapter.notifyDataSetChanged();
        levelScoreAdapter.notifyDataSetChanged();
        noScore.setVisibility(View.VISIBLE);
    }
}
