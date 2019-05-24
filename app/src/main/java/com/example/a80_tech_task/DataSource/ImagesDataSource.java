package com.example.a80_tech_task.DataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.a80_tech_task.APIUtils.JSONParser;
import com.example.a80_tech_task.APIUtils.NetworkState;
import com.example.a80_tech_task.APIUtils.ServiceGenerator;
import com.example.a80_tech_task.Entities.Movie;
import com.example.a80_tech_task.dataModels.ImagesWebService;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.Executor;



public class ImagesDataSource extends PageKeyedDataSource<Long, Movie> {
    private ImagesWebService tmdbWebService;
    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;
    private Executor retryExecutor;

    public ImagesDataSource(Executor retryExecutor, ImagesWebService webService) {
        tmdbWebService = webService;
        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
        this.retryExecutor = retryExecutor;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {

        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        tmdbWebService.getMoviesInTheater(ServiceGenerator.API_DEFAULT_PAGE_KEY).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseString;
                List<Movie> moviesList;
                if (response.isSuccessful() && response.code() ==200) {
                        try {

                            initialLoading.postValue(NetworkState.LOADING);
                            networkState.postValue(NetworkState.LOADED);
                            responseString = response.body().string();
                            moviesList = JSONParser.getMovieList(responseString);
                            callback.onResult(moviesList,null, (long) 2);

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                }else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED,response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED,response.message()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED,errorMessage));
            }
    });
    }
    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }
    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {
        networkState.postValue(NetworkState.LOADING);
        tmdbWebService.getMoviesInTheater(params.key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject responseJson;
                String responseString;
                List<Movie> moviesList;
                Long nextKey;

                if (response.isSuccessful() && response.code() ==200) {
                    try {
                        initialLoading.postValue(NetworkState.LOADING);
                        networkState.postValue(NetworkState.LOADED);

                        responseString = response.body().string();
                        moviesList = JSONParser.getMovieList(responseString);

                        responseJson = new JSONObject(responseString);
                        nextKey = (params.key == responseJson.getInt("total_pages")) ? null : params.key+1;

                        callback.onResult(moviesList, nextKey);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED,response.message()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED,errorMessage));
            }
        });
    }



}

