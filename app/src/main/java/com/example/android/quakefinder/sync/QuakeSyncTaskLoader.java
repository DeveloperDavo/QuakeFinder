package com.example.android.quakefinder.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.quakefinder.R;
import com.example.android.quakefinder.data.Earthquake;
import com.example.android.quakefinder.ui.VisibilityToggle;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by David on 21/01/2017.
 * <p>
 * Connects to the server on the background thread using the url built in UrlUtil.
 */

public class QuakeSyncTaskLoader extends AsyncTaskLoader<List<Earthquake>> {
    private static final String LOG_TAG = AsyncTaskLoader.class.getSimpleName();

    private final Context context;
    private VisibilityToggle visibilityToggle;
    private int severityId;

    private List<Earthquake> earthquakes;

    public QuakeSyncTaskLoader(Context context, VisibilityToggle visibilityToggle, int severityId) {
        super(context);
        this.context = context;
        this.visibilityToggle = visibilityToggle;
        this.severityId = severityId;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading called");
        if (earthquakes != null) {
            deliverResult(earthquakes);
        } else {
            forceLoad();
            visibilityToggle.showProgressBar();
        }
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground called");
        try {
            return JsonUtil.parse(getJsonString());
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void deliverResult(List<Earthquake> data) {
        Log.d(LOG_TAG, "deliverResult called");
        earthquakes = data;
        super.deliverResult(data);
    }

    private String getJsonString() {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try {
            final URL url = UrlUtil.buildUrl(getSeverity());
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
    private String getSeverity() {
        if (severityId == 0) {
            severityId = R.string.param_all;
        }
        return context.getString(severityId);
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
