package com.purepoint.receipe.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.purepoint.receipe.api.ReceipeApiService;
import com.purepoint.receipe.repositories.ReceipesRepository;
import com.purepoint.receipe.repositories.ReceipesRepositoryImpl;
import com.purepoint.receipe.viewmodels.ListReceipesViewModel;
import com.purepoint.receipe.viewmodels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private static final String BASE_URL = "http://www.recipepuppy.com/";

    @Provides
    @Singleton
    ReceipeApiService provideReceipeApiService() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ReceipeApiService.class);
    }

    @Provides
    @Singleton
    ReceipesRepository provideReceipesRepository(ReceipesRepositoryImpl repository) {
        return repository;
    }

    @Provides
    ViewModel provideListReceipesViewModel(ListReceipesViewModel viewModel) {
        return viewModel;
    }

    @Provides
    ViewModelProvider.Factory provideListReceipesViewModelFactory(ViewModelFactory factory) {
        return factory;
    }
}
