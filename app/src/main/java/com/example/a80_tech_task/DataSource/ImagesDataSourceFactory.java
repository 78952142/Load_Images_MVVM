package com.example.a80_tech_task.DataSource;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import com.example.a80_tech_task.dataModels.ImagesWebService;

import java.util.concurrent.Executor;



public class ImagesDataSourceFactory extends DataSource.Factory {
    ImagesDataSource moviesDataSource;
    MutableLiveData<ImagesDataSource> mutableLiveData;
    Executor executor;
    ImagesWebService webService;

    public ImagesDataSourceFactory(Executor executor, ImagesWebService webService) {
        
      this.executor = executor;
      this.webService = webService;
      mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
              moviesDataSource = new ImagesDataSource(executor,webService);
                mutableLiveData.postValue(moviesDataSource);
                return moviesDataSource;
    }

    public MutableLiveData<ImagesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
