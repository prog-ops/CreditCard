package com.example.empatkali.ui.creditcard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.empatkali.data.Item;
import com.example.empatkali.data.Repository;

public class CreditCardViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<Item> itemLiveData;

    public CreditCardViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository((application));
        itemLiveData = repository.get();
    }

    public LiveData<Item> getItemLiveData() {
        return itemLiveData;
    }

    public void insertItem(Item item) {
        repository.insert(item);
    }
}