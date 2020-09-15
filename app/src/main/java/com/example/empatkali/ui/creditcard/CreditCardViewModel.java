package com.example.empatkali.ui.creditcard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.empatkali.data.Facedata;
import com.example.empatkali.data.Item;
import com.example.empatkali.repositories.FacedataRepo;
import com.example.empatkali.repositories.ItemRepo;

import java.util.List;

public class CreditCardViewModel extends AndroidViewModel {
    private ItemRepo itemRepo;
    private FacedataRepo facedataRepo;

//    private LiveData<Item> itemLiveData;
    private LiveData<List<Item>> itemListLiveData;

    private LiveData<Facedata> facedataLiveData;

    public CreditCardViewModel(@NonNull Application application) {
        super(application);

        itemRepo = new ItemRepo((application));
//        itemLiveData = itemRepo.get();
        itemListLiveData = itemRepo.getList();

        facedataRepo = new FacedataRepo(application);
        facedataLiveData = facedataRepo.get();
    }

    /*public LiveData<Item> getItemLiveData() {
        return itemLiveData;
    }*/

    public LiveData<List<Item>> getItemListLiveData() {
        return itemListLiveData;
    }

    public void insertItem(Item item) {
        itemRepo.insert(item);
    }

    public LiveData<Facedata> getFacedataLiveData() {
        return facedataLiveData;
    }

    public void insertFacedata(Facedata facedata) {
        facedataRepo.insert(facedata);
    }
}