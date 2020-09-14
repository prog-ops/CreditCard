package com.example.empatkali.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Repository {
    private ItemDAO dao;
    private LiveData<Item> itemLiveData;

    public Repository(Application application) {
        RoomDb dataRoombase = RoomDb.getDB(application);
        this.dao = dataRoombase.getItemDAO();
        this.itemLiveData = dao.getItemsDao();
    }

    public LiveData<Item> get() {
        if (itemLiveData == null) {
            itemLiveData = new MutableLiveData<>();
        }
        return itemLiveData;
    }


    public void insert(Item item) {
        new insertAsyncTask(dao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDAO mAsyncTaskDao;

        insertAsyncTask(ItemDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insertDao(params[0]);
            return null;
        }
    }
}
