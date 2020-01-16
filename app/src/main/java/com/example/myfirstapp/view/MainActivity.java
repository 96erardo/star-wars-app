package com.example.myfirstapp.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myfirstapp.R;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
