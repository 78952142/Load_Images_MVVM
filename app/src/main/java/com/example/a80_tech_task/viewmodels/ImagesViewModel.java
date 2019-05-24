package com.example.a80_tech_task.viewmodels;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;
import com.example.a80_tech_task.APIUtils.NetworkState;
import com.example.a80_tech_task.APIUtils.ServiceGenerator;
import com.example.a80_tech_task.DataSource.ImagesDataSource;
import com.example.a80_tech_task.DataSource.ImagesDataSourceFactory;
import com.example.a80_tech_task.Entities.Movie;
import com.example.a80_tech_task.dataModels.ImagesWebService;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



public class ImagesViewModel extends ViewModel {
    private LiveData<PagedList<Movie>> moviesInTheaterList;
    private LiveData<NetworkState> networkStateLiveData;
    private Executor executor;
    private LiveData<ImagesDataSource> dataSource;


    public ImagesViewModel() {
        executor = Executors.newFixedThreadPool(5);
        ImagesWebService webService = ServiceGenerator.createService(ImagesWebService.class);
        ImagesDataSourceFactory factory = new ImagesDataSourceFactory(executor,webService);
        dataSource =  factory.getMutableLiveData();

        networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), new Function<ImagesDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(ImagesDataSource source) {
                return source.getNetworkState();
            }
        });

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                                                .setEnablePlaceholders(true)
                                                .setInitialLoadSizeHint(10)
                                                .setPageSize(20).build();

        moviesInTheaterList = (new LivePagedListBuilder<Long,Movie>(factory,pageConfig))
                                                    .setBackgroundThreadExecutor(executor)
                                                    .build();

    }

    public LiveData<PagedList<Movie>> getMoviesInTheaterList() {
        return moviesInTheaterList;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }
}

