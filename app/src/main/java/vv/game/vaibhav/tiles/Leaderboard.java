package vv.game.vaibhav.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaibhav.tiles.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity implements VVInternetQueryInterface{

    String uname;
    int score;
    RelativeLayout createUserLayout;
    TextView yourRank;
    DBHelper mydb;

    ArrayList<String> usernameList;
    ArrayList<String> scoreList;
    ArrayList<String> rankList;
    ListView leaderboardListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        createUserLayout = (RelativeLayout) findViewById(R.id.createUserLayout);
        Button createUserButton = (Button) findViewById(R.id.createUserButton);
        final EditText usernameText = (EditText) findViewById(R.id.usernameText);
        yourRank = (TextView) findViewById(R.id.yourRank);
        leaderboardListView = (ListView) findViewById(R.id.leaderboardListView);
        usernameList = new ArrayList<String>();
        scoreList = new ArrayList<String>();
        rankList = new ArrayList<String>();

        mydb = new DBHelper(this);

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usernameText.getText().toString().trim().equals(""))
                    createUser(usernameText.getText().toString().trim());
                else
                    Toast.makeText(getBaseContext(),"Please create a username", Toast.LENGTH_SHORT).show();
            }
        });

        if(mydb.getScore(-1) == -1){
            score = 0;
        }
        else
            score = mydb.getScore(-1);

        if(!isConnectedToInternet()){
            Toast.makeText(getBaseContext(), "No Internet Connection Detected", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(!checkLogin()){
            createUserLayout.setVisibility(View.VISIBLE);
        }
        else if(checkLogin()) {
            updateUser(uname);
        }
    }

    public void getLeaderboardDetails(){
        String urlSuffix = "";
        String mainUrl = "http://www.vaibhavtayal.com/tiles/getLeaderboardDetails.php";
        VVInternetQuery ru = new VVInternetQuery(Leaderboard.this, getApplicationContext());
        ru.execute(mainUrl, urlSuffix);
    }

    public void createUser(String username){
        String urlSuffix = "?u="+username+"&s="+score;
        uname = username;
        String mainUrl = "http://www.vaibhavtayal.com/tiles/createUser.php";
        VVInternetQuery ru = new VVInternetQuery(Leaderboard.this, getApplicationContext());
        ru.execute(mainUrl, urlSuffix);
    }

    public void updateUser(String username){
        String urlSuffix = "?u="+username+"&s="+score;
        uname = username;
        String mainUrl = "http://www.vaibhavtayal.com/tiles/updateUser.php";
        VVInternetQuery ru = new VVInternetQuery(Leaderboard.this, getApplicationContext());
        ru.execute(mainUrl, urlSuffix);
    }

    public void updateLeaderboard(String scores) {
        JSONObject jsonResponse;
        String OutputData = "";
        leaderboardListView.setAdapter(null);
        usernameList.clear();
        scoreList.clear();
        rankList.clear();
        int rank = 1;
        try {
            jsonResponse = new JSONObject(scores);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("scores");
            int lengthJsonArr = jsonMainNode.length();
            for(int i=0; i < lengthJsonArr; i++)
            {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                rankList.add(""+rank++);
                usernameList.add(jsonChildNode.optString("username").toString());
                scoreList.add(jsonChildNode.optString("score").toString());
                //String username   = jsonChildNode.optString("username").toString();
                //String score = jsonChildNode.optString("score").toString();
                //OutputData += "Username : "+ username +" | Score : "+ score +".";
            }
            //Toast.makeText(Leaderboard.this, OutputData, Toast.LENGTH_SHORT).show();
            leaderboardListView.setAdapter(new CustomAdapter(this, rankList, usernameList, scoreList));
            yourRank.setText("Your Username : " + uname +
                    "\nYour Score : " + scoreList.get(usernameList.indexOf(uname)) +
                    "   Your Rank : " + rankList.get(usernameList.indexOf(uname)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkLogin(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(sharedPreferences.getString("tilesLIn","NULL").equals("NULL")) {
            return false;
        }
        else {
            uname = sharedPreferences.getString("tilesLIn", "NULL");
            return true;
        }
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onTaskComplete(String result) {
        if(result.equals("success")){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("tilesLIn", uname);
            editor.commit();
            createUserLayout.setVisibility(View.GONE);
            getLeaderboardDetails();
        }
        else if(result.equals("exists")) {
            Toast.makeText(getBaseContext(),"Username exists choose a different one", Toast.LENGTH_SHORT).show();
        }
        else if(result.equals("updated")) {
            getLeaderboardDetails();
        }
        else {
            char[] temp = result.toCharArray();
            if(temp[0] == 'v' && temp[1] == 's'){
                updateLeaderboard(result.substring(2,result.length()));
            }
            else {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}