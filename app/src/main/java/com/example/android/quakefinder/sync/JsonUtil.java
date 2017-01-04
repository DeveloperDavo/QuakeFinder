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
 */

class JsonUtil {

    private JsonUtil() {
        throw new AssertionError();
    }

    static List<Earthquake> parse(String jsonString) throws JSONException {
        final ArrayList<Earthquake> earthquakes = new ArrayList<>();
        final JSONObject jsonObject = new JSONObject(jsonString);
        final JSONArray features = jsonObject.getJSONArray("features");
        for (int i = 0; i < features.length(); i++) {
            earthquakes.add(parseEarthquake(features, i));
        }
        return earthquakes;
    }

    @NonNull
    private static Earthquake parseEarthquake(JSONArray features, int i) throws JSONException {
        final JSONObject feature = features.getJSONObject(i);
        final JSONObject properties = feature.getJSONObject("properties");
        final double mag = properties.getDouble("mag");
        final String place = properties.getString("place");
        final String url = properties.getString("url");
        return new Earthquake(mag, place, url);
    }
}
