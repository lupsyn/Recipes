package com.purepoint.receipe.api;

import com.purepoint.receipe.entities.ReceipesResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReceipeApiService {
    //api/?i=onions,garlic&q=omelet&p=3
    @GET("/api/")
    Flowable<ReceipesResponse> getReceipes(@Query("q") String repo, @Query("p") int pages);
}
