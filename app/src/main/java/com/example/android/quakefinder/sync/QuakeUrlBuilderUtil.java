package com.example.android.quakefinder.sync;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by David on 03/01/2017.
 */
final class QuakeUrlBuilderUtil {
    private static final String LOG_TAG = QuakeUrlBuilderUtil.class.getSimpleName();

    /* Private Constructor */
    private QuakeUrlBuilderUtil() {
        throw new AssertionError();
    }

    static URL buildUrl() throws MalformedURLException {
        // "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojsonp"
        final Uri uri = Uri.parse("http://earthquake.usgs.gov").buildUpon()
                .appendPath("earthquakes")
                .appendPath("feed")
                .appendPath("v1.0")
                .appendPath("summary")
                .appendPath("all_day.geojsonp")
                .build();

        return new URL(uri.toString());
    }

}
