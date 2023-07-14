package com.example.fileaccessdemo.Parcel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fileaccessdemo.R;

public class ParcelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_activity_layout);
        User user = new User(1,"Testing",123);
        Intent i = new Intent(getApplicationContext(),UserActvity.class);
        i.putExtra("user",user);
        startActivity(i);
    }
}