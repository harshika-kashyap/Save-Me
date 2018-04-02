package com.example.android.saveme.core.main.recycler_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.saveme.R;
import com.example.android.saveme.common.data.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harshika on 1/4/18.
 * adapter for the hospitals recyclerview
 */

public class HospitalsAdapter extends RecyclerView.Adapter<HospitalsAdapter.ViewHolder> {

    private List<Result> hospitals;
    private Context context;

    public HospitalsAdapter(List<Result> hospitals, Context context) {
        this.hospitals = hospitals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospital_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(hospitals.get(position), context);
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        public TextView name;

        @BindView(R.id.tv_vicinity)
        public TextView vicinity;

        @BindView(R.id.tv_rating)
        public TextView rating;

        @BindView(R.id.iv_status)
        public ImageView status;

        @BindView(R.id.iv_icon)
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void bind(Result hospital, Context context) {
            if (hospital.name != null) {
                name.setText(hospital.name);
            }

            if (hospital.vicinity != null) {
                vicinity.setText(hospital.vicinity);
            }

            if (hospital.icon != null) {
                Picasso.with(context)
                        .load(hospital.icon)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(icon);
            }

            if (hospital.openingHours != null && hospital.openingHours.openNow != null) {
                if(hospital.openingHours.openNow) {
                    status.setImageResource(R.drawable.ic_lock_open_black_24dp);
                } else {
                    status.setImageResource(R.drawable.ic_lock_black_24dp);
                }
            } else {
                status.setImageResource(R.drawable.ic_lock_black_24dp);
            }

            if (hospital.rating != null) {
                rating.setText(hospital.rating.toString());
            } else {
                rating.setVisibility(View.GONE);
            }
        }
    }
}
