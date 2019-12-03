package com.example.earthquake;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private int mColorResourceId;

    public EarthQuakeAdapter(@NonNull Activity context, @NonNull ArrayList<EarthQuake> objects, int colorResource) {
        super(context, 0, objects);
        mColorResourceId = colorResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        EarthQuake currentEarthQuake = getItem(position);

        TextView magnidute = listView.findViewById(R.id.magnitude);
        magnidute.setText(currentEarthQuake.getMagnitude());

        TextView location = listView.findViewById(R.id.location_offset);
        location.setText(currentEarthQuake.getLocationOffeset());

        TextView locationOffeset = listView.findViewById(R.id.location);
        locationOffeset.setText(currentEarthQuake.getLocation());

        TextView date = listView.findViewById(R.id.date);
        date.setText(currentEarthQuake.getDate());

        TextView time=listView.findViewById(R.id.time);
        time.setText(currentEarthQuake.getTime());

// Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnidute.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(Double.parseDouble(currentEarthQuake.getMagnitude()));


        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
