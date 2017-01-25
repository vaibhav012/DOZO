package vv.game.vaibhav.tiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vaibhav on 3/9/2016.
 */
public class VVInternetQuery extends AsyncTask<String, Void, String> {

    ProgressDialog loading;
    Context activityContext;
    Context applicationContext;
    VVInternetQueryInterface returnInterface;

    public VVInternetQuery(Context c1, Context c2){
        activityContext = c1;
        applicationContext = c2;
        this.returnInterface = (VVInternetQueryInterface) c1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = new ProgressDialog(activityContext);
        loading.setMessage("Loading Scores...");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
        returnInterface.onTaskComplete(s);
        //Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... params) {
        String queryUrl = params[0];
        String s = params[1];
        s = queryUrl+s;
        VVReformatStringToUrl r = new VVReformatStringToUrl();
        s = r.reformatString(s);
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(s);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String result;
            result = bufferedReader.readLine();
            return result;
        }catch(Exception e){
            return "Timeout : Check your Connection or try later";
        }
    }
}