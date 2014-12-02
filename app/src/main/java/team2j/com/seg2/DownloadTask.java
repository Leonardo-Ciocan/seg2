package team2j.com.seg2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.GpsStatus;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadTask extends AsyncTask<String, Object, Object> {
    ImageView bmImage;

    int type = 0;
    public DownloadTask(int type) {
        this.type = type;
    }

    //the network operations are run in the background
    protected Object doInBackground(String... urls) {

        try {
            URL u = new URL(urls[0]);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            String result = null;
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    result = sb.toString();
            }


            ArrayList<DataPoint> population = Core.parsePopulationJson(result);


        /*//this download is done
        Core.pending_downloads--;
        //if it's the last one then we are done downloading - draw the chart
        if(Core.pending_downloads == 0){
            Core.listener.ready();
        }*/

            listener.downloaded(population);
            return population;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject result) {

    }

    DataDownloaded listener;
    public void setListener(DataDownloaded listener){
        this.listener = listener;
    }
    public interface DataDownloaded {
        void downloaded(Object c);
    }
}
