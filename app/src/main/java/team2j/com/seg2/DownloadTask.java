package team2j.com.seg2;

import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Downloads the json and passes it to the parser
 */
public class DownloadTask extends AsyncTask<String, Object, Object> {

    int type = 0;
    public DownloadTask(int type) {
        this.type = type;
    }

    /**
     * downloads the json
     * @param urls the url to download
     * @return the data parsed
     */
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


            ArrayList<DataPoint> population = Core.parse(result);


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

    /**
     * Triggered when the download and parsing is done
     * @param listener Sets the listener to be triggered
     */
    DataDownloaded listener;
    public void setListener(DataDownloaded listener){
        this.listener = listener;
    }
    public interface DataDownloaded {
        void downloaded(Object c);
    }
}
