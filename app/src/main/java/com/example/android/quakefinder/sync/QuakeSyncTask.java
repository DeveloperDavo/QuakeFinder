package com.example.android.quakefinder.sync;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.quakefinder.data.Earthquake;
import com.example.android.quakefinder.ui.QuakeAdapter;
import com.example.android.quakefinder.ui.VisibilityToggle;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 03/01/2017.
 */

public class QuakeSyncTask extends AsyncTask<Void, Void, List<Earthquake>> {
    private static final String LOG_TAG = QuakeSyncTask.class.getSimpleName();

    private final QuakeAdapter quakeAdapter;
    private final VisibilityToggle visibilityToggle;

    public QuakeSyncTask(QuakeAdapter quakeAdapter, VisibilityToggle visibilityToggle) {
        this.quakeAdapter = quakeAdapter;
        this.visibilityToggle = visibilityToggle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        visibilityToggle.showProgressBar();
    }

    @Override
    protected List<Earthquake> doInBackground(Void... params) {
        List<Earthquake> earthquakes = new ArrayList<>();
        try {
            earthquakes = JsonUtil.parse(getJsonString());
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return earthquakes;
    }

    @Override
    protected void onPostExecute(List<Earthquake> earthquakes) {
        visibilityToggle.hideProgressBar();
        if (earthquakes != null && earthquakes.size() > 0) {
            visibilityToggle.showData();
            quakeAdapter.setData(earthquakes);
        } else {
            visibilityToggle.showErrorMessage();
        }
    }

    private String getJsonString() {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try {
            final URL url = UrlUtil.buildUrl();
            Log.d(LOG_TAG, "url: " + url);

            urlConnection = connect(url);
            final InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            return getJsonString(bufferedReader);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return "";
        } finally {
            disconnectAndClose(urlConnection, bufferedReader);
        }
    }

    @NonNull
    private HttpURLConnection connect(URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection;
    }

    @NonNull
    private String getJsonString(BufferedReader bufferedReader) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            // helpful for debugging
            stringBuilder.append(line).append("\n");
        }

        String jsonString = stringBuilder.toString();
        // remove "eqfeed_callback(...)" from string
        final String startOfJsonStr = "eqfeed_callback(";
        if (jsonString.startsWith(startOfJsonStr)) {
            jsonString = jsonString.replace(startOfJsonStr, "");
            jsonString = jsonString.substring(0, jsonString.length());
        }
        Log.d(LOG_TAG, "jsonString: " + jsonString);
        return jsonString;
    }

    private void disconnectAndClose(HttpURLConnection urlConnection, BufferedReader reader) {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (final IOException e) {
                Log.e(LOG_TAG, "Error closing stream", e);
            }
        }
    }

}
