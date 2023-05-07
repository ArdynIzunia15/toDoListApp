package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NewTaskActivity extends AppCompatActivity {
    MaterialButton btnCancel, btnCreate;
    RadioButton radioBtnUrgent, radioBtnNotUrgent;
    RadioGroup radioGroupUrgent;
    TextInputEditText inputTitle, inputDeadline, inputDescription;
    ArrayList<Task> arrTask = new ArrayList<>();
    boolean isValid = false;
    boolean isValidTitle = false;
    boolean isValidDeadline = false;

    public String makeData(){
        String tempStr = "";

        for(int i = 0 ; i < arrTask.size() ; i++){
            tempStr += String.valueOf(arrTask.get(i).getTitle()) + "#" + String.valueOf(arrTask.get(i).getDescription()) + "#" + String.valueOf(arrTask.get(i).getUrgent()) + "#" + String.valueOf(arrTask.get(i).getDeadline()) + "\n";
        }

        return tempStr;
    }

    public void writeData(){
        try{
            File file = new File(this.getFilesDir(), "Data.txt");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(makeData());
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readData(){
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("Data.txt"); // open the file for reading
            Scanner scanner = new Scanner(fileInputStream); // create a Scanner object to read from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // read the next line from the file
                // do something with the line (e.g. print it to the console)
                String tempArray[] = line.split("#");
                arrTask.add(new Task(tempArray[0], tempArray[1], Boolean.valueOf(tempArray[2]), tempArray[3]));
                System.out.println(line);
            }
            scanner.close(); // close the scanner to release resources
            fileInputStream.close(); // close the file input stream to release resources
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // Initialize component
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);
        inputTitle = findViewById(R.id.inputTitle);
        inputDeadline = findViewById(R.id.inputDeadline);
        radioBtnNotUrgent = findViewById(R.id.radioBtnNotUrgent);
        radioBtnUrgent = findViewById(R.id.radioBtnUrgent);
        inputDescription = findViewById(R.id.inputDescription);
        radioGroupUrgent = findViewById(R.id.radioGroupUrgent);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Validation
                if(radioBtnUrgent.isChecked() && inputDeadline.getText().toString().matches("-")){
                    inputDeadline.setText("");
                }
                if(radioBtnNotUrgent.isChecked() && inputDeadline.getText().toString().matches("")){
                    inputDeadline.setText("-");
                }
                // Get data from input
                String tempTitle = inputTitle.getText().toString();
                String tempDeadline = inputDeadline.getText().toString();
                String tempDescription = inputDescription.getText().toString();
                String tempUrgent = "";
                if(radioBtnUrgent.isChecked()){
                    tempUrgent = "true";
                }
                else{
                    tempUrgent = "false";
                }
                // Title
                if(inputTitle.getText().toString().matches("")){
                    isValidTitle = false;
                    inputTitle.setError("Must be filled");
                }
                else{
                    inputTitle.setError(null);
                    isValidTitle = true;
                }
                // Deadline
                if(inputDeadline.getText().toString().matches("") && radioBtnUrgent.isChecked() == true){
                    isValidDeadline = false;
                    inputDeadline.setError("Urgent task must have a deadline");
                }
                else if(inputDeadline.getText().toString().matches("")&& radioBtnNotUrgent.isChecked() == true){
                    inputDeadline.setText("-");
                    tempDeadline = inputDeadline.getText().toString();
                    inputDeadline.setError(null);
                    isValidDeadline = true;
                }
                else{
                    inputDeadline.setError(null);
                    isValidDeadline = true;
                }
                // Description
                if(inputDescription.getText().toString().matches("")){
                    inputDescription.setText("-");
                    tempDescription = inputDescription.getText().toString();
                }
                // Validating
                if(isValidTitle && isValidDeadline){
                    isValid = true;
                }

                // Data Valid
                if(isValid){
                    // Read existing data from file and update arrTask
                    readData();

                    // Add new task to arrTask
                    arrTask.add(new Task(tempTitle, tempDescription, Boolean.valueOf(tempUrgent), tempDeadline));

                    // Update To Do File
                    writeData();
                    finish();
                }
            }
        });


        // Condition if cancel is clicked
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}