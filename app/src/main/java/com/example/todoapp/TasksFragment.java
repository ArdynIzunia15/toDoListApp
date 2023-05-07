package com.example.todoapp;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TasksFragment extends Fragment {
    RecyclerView recyclerView;
    static ArrayList<Task> objTask = new ArrayList<>();
    TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(objTask);

    public void readData(){
        objTask.clear();
        try {
            FileInputStream fileInputStream = getActivity().openFileInput("Data.txt"); // open the file for reading
            Scanner scanner = new Scanner(fileInputStream); // create a Scanner object to read from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // read the next line from the file
                // do something with the line (e.g. print it to the console)
                String tempArray[] = line.split("#");
                objTask.add(new Task(tempArray[0], tempArray[1], Boolean.valueOf(tempArray[2]), tempArray[3]));
                System.out.println(line);
            }
            scanner.close(); // close the scanner to release resources
            fileInputStream.close(); // close the file input stream to release resources
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshData(){
        objTask.clear();
        readData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Read data
        readData();

        adapter = new TaskRecyclerViewAdapter(objTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}