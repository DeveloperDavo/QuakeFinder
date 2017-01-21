package com.example.android.quakefinder.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakefinder.R;
import com.example.android.quakefinder.data.Earthquake;
import com.example.android.quakefinder.sync.QuakeSyncTaskLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        QuakeAdapter.QuakeAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int LOADER = 0;

    @BindView(R.id.rv_earthquakes)
    RecyclerView recyclerView;

    @BindView(R.id.pb_earthquakes)
    ProgressBar progressBar;

    @BindView(R.id.tv_error_message)
    TextView errorMessageTV;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private QuakeAdapter quakeAdapter;
    private int severityId;
    private VisibilityToggle visibilityToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpActionBar();
        setUpRV();
        setUpRefreshListener();
        setUpVisibilityToggle();
        initializeLoader();
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpRV() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        quakeAdapter = new QuakeAdapter(this);
        recyclerView.setAdapter(quakeAdapter);
    }

    private void setUpRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(LOG_TAG, "onRefresh called");
                quakeAdapter.setData(null);
                getSupportLoaderManager().restartLoader(LOADER, null, MainActivity.this);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setUpVisibilityToggle() {
        visibilityToggle = new VisibilityToggle(recyclerView, errorMessageTV, progressBar);
    }

    private void initializeLoader() {
        getSupportLoaderManager().initLoader(LOADER, null, this);
    }

    /* Activity methods */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_significant) {
            Log.d(LOG_TAG, "significant");
            severityId = R.string.param_significant;
        } else if (id == R.id.action_all) {
            Log.d(LOG_TAG, "all");
            severityId = R.string.param_all;
        } else {
            return super.onOptionsItemSelected(item);
        }

        quakeAdapter.setData(null);
        getSupportLoaderManager().restartLoader(LOADER, null, this);

        return true;
    }

    /* QuakeAdapterOnClickHandler methods */

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

    /* LoaderManager methods */

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new QuakeSyncTaskLoader(this, visibilityToggle, severityId);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        visibilityToggle.hideProgressBar();
        if (data != null && data.size() > 0) {
            visibilityToggle.showData();
            quakeAdapter.setData(data);
        } else {
            visibilityToggle.showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // do nothing
    }

}
