package com.example.fileaccessdemo.arch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.fileaccessdemo.R;

import java.util.ArrayList;
public class ArchActivity extends AppCompatActivity {
    private ItemViewModel itemViewModel;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(itemAdapter);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getItemsLiveData().observe(this, items -> itemAdapter.setItemsList(items));
    }
}
