package com.example.earthquake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<ArrayList<EarthQuake>> {
    String year="2019";
    /** URL for earthquake data from the USGS dataset */
    static String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-01-01&endtime=2019-12-01&minmagnitude=6";
    EarthQuakeAdapter quakeAdapter;
    ListView rootView;
    ProgressBar progressBar;
    TextView emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(R.id.rootView);
        emptyState=findViewById(R.id.empty_state);

        rootView.setEmptyView(emptyState);

        progressBar=findViewById(R.id.progress_circular);


        ConnectivityManager conManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=conManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnected()){

            getSupportLoaderManager().initLoader(100,null ,this).forceLoad();
        }else{
            progressBar.setVisibility(View.GONE);
            emptyState.setText("No network Connection");
        }



    }

    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int id, Bundle args) {
       return new QuakeAsyncLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> earthQuakes) {

        progressBar.setVisibility(View.GONE);

        if (earthQuakes!=null){
            UpdateUi(earthQuakes);
        }else{
            emptyState.setText("No earthQuake Data Found");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<EarthQuake>> loader) {

    }



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
