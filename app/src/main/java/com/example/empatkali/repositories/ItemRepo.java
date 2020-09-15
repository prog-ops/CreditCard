package com.example.empatkali.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.empatkali.data.DAO;
import com.example.empatkali.data.Item;
import com.example.empatkali.data.RoomDb;

import java.util.List;

public class ItemRepo {
    private DAO dao;
//    private LiveData<Item> itemLiveData;
    private LiveData<List<Item>> itemListLiveData;

    public ItemRepo(Application application) {
        RoomDb dataRoombase = RoomDb.getDB(application);
        this.dao = dataRoombase.getItemDAO();
//        this.itemLiveData = dao.getItemDao();
        this.itemListLiveData = dao.getItemListDao();
    }

    /*public LiveData<Item> get() {
        if (itemLiveData == null) {
            itemLiveData = new MutableLiveData<>();
        }
        return itemLiveData;
    }*/

    public LiveData<List<Item>> getList() {
        if (itemListLiveData == null) {
            itemListLiveData = new MutableLiveData<>();
        }
        return itemListLiveData;
    }


    public void insert(Item item) {
        new insertAsyncTask(dao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {
        private DAO mAsyncTaskDao;

        insertAsyncTask(DAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insertItem(params[0]);
            return null;
        }
    }
}
