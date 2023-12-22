package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EntryRecyclerViewAdapter extends RecyclerView.Adapter<EntryRecyclerViewAdapter.EntryViewHolder> {
    ArrayList<Entry> entries;
    EntryRecyclerViewAdapterInterface mListener;

    public EntryRecyclerViewAdapter(ArrayList<Entry> entries, EntryRecyclerViewAdapterInterface mListener){
        this.entries = entries;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_item, parent, false);
        //Log.d("loggingtest", view.getTag().toString());
        EntryViewHolder entryViewHolder = new EntryViewHolder(view, mListener);

        return entryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Entry entry = entries.get(position);
        holder.entry = entry;
        holder.textViewDateTime.setText("Date: " + ((entry.month + 1) + "/" + entry.day + "/" + entry.year) + ", Time: " + entry.hourOfDay + ":" + entry.minute);
        holder.textViewHours.setText("Hours Slept: " + entry.hoursSlept + ", Hours Exercised: " + entry.hoursExercised);
        holder.textViewWeight.setText("Weight: " + entry.weight);
    }

    @Override
    public int getItemCount() {
        Log.d("loggingtest", "getItemCount Ran");
        return entries.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDelete;
        TextView textViewDateTime;
        TextView textViewHours;
        TextView textViewWeight;

        EntryRecyclerViewAdapterInterface mListener;
        View rootView;
        int position;
        Entry entry;

        public EntryViewHolder(@NonNull View itemView, EntryRecyclerViewAdapterInterface mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            textViewHours = itemView.findViewById(R.id.textViewHours);
            textViewWeight = itemView.findViewById(R.id.textViewWeightLabel);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });


            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteEntry(entry);
                }
            });
        }
    }
    interface EntryRecyclerViewAdapterInterface{
        void deleteEntry(Entry entry);
    }

}

