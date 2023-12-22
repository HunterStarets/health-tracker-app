package com.example.health_tracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;

public class EntriesFragment extends Fragment {

    public EntriesFragment() {
        // Required empty public constructor
    }

    public void notifyAdapter(){
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    EntryRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrys, container, false);

        ArrayList<Entry> entries = new ArrayList<>();

        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "entry.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        entries.addAll(db.entryDao().getAll());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EntryRecyclerViewAdapter(entries, (EntryRecyclerViewAdapter.EntryRecyclerViewAdapterInterface) getContext());
        recyclerView.setAdapter(adapter);

        Button buttonVisualize = view.findViewById(R.id.buttonVisualize);
        buttonVisualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoVisualize();
            }
        });

        Button buttonAddNew = view.findViewById(R.id.buttonAddNew);
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoNewEntry();
            }
        });

        return view;
    }

    EntriesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (EntriesListener) context;
    }

    interface EntriesListener{
       void gotoVisualize();
       void gotoNewEntry();
    }
}