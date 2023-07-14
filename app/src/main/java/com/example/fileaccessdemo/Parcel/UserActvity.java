package com.example.fileaccessdemo.Parcel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fileaccessdemo.R;

public class UserActvity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_actvity);
        Intent in = getIntent();
        User user = in.getParcelableExtra("user");
        Toast.makeText(this, user+"", Toast.LENGTH_SHORT).show();
    }
}