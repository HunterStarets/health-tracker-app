package com.example.health_tracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewEntryFragment extends Fragment {

    public NewEntryFragment() {
        // Required empty public constructor
    }
    Entry entry = new Entry();

    public void setDate(int year, int month, int day){
        entry.year = year;
        entry.month = month;
        entry.day = day;
        textViewDate.setText((month + 1) + "/" + day + "/" + year);
    }
    public void setTime(int hourOfDay, int minute){
        entry.hourOfDay = hourOfDay;
        entry.minute = minute;
        //TODO: Fix how time displays
        // maybe add method to entry object that returns time in a displayable format
        textViewTime.setText(hourOfDay + ":" + minute);
    }

    public void setSleepQuality(int sleepQuality){
        entry.quality = sleepQuality;
    }

    public void setHoursSlept(double hoursSlept){
        entry.hoursSlept = hoursSlept;
    }

    public void setHoursExercised(double hoursExercised){
        entry.hoursExercised = hoursExercised;
    }

    TextView textViewDate;
    TextView textViewTime;
    TextView textViewQuality;
    TextView textViewHoursExercised;
    TextView textViewHoursSleptInput;
    EditText editTextWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_entry, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewTime = view.findViewById(R.id.textViewTime);
        textViewQuality = view.findViewById(R.id.textViewQualityTest);
        textViewHoursExercised = view.findViewById(R.id.textViewHoursExercised);
        textViewHoursSleptInput = view.findViewById(R.id.textViewHoursSleptInput);
        editTextWeight = view.findViewById(R.id.editTextWeight);

        if(!(entry.year == 0 && entry.day == 0 && entry.month == 0)){
            textViewDate.setText((entry.month + 1) + "/" + entry.day + "/" + entry.year);
        }

        if(!(entry.hourOfDay == 0 && entry.minute == 0)){
            textViewTime.setText(entry.hourOfDay + ":" + entry.minute);
        }

        if(!(entry.quality == 0)){
            textViewQuality.setText(entry.getQualityAsString());
        }

        if(!(entry.hoursSlept == 0.0)){
            textViewHoursSleptInput.setText(String.valueOf(entry.hoursSlept));
        }

        if(!(entry.hoursExercised == 0.0)){
            textViewHoursExercised.setText(String.valueOf(entry.hoursExercised));
        }



        Button buttonDate = view.findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch date picker
                (new DatePickerFragment()).show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        Button buttonTime = view.findViewById(R.id.buttonTime);
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch time picker
                (new TimePickerFragment()).show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        Button buttonQuality = view.findViewById(R.id.buttonQuality);
        buttonQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoQuality();
            }
        });

        Button buttonSlept = view.findViewById(R.id.buttonSlept);
        buttonSlept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectHoursSlept();
            }
        });

        Button buttonExercised = view.findViewById(R.id.buttonExercised);
        buttonExercised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectHoursExercised();
            }
        });

        //SUBMIT AND CANCEL//
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = String.valueOf(editTextWeight.getText());
                boolean isPopulated = true;
                if((entry.year == 0 || entry.day == 0 || entry.month == 0)){
                    isPopulated = false;
                }

                if((entry.hourOfDay == 0 || entry.minute == 0)){
                    isPopulated = false;
                }

                if((entry.quality == 0)){
                    isPopulated = false;
                }

                if((entry.hoursSlept == 0.0)){
                    isPopulated = false;
                }

                if((entry.hoursExercised == 0.0)){
                    isPopulated = false;
                }
                if(weight.isEmpty()){
                    isPopulated = false;
                }

                if(isPopulated){
                    entry.weight = Integer.valueOf(weight);
                    AppDatabase db = db = Room.databaseBuilder(getContext(), AppDatabase.class, "entry.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                    db.entryDao().insertAll(entry);
                    mListener.popBackStack();
                } else {
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.popBackStack();
            }
        });
    }

    NewEntryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NewEntryListener) context;
    }

    interface NewEntryListener{
        void gotoQuality();
        void gotoSelectHoursSlept();
        void gotoSelectHoursExercised();
        void popBackStack();
    }

}