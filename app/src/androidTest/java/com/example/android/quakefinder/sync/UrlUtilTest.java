package com.example.android.quakefinder.sync;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class UrlUtilTest {

    @Test
    public void test_buildUrl() throws MalformedURLException {
        // given and when
        final URL url = UrlUtil.buildUrl();

        // then
        final URL expected = new URL("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojsonp");
        assertThat(url).isEqualTo(expected);
    }

}
