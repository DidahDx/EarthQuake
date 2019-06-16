package com.example.earthquake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<ArrayList<EarthQuake>> {
    String year="2019";
    /** URL for earthquake data from the USGS dataset */
    static String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-01-01&endtime=2019-12-01&minmagnitude=6";
    EarthQuakeAdapter quakeAdapter;
    ListView rootView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootView = findViewById(R.id.rootView);


//       QuakeAsyncLoader task=new QuakeAsyncLoader();
//       task.execute(USGS_REQUEST_URL);


        progressBar=findViewById(R.id.progress_circular);

        getSupportLoaderManager().initLoader(100,null ,this).forceLoad();
    }

    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int id, Bundle args) {
       return new QuakeAsyncLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> earthQuakes) {
        progressBar.setVisibility(View.GONE);
        UpdateUi(earthQuakes);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<EarthQuake>> loader) {

    }




//        private class QuakeAsyncLoader extends AsyncTask<String,Void, ArrayList<EarthQuake>> {
//
//            @Override
//            protected ArrayList<EarthQuake> doInBackground(String... strings) {
//
//               ArrayList<EarthQuake>  quakes= (ArrayList<EarthQuake>) QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
//               return quakes;
//            }
//
//            @Override
//            protected void onPostExecute(ArrayList<EarthQuake> earthQuakes) {
//
//
//                if (earthQuakes==null){
//                    return;
//                }
//
//                    progressBar.setVisibility(View.GONE);
//              UpdateUi(earthQuakes);
//
//            }
//        }



    private static class QuakeAsyncLoader extends AsyncTaskLoader<ArrayList<EarthQuake>> {

        QuakeAsyncLoader(@NonNull Context context) {
            super(context);
        }


        @Nullable
        @Override
        public ArrayList<EarthQuake> loadInBackground() {
            ArrayList<EarthQuake>  quakes= (ArrayList<EarthQuake>) QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);


            return quakes;
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
