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
            loadItems();
        }
        return itemsLiveData;
    }
    private void loadItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        items.add(new Item("Dhoni","Jharkand"));
        items.add(new Item("Shewag","Delhi"));
        items.add(new Item("Dravid","Bangalore"));
        itemsLiveData.setValue(items);
    }
}

