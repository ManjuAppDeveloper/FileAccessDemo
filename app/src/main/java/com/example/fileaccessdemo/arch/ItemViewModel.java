package com.example.fileaccessdemo.arch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<List<Item>> itemsLiveData;

    public LiveData<List<Item>> getItemsLiveData() {
        if (itemsLiveData == null) {
            itemsLiveData = new MutableLiveData<>();
            loadItems(); // Fetch your data here and update the LiveData
        }
        return itemsLiveData;
    }

    private void loadItems() {
        // Simulate data loading
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item 1"));
        items.add(new Item("Item 2"));
        items.add(new Item("Item 3"));
        itemsLiveData.setValue(items);
    }
}

