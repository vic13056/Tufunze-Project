package com.example.tufunze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UsersActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
            radioGroup = findViewById(R.id.radiogroup);
    }

    public void checkbutton(View view) {
        int radioid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);
    }
}
