package com.example.android.quakefinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by David on 03/01/2017.
 */

public class QuakeAdapter extends RecyclerView.Adapter<QuakeAdapter.QuakeViewHolder> {
    private static final String LOG_TAG = QuakeAdapter.class.getSimpleName();

    @Override
    public QuakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final int layoutIdForListItem = R.layout.earthquake_list_item;
        final LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        final View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new QuakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuakeViewHolder holder, int position) {
        holder.placeTextView.setText("test");
    }

    @Override
    public int getItemCount() {
        return 17;
    }

    class QuakeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_place)
        TextView placeTextView;

        QuakeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
