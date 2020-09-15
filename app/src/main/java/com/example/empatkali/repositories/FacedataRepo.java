package com.example.empatkali.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.empatkali.data.DAO;
import com.example.empatkali.data.Facedata;
import com.example.empatkali.data.RoomDb;

public class FacedataRepo {
    private DAO dao;
    private LiveData<Facedata> facedataLiveData;

    public FacedataRepo(Application application) {
        RoomDb dataRoombase = RoomDb.getDB(application);
        this.dao = dataRoombase.getItemDAO();
        this.facedataLiveData = dao.getFacedataDao();
    }

    public LiveData<Facedata> get() {
        if (facedataLiveData == null) {
            facedataLiveData = new MutableLiveData<>();
        }
        return facedataLiveData;
    }


    public void insert(Facedata item) {
        new insertAsyncTask(dao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<Facedata, Void, Void> {
        private DAO mAsyncTaskDao;

        insertAsyncTask(DAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Facedata... params) {
            mAsyncTaskDao.insertFacedata(params[0]);
            return null;
        }
    }
}
