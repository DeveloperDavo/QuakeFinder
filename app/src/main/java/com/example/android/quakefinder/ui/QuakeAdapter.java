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
    private QuakeAdapterOnClickHandler clickHandler;

    public QuakeAdapter(QuakeAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

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

    interface QuakeAdapterOnClickHandler {
        void onClick(Earthquake earthquake);
    }

    class QuakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_mag)
        TextView magTextView;

        @BindView(R.id.tv_region)
        TextView regionTextView;

        QuakeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Earthquake earthquake) {
            setMagTV(earthquake);
            setRegionTV(earthquake);
        }

        private void setMagTV(Earthquake earthquake) {
            final String mag = Double.toString(earthquake.getMag());
            magTextView.setText(mag);
        }

        private void setRegionTV(Earthquake earthquake) {
            final String place = earthquake.getPlace();
//            final String region = place.substring(place.lastIndexOf(',') + 1).trim();
            regionTextView.setText(place);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClick(earthquakes.get(getAdapterPosition()));
        }
    }
}
