package com.example.android.quakefinder.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.quakefinder.R;
import com.example.android.quakefinder.data.Earthquake;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by David on 03/01/2017.
 */

public class QuakeAdapter extends RecyclerView.Adapter<QuakeAdapter.QuakeViewHolder> {
    private static final String LOG_TAG = QuakeAdapter.class.getSimpleName();

    private List<Earthquake> earthquakes;

    public void setData(List<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
        notifyDataSetChanged();
    }

    @Override
    public QuakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.earthquake_list_item, parent, false);

        return new QuakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuakeViewHolder holder, int position) {
        holder.bind(earthquakes.get(position));
    }

    @Override
    public int getItemCount() {
        if (earthquakes != null) {
            return earthquakes.size();
        } else {
            return 0;
        }
    }

    class QuakeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_place)
        TextView placeTextView;

        QuakeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Earthquake earthquake) {
            placeTextView.setText(earthquake.getPlace());
        }
    }
}
