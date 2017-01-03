package com.example.android.quakefinder.sync;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by David on 03/01/2017.
 */

public class QuakeSyncTask extends AsyncTask<Void, Void, Void> {
    private static final String LOG_TAG = QuakeSyncTask.class.getSimpleName();

    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            final URL url = QuakeUrlBuilderUtil.buildUrl();
            Log.d(LOG_TAG, "url: " + url);

            urlConnection = connect(url);
            final InputStream inputStream = urlConnection.getInputStream();
            final StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // helpful for debugging
                buffer.append(line).append("\n");
            }

            final String jsonString = buffer.toString();
            Log.d(LOG_TAG, "jsonString: " + jsonString);
            // TODO: parse string
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } finally {
            disconnectAndClose(urlConnection, reader);
        }

        return null;
    }

    @NonNull
    private HttpURLConnection connect(URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection;
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
