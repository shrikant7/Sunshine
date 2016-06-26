package com.example.user.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> mForecastAdapter;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* dummy weather forecast data */
        String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/40",
                "Wed - Cloudy - 72/63",
                "Thurs - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHERSTATION",
                "Sun - Sunny - 80/68"
        };

        //setting list with dummy data array.
        List<String> weekForecast = new LinkedList<String>(Arrays.asList(forecastArray));

        //Adapter to view sourse list to listView.
        mForecastAdapter = new ArrayAdapter<String>(this,R.layout.list_item_forecast,R.id.list_item_forecast_textview,weekForecast);
        mListView = (ListView) findViewById(R.id.listview_forecast);
        mListView.setAdapter(mForecastAdapter);
    }
}
