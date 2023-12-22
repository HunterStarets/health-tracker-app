package com.example.health_tracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    double hoursSlept;

    @ColumnInfo
    double hoursExercised;
    @ColumnInfo
    int quality;

    @ColumnInfo
    int weight;

    @ColumnInfo
    int hourOfDay;

    @ColumnInfo
    int minute;

    @ColumnInfo
    int year;

    @ColumnInfo
    int month;

    @ColumnInfo
    int day;

    public Entry(long id, double hoursSlept, double hoursExercised, int quality, int weight, int hourOfDay, int minute, int year, int month, int day) {
        this.id = id;
        this.hoursSlept = hoursSlept;
        this.hoursExercised = hoursExercised;
        this.quality = quality;
        this.weight = weight;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Entry(double hoursSlept, double hoursExercised, int quality, int weight, int hourOfDay, int minute, int year, int month, int day) {
        this.hoursSlept = hoursSlept;
        this.hoursExercised = hoursExercised;
        this.quality = quality;
        this.weight = weight;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Entry(){}

    public String getQualityAsString() {
        switch (quality) {
            case 1:
                return "Poor";
            case 2:
                return "Fair";
            case 3:
                return "Good";
            case 4:
                return "Very good";
            case 5:
                return "Excellent";
            default:
                return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", hoursSlept=" + hoursSlept +
                ", quality=" + quality +
                ", weight=" + weight +
                ", hourOfDay=" + hourOfDay +
                ", minute=" + minute +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
