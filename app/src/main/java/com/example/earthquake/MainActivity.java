package com.example.earthquake;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String year="2019";
    /** URL for earthquake data from the USGS dataset */
      String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+year+"-01-01&endtime="+year+"-12-01&minmagnitude=6";
    EarthQuakeAdapter quakeAdapter;
    ListView rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootView = findViewById(R.id.rootView);

       EarthQuakeAsync task=new EarthQuakeAsync();
       task.execute(USGS_REQUEST_URL);


    }


        private class EarthQuakeAsync extends AsyncTask<String,Void, ArrayList<EarthQuake>>{

            @Override
            protected ArrayList<EarthQuake> doInBackground(String... strings) {

               ArrayList<EarthQuake>  quakes= (ArrayList<EarthQuake>) QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
               return quakes;
            }

            @Override
            protected void onPostExecute(ArrayList<EarthQuake> earthQuakes) {
                if (earthQuakes==null){
                    return;
                }

              UpdateUi(earthQuakes);

            }
        }


        private void UpdateUi(final ArrayList<EarthQuake> earthQuakes){
            quakeAdapter = new EarthQuakeAdapter(this, earthQuakes, R.color.category_numbers);
            rootView.setAdapter(quakeAdapter);

            rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuake quake=earthQuakes.get(position);

                String url=quake.getUrl();
                String location=quake.getLocation();

                    Intent i=new Intent(MainActivity.this,Browser.class);
                    i.putExtra("url",url);
                    i.putExtra("location",location);
                    startActivity(i);
                }

            });
        }


}
