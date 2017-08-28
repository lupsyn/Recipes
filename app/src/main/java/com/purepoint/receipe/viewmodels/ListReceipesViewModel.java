package com.purepoint.receipe.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.purepoint.receipe.entities.ApiResponse;
import com.purepoint.receipe.repositories.ReceipesRepository;

import javax.inject.Inject;

public class ListReceipesViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> mApiResponse;
    private ReceipesRepository mIssueRepository;

    @Inject
    public ListReceipesViewModel(ReceipesRepository issueRepositoryry) {
        mApiResponse = new MediatorLiveData<>();
        mIssueRepository = issueRepositoryry;
    }

    @NonNull
    public LiveData<ApiResponse> getApiResponse() {
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadReceipes(@NonNull String search) {
        mApiResponse.addSource(
                mIssueRepository.getReceipes(search),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );
        return mApiResponse;
    }

}