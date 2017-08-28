package com.purepoint.receipe.repositories;

import android.arch.lifecycle.LiveData;

import com.purepoint.receipe.entities.ApiResponse;


public interface ReceipesRepository {
    LiveData<ApiResponse> getReceipes(String toSearch);
}
