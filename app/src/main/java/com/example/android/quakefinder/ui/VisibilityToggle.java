package com.example.android.quakefinder.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by David on 05/01/2017.
 */

public class VisibilityToggle {

    private RecyclerView recyclerView;
    private TextView errorMessageTV;
    private ProgressBar progressBar;

    VisibilityToggle(RecyclerView recyclerView, TextView errorMessageTV, ProgressBar progressBar) {
        this.recyclerView = recyclerView;
        this.errorMessageTV = errorMessageTV;
        this.progressBar = progressBar;
    }

    public void showData() {
        recyclerView.setVisibility(View.VISIBLE);
        errorMessageTV.setVisibility(View.INVISIBLE);
    }

    public void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessageTV.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
