package com.example.user.sunshine;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastAdapter extends CursorAdapter {
    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /*
     cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView highTempView;
        public final TextView lowTempView;
        public final TextView descriptionView;

        public ViewHolder(View view)
        {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView =(TextView) view.findViewById(R.id.list_item_date_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        }
    }
    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;
    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType)
        {
            case VIEW_TYPE_TODAY:
                layoutId = R.layout.list_item_forecast_today;
                break;
            case VIEW_TYPE_FUTURE_DAY:
                layoutId = R.layout.list_item_forecast;
                break;
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    /*
                This is where we fill-in the views with the contents of the cursor.
             */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // Read weather icon ID from cursor
        int weatherId = cursor.getInt(MainActivity.COL_WEATHER_CONDITION_ID);
        // choose weather condition image
        int viewType = getItemViewType(cursor.getPosition());
        switch (viewType)
        {
            case VIEW_TYPE_TODAY:
                //Get weather icon.
                holder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
                break;
            case VIEW_TYPE_FUTURE_DAY:
                //Get weather icon;
                holder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(weatherId));
                break;
        }
        // Read date from cursor
        long dateInMillis = cursor.getLong(MainActivity.COL_WEATHER_DATE);
        // Find TextView and set formatted date on it
        holder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        // Read weather forecast from cursor
        String description = cursor.getString(MainActivity.COL_WEATHER_DESC);
        // Find TextView and set weather forecast on it
        holder.descriptionView.setText(description);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);

        // Read high temperature from cursor
        double high = cursor.getDouble(MainActivity.COL_WEATHER_MAX_TEMP);
        holder.highTempView.setText(Utility.formatTemperature(context,high, isMetric));

        // Read low temperature from cursor
        double low = cursor.getDouble(MainActivity.COL_WEATHER_MIN_TEMP);
        holder.lowTempView.setText(Utility.formatTemperature(context,low, isMetric));
    }
}