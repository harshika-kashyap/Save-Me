package com.example.android.saveme.core.main.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.saveme.R;
import com.example.android.saveme.common.data.pojo.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harshika on 1/4/18.
 * adapter for the hospitals recyclerview
 */

public class HospitalsAdapter extends RecyclerView.Adapter<HospitalsAdapter.ViewHolder> {

    private List<Result> hospitals;

    public HospitalsAdapter(List<Result> hospitals) {
        this.hospitals = hospitals;
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
        holder.bind(hospitals.get(position));
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void bind(Result hospital) {
            name.setText(hospital.name);
            vicinity.setText(hospital.vicinity);
        }
    }
}
