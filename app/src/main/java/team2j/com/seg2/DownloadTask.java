package team2j.com.seg2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DownloadTask extends AsyncTask<String, Void, Void> {
    ImageView bmImage;

    public DownloadTask(String URL) {

    }

    //the network operations are run in the background
    protected Void doInBackground(String... urls) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(urls[0]);
        //httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }

        //this will extract the jsonarray , currently works for the population
        JSONArray jObject = null;
        JSONArray object = null;
        String url = null;
        try {
            jObject = new JSONArray(result);
            object = jObject.getJSONArray(1);
            int x = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //we iterate through the array , extract the date and value and add it to our arraylist
        ArrayList<DataPoint> Data = new ArrayList<DataPoint>();
        for(int x= 0; x< object.length();x++){
            try {
                JSONObject current = object.getJSONObject(x);
                Data.add(new DataPoint(current.getInt("date"),current.getInt("value")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Core.DataSets.add(new DataSet(urls[1],Data));
        //this download is done
        Core.pending_downloads--;
        //if it's the last one then we are done downloading - draw the chart
        if(Core.pending_downloads == 0){
            Core.listener.ready();
        }

        return null;
    }

    protected void onPostExecute(JSONObject result) {

    }
}
