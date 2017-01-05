package com.example.android.quakefinder.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.quakefinder.R;
import com.example.android.quakefinder.data.Earthquake;
import com.example.android.quakefinder.sync.QuakeSyncTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements QuakeAdapter.QuakeAdapterOnClickHandler {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_earthquakes)
    RecyclerView recyclerView;

    private QuakeAdapter quakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpActionBar();
        setUpRV();

        loadQuakeData();
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpRV() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        quakeAdapter = new QuakeAdapter(this);
        recyclerView.setAdapter(quakeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Log.d(LOG_TAG, "refresh");
            loadQuakeData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Earthquake earthquake) {
        final String url = earthquake.getUrl();
        openWebPage(url);
    }

    private void openWebPage(String url) {
        final Uri uri = Uri.parse(url);
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void loadQuakeData() {
        new QuakeSyncTask(quakeAdapter).execute();
    }

}
