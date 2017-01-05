package com.example.android.quakefinder.sync;

import android.support.annotation.NonNull;

import com.example.android.quakefinder.data.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 04/01/2017.
 *
 * Utility class to assist parsing json.
 */

class JsonUtil {

    private static final String USGS_FEATURES = "features";
    private static final String USGS_PROPERTIES = "properties";
    private static final String USGS_MAG = "mag";
    private static final String USGS_PLACE = "place";
    private static final String USGS_URL = "url";

    /* Class should not be instantiated */
    private JsonUtil() {
        throw new AssertionError();
    }

    static List<Earthquake> parse(String jsonString) throws JSONException {
        final ArrayList<Earthquake> earthquakes = new ArrayList<>();
        final JSONObject jsonObject = new JSONObject(jsonString);
        final JSONArray features = jsonObject.getJSONArray(USGS_FEATURES);
        for (int i = 0; i < features.length(); i++) {
            earthquakes.add(parseEarthquake(features, i));
        }
        return earthquakes;
    }

    @NonNull
    private static Earthquake parseEarthquake(JSONArray features, int i) throws JSONException {
        final JSONObject feature = features.getJSONObject(i);
        final JSONObject properties = feature.getJSONObject(USGS_PROPERTIES);
        final double mag = properties.getDouble(USGS_MAG);
        final String place = properties.getString(USGS_PLACE);
        final String url = properties.getString(USGS_URL);
        return new Earthquake(mag, place, url);
    }
}
