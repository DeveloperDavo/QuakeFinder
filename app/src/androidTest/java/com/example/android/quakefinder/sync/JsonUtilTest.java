package com.example.android.quakefinder.sync;

import android.support.test.runner.AndroidJUnit4;

import com.example.android.quakefinder.TestUtil;
import com.example.android.quakefinder.data.Earthquake;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class JsonUtilTest {

    List<Earthquake> earthquakes;

    @Before
    public void setUp() throws JSONException {
        // given and when
        earthquakes = JsonUtil.parse(TestUtil.JSON_STRING);
        assertNotNull(earthquakes);
        assertTrue(earthquakes.size() == 4);
    }

    @Test
    public void test_parse_mag() {
        // then
        final Earthquake earthquake = earthquakes.get(0);
        assertEquals(1.6, earthquake.getMag());
    }

    @Test
    public void test_parse_place() {
        // then
        final Earthquake earthquake = earthquakes.get(2);
        assertEquals("24km WSW of Coalinga, California", earthquake.getPlace());
    }

    @Test
    public void test_parse_url() {
        // then
        final Earthquake earthquake = earthquakes.get(3);
        assertEquals("http://earthquake.usgs.gov/earthquakes/eventpage/nc72747455", earthquake.getUrl());

    }

}
