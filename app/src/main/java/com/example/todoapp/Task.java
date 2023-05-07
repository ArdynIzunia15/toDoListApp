package com.example.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String title;
    private String description;
    private Boolean isUrgent;
    private String deadline;

    public Task(String title, String description, Boolean isUrgent, String deadline) {
        this.title = title;
        this.description = description;
        this.isUrgent = isUrgent;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUrgent() {
        return isUrgent;
    }

    public void setUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeValue(this.isUrgent);
        dest.writeString(this.deadline);
    }

    public void readFromParcel(Parcel source) {
        this.title = source.readString();
        this.description = source.readString();
        this.isUrgent = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.deadline = source.readString();
    }

    protected Task(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.isUrgent = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deadline = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
