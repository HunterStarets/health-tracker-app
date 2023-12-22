package com.example.health_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;


import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements VisualizationFragment.VisualizationListener, EntryRecyclerViewAdapter.EntryRecyclerViewAdapterInterface, EntriesFragment.EntriesListener, NewEntryFragment.NewEntryListener, DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener, SelectSleepQualityFragment.SelectSleepQualityListener, SelectHoursSleptFragment.SelectHoursSleptListener, SelectHoursExercisedFragment.SelectHoursExercisedListener {
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         db = Room.databaseBuilder(this, AppDatabase.class, "entry.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new EntriesFragment(), "entries-fragment")
                .commit();
    }

    @Override
    public void gotoVisualize() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new VisualizationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoNewEntry() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new NewEntryFragment(), "new-entry-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendSleepQuality(int sleepQuality) {
        NewEntryFragment fragment = (NewEntryFragment) getSupportFragmentManager().findFragmentByTag("new-entry-fragment");
        fragment.setSleepQuality(sleepQuality);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendHoursSlept(double hoursSlept) {
        NewEntryFragment fragment = (NewEntryFragment) getSupportFragmentManager().findFragmentByTag("new-entry-fragment");
        fragment.setHoursSlept(hoursSlept);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendHoursExercised(double hoursExercised) {
        NewEntryFragment fragment = (NewEntryFragment) getSupportFragmentManager().findFragmentByTag("new-entry-fragment");
        fragment.setHoursExercised(hoursExercised);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoQuality() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new SelectSleepQualityFragment(), "select-sleep-quality-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectHoursSlept() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new SelectHoursSleptFragment(), "select-hours-slept-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectHoursExercised() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new SelectHoursExercisedFragment(), "select-hours-exercised-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendDateToNewEntry(int year, int month, int day) {
        NewEntryFragment fragment =  (NewEntryFragment) getSupportFragmentManager().findFragmentByTag("new-entry-fragment");
        fragment.setDate(year, month, day);
        Log.d("TESTING", String.valueOf(fragment.getId()));
    }

    @Override
    public void sendTimeToNewEntry(int hourOfDay, int minute) {
        NewEntryFragment fragment =  (NewEntryFragment) getSupportFragmentManager().findFragmentByTag("new-entry-fragment");
        fragment.setTime(hourOfDay, minute);
        Log.d("TESTING", String.valueOf(fragment.getId()));

    }



    public AppDatabase getDatabase(){
        return db;
    }

    @Override
    public void deleteEntry(Entry entry) {
        db.entryDao().delete(entry);
        Log.d("ENTRY", String.valueOf(entry.id));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new EntriesFragment(), "entries-fragment")
                .commit();
    }

}
