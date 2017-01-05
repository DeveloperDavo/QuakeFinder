package com.example.android.quakefinder.sync;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by David on 03/01/2017.
 * <p>
 * Utility class to assist with building the url.
 */
final class UrlUtil {
    private static final String LOG_TAG = UrlUtil.class.getSimpleName();

    /* Class should not be instantiated */
    private UrlUtil() {
        throw new AssertionError();
    }

    static URL buildUrl(String severity) throws MalformedURLException {
        // "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojsonp"
        final String timeframe = "day";
        final Uri uri = Uri.parse("http://earthquake.usgs.gov").buildUpon()
                .appendPath("earthquakes")
                .appendPath("feed")
                .appendPath("v1.0")
                .appendPath("summary")
                .appendPath(severity + "_" + timeframe + ".geojsonp")
                .build();

        return new URL(uri.toString());
    }

}
