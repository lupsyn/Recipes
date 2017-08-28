package com.purepoint.receipe.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.purepoint.receipe.api.ReceipeApiService;
import com.purepoint.receipe.entities.ApiResponse;
import com.purepoint.receipe.entities.Receipe;
import com.purepoint.receipe.entities.ReceipesResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceipesRepositoryImpl implements ReceipesRepository {
    @Inject
    ReceipeApiService mApiService;

    @Inject
    public ReceipesRepositoryImpl() {
    }

    // There might be cases when the default transformations won’t be applicable or sufficient to your needs,
    // you’ll need a custom transformation.
    // To implement your own transformation you can you use the MediatorLiveData class.
    public LiveData<ApiResponse> getReceipes(String toSearch) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();

        //This is threated like only one call. If one fails all the call fail.
        Flowable.zip(mApiService.getReceipes(toSearch.toLowerCase(), 1),
                mApiService.getReceipes(toSearch, 2), (BiFunction<ReceipesResponse, ReceipesResponse, Object>)
                        (receipesResponse, receipesResponse2) -> {
                            List<Receipe> receipes = new ArrayList<>();
                            receipes.addAll(receipesResponse.getReceipes());
                            receipes.addAll(receipesResponse2.getReceipes());
                            return new ApiResponse(receipes);
                        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse -> liveData.setValue((ApiResponse) apiResponse),
                        throwable -> liveData.setValue(new ApiResponse(throwable)));

        return liveData;
    }


}
