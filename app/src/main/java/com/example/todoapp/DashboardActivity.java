package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class DashboardActivity extends AppCompatActivity {
    FloatingActionButton btnAdd, btnDelete, btnRefresh;

    public void createFile(){
        String filename = "Data.txt";
        String fileContents = "Here is the Task Title#Here will be the task description#false#Here will be the task deadline\n";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
            Log.i("MyApp", "File " + filename + " telah berhasil dibuat di penyimpanan internal.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MyApp", "Error saat membuat file " + filename + " di penyimpanan internal.");
        }

    }

    public void writeDataFirstTime(){
        try{
            File file = new File(getApplicationContext().getFilesDir(), "Data.txt");
            if (!file.exists()) {
                // file.getParentFile().mkdirs();
                // file.createNewFile();
                createFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write("Here is the Task Title#Here will be the task description#false#Here will be the task deadline\n");
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the status bar color to red
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.background));
        }
        // Get the window and the decor view
        Window window = getWindow();
        View decorView = window.getDecorView();

        // Determine the system UI visibility flags to set
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                // If the current theme is dark, set the status bar text color to black
                uiOptions = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                // If the current theme is light, set the status bar text color to white
                uiOptions = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }

        // Set the system UI visibility flags
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_dashboard);

        // Main Program
        TasksFragment fragment = new TasksFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment, "displayFragment").commit();
        }


        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnRefresh = findViewById(R.id.btnRefresh);

        // Create file for the first time
        File file = new File(getApplicationContext().getFilesDir(), "Data.txt");
        if (!file.exists()) {
            createFile();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with a new instance of the same fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new TasksFragment(), "displayFragment");
                fragmentTransaction.commit();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDataFirstTime();
            }
        });
    }
}