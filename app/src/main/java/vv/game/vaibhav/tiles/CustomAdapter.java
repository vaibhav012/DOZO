package vv.game.vaibhav.tiles;

/**
 * Created by Vaibhav on 3/5/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vaibhav.tiles.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    ArrayList <String> rankList, usernameList, scoreList;
    Context context;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Leaderboard c, ArrayList<String> rankL, ArrayList<String> usernameL, ArrayList<String> scoreL) {
        // TODO Auto-generated constructor stub
        rankList = rankL;
        usernameList = usernameL;
        scoreList = scoreL;
        context = c;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rankList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView rank;
        TextView username;
        TextView score;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.leaderboard_list_row, null);
        holder.rank = (TextView) rowView.findViewById(R.id.rank);
        holder.username = (TextView) rowView.findViewById(R.id.username);
        holder.score = (TextView) rowView.findViewById(R.id.score);
        holder.rank.setText(rankList.get(position).toString());
        holder.username.setText(usernameList.get(position).toString());
        holder.score.setText(scoreList.get(position).toString());
        return rowView;
    }
}
