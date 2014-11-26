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
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");

        HttpPost httppost = new HttpPost(urls[0]);

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

        ArrayList<DataPoint> population = Core.parsePopulationJson(result);


        /*//this download is done
        Core.pending_downloads--;
        //if it's the last one then we are done downloading - draw the chart
        if(Core.pending_downloads == 0){
            Core.listener.ready();
        }*/

        listener.downloaded(population);
        return population;
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
