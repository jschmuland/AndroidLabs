package com.example.user.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WeatherForecast extends AppCompatActivity {
    public static final String TAG = "WeatherForecast";
    private ProgressBar progressBar;
    private TextView minText,maxText,currText;
    private ImageView wImg;
    Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("WeatherForecast", "onCreate:");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        minText = (TextView) findViewById(R.id.minTemp);
        maxText = (TextView) findViewById(R.id.maxTemp);
        currText = (TextView) findViewById(R.id.currentTemp);
        wImg = (ImageView) findViewById(R.id.weatherIcon);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery forcats = new ForecastQuery();
        forcats.execute();
    }

    protected class ForecastQuery extends AsyncTask<String,Integer,String>{
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        String min,max,current,picString;


        boolean wifiConnected;

        @Override
        protected String doInBackground(String...args) {


                try {
                    //getting input stream
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    Log.i(TAG, "doInBackground: conn.state= "+conn.getURL());

                    //start the parser
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(conn.getInputStream(),null);
                    parser.nextTag();
                    int eventType = parser.getEventType();

                    while (parser.next()!=XmlPullParser.END_DOCUMENT) {
                        if(parser.getEventType()!=XmlPullParser.START_TAG){
                            continue;
                        }
                        try {

                            if (parser.getName().equals("temperature")) {
                                current = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                Log.i("Weather Activity: ", "got current temp= " + current);
                                sleepNow();

                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                sleepNow();

                                Log.i("Weather Activity: ", "got min temp= " + min);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                                sleepNow();

                            } else if (parser.getName().equals("weather")) {
                                picString = parser.getAttributeValue(null, "icon");

                                Log.i(TAG, "doInBackground: Getting weather icon"+picString);
                                picOut(picString);

                                publishProgress(100);


                            }

                        }catch(IndexOutOfBoundsException |NullPointerException ex){

                            Log.i(TAG, "doInBackground: in indexOut of Bounds");

                        }
                    }


                } catch (XmlPullParserException | IOException ex) {

                    Log.i(TAG, "doInBackground: In pullparser exct");
                }
            Log.i(TAG, "doInBackground: returned done");
            return "Done";
        }

        private void sleepNow(){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
        }

        public void onProgressUpdate(Integer ...val){


            progressBar.setProgress(val[0]);

            Log.i(TAG, "onProgressUpdate: updated progress bar to = "+val[0]);
        }

        protected void onPostExecute(String args){
            minText.setText("Min: "+min);
            maxText.setText("Max: "+max);
            currText.setText("Current:"+ current);
            wImg.setImageBitmap(pic);
            progressBar.setVisibility(View.INVISIBLE);

            Log.i(TAG, "onPostExectue: string passed = "+args);

        }

    }


    public void picOut(String picString){

        String fileString = picString+".png";
        Log.i(TAG, "picOut: looking for "+fileString);
        if (fileExistance(fileString)){
            Log.i(TAG, "picOut: Found "+fileString+"getting from file");
            FileInputStream fis = null;
                    try{
                        fis = openFileInput(fileString);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
            pic = BitmapFactory.decodeStream(fis);
        }else {
            Log.i(TAG, "picOut: "+picString+" not found downloading now");
            try {
                URL picUrl = new URL("http://openweathermap.org/img/w/" + fileString);

                pic = getImage(picUrl);

                FileOutputStream outputStream = openFileOutput(fileString, Context.MODE_PRIVATE);
                fileExistance(outputStream.toString());
                pic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();   }

    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
